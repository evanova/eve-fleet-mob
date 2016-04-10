package com.eve0.crest.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CrestServerStatus extends CrestEntity {

    @JsonProperty
    private String serverName;

    @JsonProperty
    private String serverVersion;

    @JsonProperty
    private Map<String, Integer> userCounts = new HashMap<>();

    public String getServerName() {
        return serverName;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getDustCount() {
        return getInt("dust");
    }

    public int getEveCount() {
        return getInt("eve");
    }

    private int getInt(final String key) {
        final Integer a = userCounts.get(key);
        return (null == a) ? 0 : a;
    }
}
