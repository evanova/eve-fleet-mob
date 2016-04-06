package com.eve0.fleetmob.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CrestCharacter extends CrestItem {

    @JsonProperty
    private boolean isNPC;

    @JsonProperty
    private CrestCorporation corporation;

    @JsonProperty("capsuleer")
    @JsonDeserialize(using = RefDeserializer.class)
    private String capsuleerRef;

    public boolean getNPC() {
        return isNPC;
    }

    public CrestCorporation getCorporation() {
        return corporation;
    }

    public String getCapsuleerRef() {
        return capsuleerRef;
    }
}
