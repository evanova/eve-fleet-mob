package com.eve0.fleetmob.crest.model;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTToken {

    private String accessToken;

    private String tokenType;

    private long expiresIn;

    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
