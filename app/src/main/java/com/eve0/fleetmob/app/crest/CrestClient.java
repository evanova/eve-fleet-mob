package com.eve0.fleetmob.app.crest;

import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContacts;
import com.eve0.crest.model.CrestToken;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;
import com.eve0.fleetmob.app.util.RX;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public final class CrestClient {

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
        Observable<CrestContacts> getUserContacts(@Path("characterId") long characterId);

        @GET("/characters/{characterId}/")
        Observable<CrestCharacter> getCharacter(@Path("characterId") long characterId);
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
            public Observable<List<EveContact>> getContacts() {
                return RX
                        .scheduled(service.getUserContacts(status.getCharacterID())
                        .map(contacts -> CrestMapper.map(contacts)));
            }

            @Override
            public Observable<EveCharacter> getCharacter() {
                return RX
                        .scheduled(service.getCharacter(status.getCharacterID())
                        .map(c -> CrestMapper.map(status, c)));
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
