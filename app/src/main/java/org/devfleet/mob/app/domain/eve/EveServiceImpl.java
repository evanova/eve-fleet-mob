package org.devfleet.mob.app.domain.eve;

import org.devfleet.crest.CrestService;
import org.devfleet.crest.model.CrestSolarSystem;
import org.devfleet.crest.retrofit.CrestClient;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.model.EveServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class EveServiceImpl implements EveService {
    private static final Logger LOG = LoggerFactory.getLogger(EveServiceImpl.class);

    private final CrestClient client;
    private CrestService service = null;

    private EveCharacter character;

    public EveServiceImpl(final CrestClient client) {
        this.client = client;
        try {
            this.service = client.fromDefault();
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean setClientToken(final String refresh) {
        this.character = null;

        try {
            this.service = client.fromRefreshToken(refresh);
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
            this.service = client.fromAuthCode(authCode);
            return true;
        }
        catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public EveServerStatus getServerStatus() {
        return CrestMapper.map(this.service.getServerStatus());
    }

    @Override
    public List<EveContact> getContacts() {
        if (null == this.service) {
            return Collections.emptyList();
        }
        try {
            return CrestMapper.mapContacts(this.service.getContacts());
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getContacts: not authenticated");
            return Collections.emptyList();
        }
    }

    @Override
    public EveCharacter getCharacter() {
        if (null == this.service) {
            return null;
        }
        if (null == this.character) {
            this.character = getCharacter(this.service);
        }
        return this.character;
    }

    @Override
    public List<EveFitting> getFittings() {
        if (null == this.service) {
            return Collections.emptyList();
        }
        try {
            return CrestMapper.mapFittings(this.service.getFittings());
        }
        catch (IllegalStateException e) {
            LOG.debug(e.getLocalizedMessage(), e);
            LOG.warn("getFittings: not authenticated");
            return Collections.emptyList();
        }
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
