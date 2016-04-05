package com.eve0.fleetmob.crest.model;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTCharacter {

    private String name;

    private CRESTCorporation corporation;

    private boolean isNPC;

    private String href;

    private CRESTReference capsuleer;

    private CRESTImages portrait;

    private long id;

    public long getId() {
        return id;
    }

    public CRESTCorporation getCorporation() {
        return corporation;
    }

    public CRESTImages getPortrait() {
        return portrait;
    }

    public CRESTReference getCapsuleer() {
        return capsuleer;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public void setCapsuleer(String href) {
        capsuleer = new CRESTReference(href);
    }
}
