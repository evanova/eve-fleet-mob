package com.eve0.fleetmob.app.crest;

import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContacts;
import com.eve0.crest.model.CrestToken;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public final class CrestClient {
    private static final Logger LOG = LoggerFactory.getLogger(CrestClient.class);

    interface LoginService {

        @POST("/oauth/token")
        Call<CrestToken> getUserToken(@Query("grant_type") String type, @Query("code") String code);

        @POST("/oauth/token")
        Call<CrestToken> getUserTokenUsingRefresh(@Query("grant_type") String type, @Query("refresh_token") String token);

        @GET("/oauth/verify")
        Call<CrestCharacterStatus> getVerification(@Header("Authorization") String token);
    }

    interface ClientService {

        @GET("/characters/{characterId}/contacts/")
        Call<CrestContacts> getUserContacts(@Path("characterId") long characterId);

        @GET("/characters/{characterId}/")
        Call<CrestCharacter> getCharacter(@Path("characterId") long characterId);
    }

    private final LoginService login;

    public CrestClient(final String clientID, final String clientKey) {
        Validate.isTrue(StringUtils.isNotBlank(clientID));
        Validate.isTrue(StringUtils.isNotBlank(clientKey));

        this.login = CrestRetrofit.newLogin(clientID, clientKey).create(LoginService.class);
    }

    public CrestService authorize(final String authCode) throws IOException {
        final CrestToken token = obtainToken(this.login, authCode);
        final CrestCharacterStatus status = obtainStatus(this.login, token);
        final ClientService service = CrestRetrofit.newClient(token.getAccessToken()).create(ClientService.class);
        return new CrestService() {
            @Override
            public List<EveContact> getContacts() {
                try {
                    return CrestMapper.map(
                            service.getUserContacts(status.getCharacterID()).execute().body());
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            }

            @Override
            public EveCharacter getCharacter() {
                try {
                    return CrestMapper.map(
                        status,
                        service.getCharacter(status.getCharacterID()).execute().body());
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            }
        };
    }

    private static CrestToken obtainToken(final LoginService login, final String authCode) throws IOException {
        final Response<CrestToken> response = login.getUserToken("authorization_code", authCode).execute();
        if (!response.isSuccessful()) {
            throw new IOException("token request unsuccessful: " + response.message());
        }
        return response.body();
    }

    private static CrestCharacterStatus obtainStatus(final LoginService login, final CrestToken token) throws IOException {
        final Response<CrestCharacterStatus> response = login.getVerification(token.getAccessToken()).execute();
        if (!response.isSuccessful()) {
            throw new IOException("status request unsuccessful: " + response.message());
        }
        return response.body();
    }
}
