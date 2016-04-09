package com.eve0.fleetmob.app.crest;

import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContacts;
import com.eve0.crest.model.CrestToken;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

class CrestClient {

    interface LoginService {

        @POST("/oauth/token")
        Call<CrestToken> getUserToken(@Query("grant_type") String type, @Query("code") String code);

        @POST("/oauth/token")
        Call<CrestToken> getUserTokenUsingRefresh(@Query("grant_type") String type, @Query("refresh_token") String token);

        @GET("/oauth/verify")
        Call<CrestCharacterStatus> getVerification(@Header("Authorization") String token);
    }

    interface CrestService {
        @GET("/characters/{characterId}/contacts/")
        Observable<CrestContacts> getUserContacts(@Path("characterId") long characterId);

        @GET("/characters/{characterId}/")
        Observable<CrestCharacter> getCharacter(@Path("characterId") long characterId);
    }



    private static final String CLIENT_ID = "84c122b173f94063a488c177d7e0b433";
    private static final String SECRET_KEY = "Ro0FkbQujNwNmWEXguGj8cRTpsRIPq7kX5e8qMNZ";

    private final LoginService login;

    public CrestClient(final String clientID, final String clientKey) {
        Validate.isTrue(StringUtils.isNotBlank(clientID));
        Validate.isTrue(StringUtils.isNotBlank(clientKey));

        this.login = CrestRetrofit.newLogin(clientID, clientKey).create(LoginService.class);
    }

    public ClientService authorize(final String authCode) {
        final CrestToken token = obtainToken(this.login, authCode);
        final CrestCharacterStatus status = obtainStatus(this.login, token);
        final CrestService service = CrestRetrofit.newClient(token.getAccessToken()).create(CrestService.class);
        return new ClientService() {
            @Override
            public Observable<CrestContacts> getUserContacts() {
                return service.getUserContacts(status.getCharacterID());
            }

            @Override
            public Observable<CrestCharacter> getCharacter() {
                return service.getCharacter(status.getCharacterID());
            }
        };
    }

    private static CrestToken obtainToken(final LoginService login, final String authCode) {
        try {
            final Response<CrestToken> response = login.getUserToken("authorization_code", authCode).execute();
            if (!response.isSuccessful()) {
                throw new IOException("token request unsuccessful: " + response.message());
            }
            return response.body();
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static CrestCharacterStatus obtainStatus(final LoginService login, final CrestToken token) {
        try {
            final Response<CrestCharacterStatus> response = login.getVerification(token.getAccessToken()).execute();
            if (!response.isSuccessful()) {
                throw new IOException("status request unsuccessful: " + response.message());
            }
            return response.body();
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
