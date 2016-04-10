package com.eve0.fleetmob.app.crest;

import com.eve0.crest.CrestService;
import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContact;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

final class CrestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(CrestMapper.class);

    private CrestMapper() {}

    public static EveService map(final CrestService service) {
        return new EveService() {
            @Override
            public List<EveContact> getContacts() {
                return map(service.getCharacterContacts());
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
        returned.setAccessToken(character.getAccessToken());
        return returned;
    }

    public static EveContact map(final CrestContact contact) {
        if (null == contact.getCharacter()) {
            LOG.warn("Contact has no character {}", ToStringBuilder.reflectionToString(contact));
            return null;
        }

        final EveContact returned = new EveContact();
        final CrestCharacter character = contact.getCharacter();

        returned.setName(character.getName());
        returned.setID(character.getId());
        returned.setHref(contact.getHref());
        return returned;
    }

    public static List<EveContact> map(final List<CrestContact> contacts) {
        final List<EveContact> returned = new ArrayList<>();
        for (CrestContact c: contacts) {
            final EveContact mapped = map(c);
            if (null != mapped) {
                returned.add(mapped);
            }
        }
        return returned;
    }
}
