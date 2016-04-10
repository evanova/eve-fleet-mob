package com.eve0.crest.retrofit;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContact;
import com.eve0.crest.model.CrestContacts;
import com.eve0.crest.CrestService;
import com.eve0.crest.model.CrestServerStatus;
import com.eve0.crest.model.CrestToken;


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

        @GET("/")
        Call<CrestServerStatus> getServerStatus();

        @GET("/characters/{characterId}/contacts/")
        Call<CrestContacts> getUserContacts(@Path("characterId") long characterId);

        @GET("/characters/{characterId}/")
        Call<CrestCharacter> getCharacter(@Path("characterId") long characterId);
    }

    private final LoginService loginService;
    private final ClientService publicService;

    public CrestClient(final String clientID, final String clientKey) {
        Validate.isTrue(StringUtils.isNotBlank(clientID));
        Validate.isTrue(StringUtils.isNotBlank(clientKey));

        this.loginService = CrestRetrofit.newLogin(clientID, clientKey).create(LoginService.class);
        this.publicService = CrestRetrofit.newClient().create(ClientService.class);
    }

    public final CrestToken getToken(final String authCode) throws IOException {
        return obtainToken(this.loginService, authCode);
    }

    public final CrestService getService() throws IOException {
        return new CrestService() {
            @Override
            public CrestServerStatus getServerStatus() {
                try {
                    return publicService.getServerStatus().execute().body();
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            }

            @Override
            public CrestCharacterStatus getCharacterStatus() {
                throw new IllegalStateException("not authenticated");
            }

            @Override
            public CrestCharacter getCharacter() {
                throw new IllegalStateException("not authenticated");
            }

            @Override
            public List<CrestContact> getCharacterContacts() {
                throw new IllegalStateException("not authenticated");
            }
        };
    }

    public final CrestService getService(final String token) throws IOException {

        final CrestCharacterStatus status = obtainStatus(this.loginService, token);
        final ClientService service = CrestRetrofit.newClient(token).create(ClientService.class);
        return new CrestService() {
            @Override
            public List<CrestContact> getCharacterContacts() {
                try {
                    final CrestContacts contacts = service.getUserContacts(status.getCharacterID()).execute().body();
                    return contacts.getItems();
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            }

            @Override
            public CrestCharacter getCharacter() {
                try {
                    final CrestCharacter c = service.getCharacter(status.getCharacterID()).execute().body();
                    c.setAccessToken(token);
                    return c;
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            }

            @Override
            public CrestCharacterStatus getCharacterStatus() {
                return status;
            }

            @Override
            public CrestServerStatus getServerStatus() {
                try {
                    return service.getServerStatus().execute().body();
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

    private static CrestCharacterStatus obtainStatus(final LoginService login, final String token) throws IOException {
        final Response<CrestCharacterStatus> response = login.getVerification(token).execute();
        if (!response.isSuccessful()) {
            throw new IOException("status request unsuccessful: " + response.message());
        }
        return response.body();
    }
}
