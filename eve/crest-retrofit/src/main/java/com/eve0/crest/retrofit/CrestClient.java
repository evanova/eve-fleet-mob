package com.eve0.crest.retrofit;

import com.eve0.crest.CrestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class CrestClient {

    private final String clientID;
    private final String clientKey;

    public CrestClient(final String clientID, final String clientKey) {
        this.clientID = clientID;
        this.clientKey = clientKey;
    }

    public CrestService fromRefreshToken(final String token) throws IOException {
        final CrestServiceImpl service = new CrestServiceImpl(this.clientID, this.clientKey);
        service.setRefreshToken(token);
        return service;
    }


    public CrestService fromAuthCode(final String authCode) throws IOException {
        final CrestServiceImpl service = new CrestServiceImpl(this.clientID, this.clientKey);
        service.setAuthCode(authCode);
        return service;
    }
}
