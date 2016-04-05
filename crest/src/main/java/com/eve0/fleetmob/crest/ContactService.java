package com.eve0.fleetmob.crest;

import com.eve0.fleetmob.crest.model.CRESTCharacter;
import com.eve0.fleetmob.crest.model.CRESTContacts;
import com.eve0.fleetmob.crest.model.CRESTToken;
import com.eve0.fleetmob.crest.model.CRESTVerification;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ContactService {
    @GET("/characters/{characterId}/contacts/")
    Observable<CRESTContacts> getUserContacts(@Path("characterId") long characterId);

    @GET("/characters/{characterId}/")
    Observable<CRESTCharacter> getCharacter(@Path("characterId") long characterId);

/*@GET
Observable<CRESTContacts> getUserContacts(@Url String nextPage);*/

    @POST("/oauth/token")
    Observable<CRESTToken> getUserToken(@Query("grant_type") String type, @Query("code") String code);

    @POST("/oauth/token")
    Observable<CRESTToken> getUserTokenUsingRefresh(@Query("grant_type") String type, @Query("refresh_token") String token);

    @GET("/oauth/verify")
    Observable<CRESTVerification> getVerification(@Header("Authorization") String token);
}
