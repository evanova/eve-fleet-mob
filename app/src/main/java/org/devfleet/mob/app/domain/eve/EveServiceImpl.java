package org.devfleet.mob.app.domain.eve;

import org.devfleet.crest.CrestService;
import org.devfleet.crest.model.CrestContact;
import org.devfleet.crest.model.CrestItem;
import org.devfleet.crest.model.CrestSolarSystem;
import org.devfleet.crest.retrofit.CrestClient;
import org.devfleet.dotlan.DotlanOptions;
import org.devfleet.dotlan.DotlanRoute;
import org.devfleet.dotlan.DotlanService;
import org.devfleet.dotlan.impl.DotlanServiceImpl;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.model.EveServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EveServiceImpl implements EveService {

    private static final Logger LOG = LoggerFactory.getLogger(EveServiceImpl.class);

    private final CrestClient client;

    private CrestService crest = null;
    private DotlanService dotlan = null;

    private EveCharacter character;

    public EveServiceImpl(final CrestClient client) {
        this.client = client;
        this.dotlan = new DotlanServiceImpl();

        try {
            this.crest = client.fromDefault();
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean setClientToken(final String refresh) {
        this.character = null;

        try {
            this.crest = client.fromRefreshToken(refresh);
            return true;
        }
        catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    public boolean setClientAuth(final String authCode) {
        this.character = null;

        try {
            this.crest = client.fromAuthCode(authCode);
            return true;
        }
        catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public EveServerStatus getServerStatus() {
        return CrestMapper.map(this.crest.getServerStatus());
    }

    @Override
    public List<EveContact> getContacts() {
        if (null == this.crest) {
            return Collections.emptyList();
        }
        try {
            return CrestMapper.mapContacts(this.crest.getContacts());
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getContacts: not authenticated");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean addContact(final EveContact contact) {
        if (null == this.crest) {
            return false;
        }
        try {
            final CrestContact c = new CrestContact();
            c.setContactType(contact.getContactType());
            c.setStanding(contact.getStanding());

            final CrestItem item = new CrestItem();
            item.setId(contact.getID());
            c.setContact(item);
            return this.crest.addContact(c);
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("addContact: not authenticated");
            return false;
        }
    }

    @Override
    public boolean removeContact(final long contactID) {
        if (null == this.crest) {
            return false;
        }
        try {
            return this.crest.deleteContact(contactID);
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("removeContact: not authenticated");
            return false;
        }
    }

    @Override
    public EveCharacter getCharacter() {
        if (null == this.crest) {
            return null;
        }
        if (null == this.character) {
            this.character = getCharacter(this.crest);
        }
        return this.character;
    }

    @Override
    public List<EveFitting> getFittings() {
        if (null == this.crest) {
            return Collections.emptyList();
        }
        try {
            return CrestMapper.mapFittings(this.crest.getFittings());
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getFittings: not authenticated");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean setRoute(final EveRoute route) {
        if (null == this.crest) {
            return false;
        }
        try {
            return this.crest.setWaypoints(CrestMapper.map(route));
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getCharacter: not authenticated");
            return false;
        }
    }

    @Override
    public boolean addRoute(final EveRoute route) {
        if (null == this.crest) {
            return false;
        }
        try {
            return this.crest.addWaypoints(CrestMapper.map(route));
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getCharacter: not authenticated");
            return false;
        }
    }

    @Override
    public EveRoute getRoute(final EveRoute route) {
        final DotlanOptions options = DotlanMapper.options(route);

        switch (route.getType()) {
            case EveRoute.HIGHSEC:
                return DotlanMapper.transform(dotlan.getHighSecRoute(options), route.getType());
            case EveRoute.LOWSEC:
                return DotlanMapper.transform(dotlan.getLowSecRoute(options), route.getType());
            case EveRoute.FASTEST:
            default:
                return DotlanMapper.transform(dotlan.getFastestRoute(options), route.getType());
        }
    }

    @Override
    public List<EveLocation> getLocations() {
        final List<CrestSolarSystem> systems = this.crest.getLocations();
        if (null == systems) {
            LOG.error("Unable to retrieve CREST solar systems");
            return Collections.emptyList();
        }
        final List<EveLocation> locations = new ArrayList<>(systems.size());
        for (CrestSolarSystem s: systems) {
            locations.add(CrestMapper.map(s));
            LOG.error("getLocations: {}", s.getName());
        }
        return locations;
    }

    private EveCharacter getCharacter(final CrestService service) {
        if (null == service) {
            return null;
        }
        try {
            final EveCharacter character = CrestMapper.map(service.getCharacter());
            final CrestSolarSystem location = service.getLocation();
            if (null != location) {
                character.setLocationId(location.getId());
                character.setLocationName(location.getName());
            }
            return character;
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getCharacter: not authenticated");
            return null;
        }
    }
}
