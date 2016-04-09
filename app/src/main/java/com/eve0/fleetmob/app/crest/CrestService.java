package com.eve0.fleetmob.app.crest;

import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestContacts;

import rx.Observable;

public interface CrestService {

    Observable<CrestContacts> getUserContacts();

    Observable<CrestCharacter> getCharacter();

}
