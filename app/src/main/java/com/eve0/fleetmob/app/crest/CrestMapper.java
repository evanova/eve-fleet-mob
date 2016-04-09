package com.eve0.fleetmob.app.crest;

import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContactItem;
import com.eve0.crest.model.CrestContacts;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import java.util.ArrayList;
import java.util.List;

final class CrestMapper {
    private CrestMapper() {}

    public static EveCharacter map(final CrestCharacterStatus status, final CrestCharacter character) {
        final EveCharacter returned = new EveCharacter();
        returned.setName(status.getCharacterName());
        returned.setID(status.getCharacterID());
        returned.setNpc(character.getNPC());
        returned.setHref(character.getCapsuleerRef());
        return returned;
    }

    public static EveContact map(final CrestContactItem contact) {
        final EveContact returned = new EveContact();

        return returned;
    }

    public static List<EveContact> map(final CrestContacts contacts) {
        final List<EveContact> returned = new ArrayList<>();
        for (CrestContactItem c: contacts.getItems()) {
            returned.add(map(c));
        }
        return returned;
    }

}
