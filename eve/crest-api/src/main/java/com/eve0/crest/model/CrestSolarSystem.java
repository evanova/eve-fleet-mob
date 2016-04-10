package com.eve0.crest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CrestSolarSystem extends com.eve0.crest.model.CrestItem {

    @JsonProperty("stats")
    @JsonDeserialize(using = RefDeserializer.class)
    private String statsRef;

    @JsonProperty("planets")
    @JsonDeserialize(using = RefDeserializer.class)
    private List<String> planetsRef;

    @JsonProperty
    private double securityStatus;

    @JsonProperty
    private String securityClass;

    @JsonProperty
    private CrestPosition position;

    @JsonProperty
    private com.eve0.crest.model.CrestItem sovereignty;

    @JsonProperty
    private com.eve0.crest.model.CrestItem constellation;

    public String getStatRef() {
        return statsRef;
    }

    public List<String> getPlanetRefs() {
        return planetsRef;
    }

    public double getSecurityStatus() {
        return securityStatus;
    }

    public String getSecurityClass() {
        return securityClass;
    }

    public CrestPosition getPosition() {
        return position;
    }

    public com.eve0.crest.model.CrestItem getSovereignty() {
        return sovereignty;
    }

    public com.eve0.crest.model.CrestItem getConstellation() {
        return constellation;
    }
}
