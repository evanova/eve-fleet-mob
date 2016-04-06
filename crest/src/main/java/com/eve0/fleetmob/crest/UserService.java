package com.eve0.fleetmob.crest;

import com.eve0.fleetmob.crest.model.CrestCharacter;
import com.eve0.fleetmob.crest.model.CrestContacts;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface UserService {
    @GET("/characters/{characterId}/contacts/")
    Observable<CrestContacts> getUserContacts(@Path("characterId") long characterId);

    @GET("/characters/{characterId}/")
    Observable<CrestCharacter> getCharacter(@Path("characterId") long characterId);

}
