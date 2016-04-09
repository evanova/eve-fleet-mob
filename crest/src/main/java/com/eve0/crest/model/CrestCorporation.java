package com.eve0.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestCorporation extends CrestItem {

    @JsonProperty
    private boolean isNPC;

    //FIXME maybe logo

    public boolean getNPC() {
        return isNPC;
    }
}
