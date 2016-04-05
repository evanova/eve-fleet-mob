package com.eve0.fleetmob.crest.model;

import java.lang.*;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTContactItem {

    private double standing;

    private CRESTCharacter character;

    private CRESTContact contact;

    private String href;

    private String contactType;

    private boolean watched;

    private boolean blocked;

    public String getHref() {
        return href;
    }

    public CRESTCharacter getCharacter() {
        return character;
    }

    public CRESTContact getContact() {
        return contact;
    }

    public double getStanding() {
        return standing;
    }

    public String getContactType() {
        return contactType;
    }
}
