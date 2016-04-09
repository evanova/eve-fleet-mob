package com.eve0.crest.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CrestType extends CrestItem {

    static class AttributeWrapper {

        @JsonProperty
        private com.eve0.crest.model.CrestDogmaAttribute attribute;

        @JsonProperty
        private double value;
    }

    static class EffectWrapper {

        @JsonProperty
        private com.eve0.crest.model.CrestDogmaEffect effect;

        @JsonProperty
        private boolean isDefault;
    }

    static class Dogma {

        private List<com.eve0.crest.model.CrestDogmaAttribute> attributes;
        private List<com.eve0.crest.model.CrestDogmaEffect> effects;

        @JsonSetter
        public void setAttributes(List<AttributeWrapper> attributes) {
            this.attributes = new ArrayList<>(attributes.size());
            for (AttributeWrapper w: attributes) {
                w.attribute.setCurrentValue(w.value);
                this.attributes.add(w.attribute);
            }
        }

        @JsonSetter
        public void setEffects(List<EffectWrapper> effects) {
            this.effects = new ArrayList<>(effects.size());
            for (EffectWrapper w: effects) {
                w.effect.setDefaultEffect(w.isDefault);
                this.effects.add(w.effect);
            }
        }
    }

    @JsonProperty
    private double capacity;

    @JsonProperty
    private double portionSize;

    @JsonProperty
    private double volume;

    @JsonProperty
    private double radius;

    @JsonProperty
    private double mass;

    @JsonProperty
    private Dogma dogma;

    @JsonProperty
    private long iconID;

    public double getCapacity() {
        return capacity;
    }

    public double getPortionSize() {
        return portionSize;
    }

    public double getVolume() {
        return volume;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public List<com.eve0.crest.model.CrestDogmaAttribute> getAttributes() {
        return (null == dogma) ? null : dogma.attributes;
    }

    public List<com.eve0.crest.model.CrestDogmaEffect> getEffects() {
        return (null == dogma) ? null : dogma.effects;
    }
}
