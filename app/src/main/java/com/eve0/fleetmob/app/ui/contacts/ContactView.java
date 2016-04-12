package com.eve0.fleetmob.app.ui.contacts;

import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import java.util.List;

public interface ContactView {

    void showContacts(final EveCharacter character, final List<EveContact> contacts);

}
