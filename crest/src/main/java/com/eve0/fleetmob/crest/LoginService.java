package com.eve0.fleetmob.crest;

import com.eve0.fleetmob.crest.model.CRESTToken;
import com.eve0.fleetmob.crest.model.CRESTVerification;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by flibuste on 05-Apr-2016.
 */
public interface LoginService {

    //    getUserToken("refresh_token", REFRESH_TOKEN)
    //    getUserToken("authorization_code", AUTH_CODE)

    @POST("/oauth/token")
    Observable<CRESTToken> getUserToken(@Query("grant_type") String type, @Query("code") String code);

    @POST("/oauth/token")
    Observable<CRESTToken> getUserTokenUsingRefresh(@Query("grant_type") String type, @Query("refresh_token") String token);

    @GET("/oauth/verify")
    Observable<CRESTVerification> getVerification(@Header("Authorization") String token);
}
