package com.eve0.fleetmob.crest.model;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTVerification {

    private long characterID;
    private String characterName;
    private String scopes;
    private String expiresOn;
    private String tokenType;
    private String characterOwnerHash;

    public long getCharacterID() {
        return characterID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getScopes() {
        return scopes;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getCharacterOwnerHash() {
        return characterOwnerHash;
    }
}
