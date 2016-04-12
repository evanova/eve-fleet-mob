package com.eve0.crest.retrofit;

import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestContacts;
import com.eve0.crest.model.CrestServerStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ClientService {

    @GET("/")
    Call<CrestServerStatus> getServerStatus();

    @GET("/characters/{characterId}/contacts/")
    Call<CrestContacts> getUserContacts(@Path("characterId") long characterId);

    @GET("/characters/{characterId}/")
    Call<CrestCharacter> getCharacter(@Path("characterId") long characterId);
}
