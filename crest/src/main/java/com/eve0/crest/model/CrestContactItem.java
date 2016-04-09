package com.eve0.crest.model;

import java.lang.*;

public final class CrestContactItem extends CrestEntity {

    private double standing;

    private CrestCharacter character;

    private com.eve0.crest.model.CrestContact contact;

    private String href;

    private String contactType;

    private boolean watched;

    private boolean blocked;

    public String getHref() {
        return href;
    }

    public CrestCharacter getCharacter() {
        return character;
    }

    public com.eve0.crest.model.CrestContact getContact() {
        return contact;
    }

    public double getStanding() {
        return standing;
    }

    public String getContactType() {
        return contactType;
    }
}
