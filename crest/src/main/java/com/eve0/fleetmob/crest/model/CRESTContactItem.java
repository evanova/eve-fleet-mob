package com.eve0.fleetmob.crest.model;

import java.lang.*;

public final class CrestContactItem extends CrestEntity {

    private double standing;

    private CrestCharacter character;

    private CrestContact contact;

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

    public CrestContact getContact() {
        return contact;
    }

    public double getStanding() {
        return standing;
    }

    public String getContactType() {
        return contactType;
    }
}
