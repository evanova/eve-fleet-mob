package org.devfleet.mob.app.domain.eve;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.devfleet.crest.model.CrestCharacter;
import org.devfleet.crest.model.CrestContact;
import org.devfleet.crest.model.CrestCorporation;
import org.devfleet.crest.model.CrestFitting;
import org.devfleet.crest.model.CrestItem;
import org.devfleet.crest.model.CrestServerStatus;
import org.devfleet.crest.model.CrestSolarSystem;
import org.devfleet.crest.model.CrestWaypoint;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.model.EveServerStatus;
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
        returned.setToken(character.getRefreshToken());

        final CrestCorporation corporation = character.getCorporation();
        if (null != corporation) {
            returned.setCorporationID(corporation.getId());
            returned.setCorporationName(corporation.getName());
        }
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
        //returned.setBlocked(contact.isBlocked());
       // returned.setWatched(contact.isWatched());
        returned.setContactType(contact.getContactType());
        returned.setStanding(contact.getStanding());

        final CrestCorporation corporation = character.getCorporation();
        if (null != corporation) {
            returned.setCorporationID(corporation.getId());
            returned.setCorporationName(corporation.getName());
        }

        final CrestItem item = contact.getContact();
        if (null != item) {
            returned.setDescription(item.getDescription());
        }
        return returned;
    }

    public static List<EveContact> mapContacts(final List<CrestContact> contacts) {
        final List<EveContact> returned = new ArrayList<>();
        for (CrestContact c: contacts) {
            final EveContact mapped = map(c);
            if (null != mapped) {
                returned.add(mapped);
            }
        }
        return returned;
    }

    public static List<EveFitting> mapFittings(final List<CrestFitting> fittings) {
        final List<EveFitting> returned = new ArrayList<>();
        for (CrestFitting f: fittings) {
            returned.add(map(f));
        }
        return returned;
    }

    public static EveFitting map(final CrestFitting fitting) {
        final EveFitting returned = new EveFitting();
        returned.setId(fitting.getFittingID());
        returned.setName(fitting.getName());
        returned.setDescription(fitting.getDescription());
        returned.setShipName(fitting.getShip().getName());
        returned.setShipId(fitting.getShip().getId());
        return returned;
    }

    public static EveServerStatus map(final CrestServerStatus status) {
        if (null == status) {
            return null;
        }
        final EveServerStatus returned = new EveServerStatus();
        returned.setDustCount(status.getDustCount());
        returned.setDustOnline(status.getDustOnline());
        returned.setEveCount(status.getEveCount());
        returned.setEveOnline(status.getEveOnline());
        returned.setServerName(status.getServerName());
        returned.setServerVersion(status.getServerVersion());
        return returned;
    }

    public static EveLocation map(final CrestSolarSystem solarSystem) {
        final EveLocation returned = new EveLocation();
        returned.setSolarSystemID(solarSystem.getId());
        returned.setSolarSystemName(solarSystem.getName());
        return returned;
    }

    public static List<CrestWaypoint> map(final EveRoute route) {
        final List<CrestWaypoint> returned = new ArrayList<>(route.getLocations().size());
        for (EveLocation l: route.getLocations()) {
            final CrestWaypoint wp = new CrestWaypoint();
            final CrestItem item = new CrestItem();
            item.setId(l.getSolarSystemID());
            item.setName(l.getSolarSystemName());
            wp.setSolarSystem(item);
            returned.add(wp);
        }
        return returned;
    }
}
