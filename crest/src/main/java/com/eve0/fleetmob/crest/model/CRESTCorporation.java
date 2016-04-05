package com.eve0.fleetmob.crest.model;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTCorporation {

    private String name;

    private boolean isNPC;

    private String href;

    private CRESTImages logo;

    private long id;

    public CRESTImages getLogo() {
        return logo;
    }

    public long getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }


}
