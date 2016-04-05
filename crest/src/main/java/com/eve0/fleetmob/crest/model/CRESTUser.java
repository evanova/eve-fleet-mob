package com.eve0.fleetmob.crest.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTUser {

    private long characterId;

    private String name;

    private String expiration;

    private String accessToken;

    private String refreshToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getCharacterId() {
        return characterId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getExpiration() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            return simpleDateFormat.parse(this.expiration);
        }
        catch(Exception ex) {
            return new Date();
        }
    }
}
