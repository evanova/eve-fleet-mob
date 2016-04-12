package com.eve0.fleetmob.app.eve;

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

    public static EveCharacter map(final CrestCharacter character) {
        final EveCharacter returned = new EveCharacter();
        returned.setName(character.getName());
        returned.setID(character.getId());
        returned.setNpc(character.getNPC());
        returned.setHref(character.getCapsuleerRef());
        returned.setToken(character.getRefreshToken());
        return returned;
    }

    public static EveContact map(final CrestContact contact) {
        if (null == contact.getCharacter()) {
            //character was bio-massed most likely
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
