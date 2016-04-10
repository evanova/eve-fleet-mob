package com.eve0.fleetmob.app.crest;

import com.eve0.crest.CrestService;
import com.eve0.crest.retrofit.CrestClient;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.model.EveContact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class EveServiceImpl implements EveService {
    private static final Logger LOG = LoggerFactory.getLogger(EveServiceImpl.class);

    private final CrestClient client;
    private CrestService service = null;

    public EveServiceImpl(final CrestClient client) {
        this.client = client;
    }

    public boolean setClientToken(final String token) {
        try {
            this.service = client.getService(token);
            return true;
        }
        catch (IOException e) {
            this.service = null;
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    public boolean setClientAuth(final String authCode) {
        try {
            setClientToken(client.getToken(authCode).getAccessToken());
            return true;
        }
        catch (IOException e) {
            this.service = null;
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    public List<EveContact> getContacts() {
        if (null == this.service) {
            return Collections.emptyList();
        }
        return CrestMapper.map(this.service.getCharacterContacts());
    }

    @Override
    public EveCharacter getCharacter() {
        if (null == this.service) {
            return null;
        }
        return CrestMapper.map(this.service.getCharacterStatus(), this.service.getCharacter());
    }
}
