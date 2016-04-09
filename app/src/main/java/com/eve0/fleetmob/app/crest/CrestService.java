package com.eve0.fleetmob.app.crest;

import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import java.util.List;

import rx.Observable;

public interface CrestService {

    Observable<List<EveContact>> getContacts();

    Observable<EveCharacter> getCharacter();

}
