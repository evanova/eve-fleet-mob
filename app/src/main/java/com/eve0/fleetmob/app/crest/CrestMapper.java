package com.eve0.fleetmob.app.crest;

import com.eve0.crest.CrestService;
import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContact;
import com.eve0.crest.model.CrestContacts;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import java.util.ArrayList;
import java.util.List;

final class CrestMapper {
    private CrestMapper() {}

    public static EveService map(final CrestService service) {
        return new EveService() {
            @Override
            public List<EveContact> getContacts() {
                return map(service.getContacts());
            }

            @Override
                public EveCharacter getCharacter() {
                    final CrestCharacterStatus status = service.getCharacterStatus();
                    final CrestCharacter character = service.getCharacter();
                    return map(status, character);
            }
        };
    }

    public static EveCharacter map(final CrestCharacterStatus status, final CrestCharacter character) {
        final EveCharacter returned = new EveCharacter();
        returned.setName(status.getCharacterName());
        returned.setID(status.getCharacterID());
        returned.setNpc(character.getNPC());
        returned.setHref(character.getCapsuleerRef());
        return returned;
    }

    public static EveContact map(final CrestContact contact) {
        final EveContact returned = new EveContact();

        return returned;
    }
    public static List<EveContact> map(final List<CrestContact> contacts) {
        final List<EveContact> returned = new ArrayList<>();
        for (CrestContact c: contacts) {
            returned.add(map(c));
        }
        return returned;
    }
    public static List<EveContact> map(final CrestContacts contacts) {
        final List<EveContact> returned = new ArrayList<>();
        for (CrestContact c: contacts.getItems()) {
            returned.add(map(c));
        }
        return returned;
    }

}
