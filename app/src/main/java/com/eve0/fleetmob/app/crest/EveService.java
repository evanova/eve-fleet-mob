package com.eve0.fleetmob.app.crest;

import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import java.util.List;

public interface EveService {

    List<EveContact> getContacts();

    EveCharacter getCharacter();
}
