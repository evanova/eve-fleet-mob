package com.eve0.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestMarketPrice extends CrestEntity {

    @JsonProperty
    private double adjustedPrice;

    @JsonProperty
    private double averagePrice;

    @JsonProperty
    private com.eve0.crest.model.CrestItem type;

    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public com.eve0.crest.model.CrestItem getType() {
        return type;
    }
}
