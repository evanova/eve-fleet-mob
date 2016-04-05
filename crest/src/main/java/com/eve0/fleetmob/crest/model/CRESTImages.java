package com.eve0.fleetmob.crest.model;


/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTImages {

    //@SerializedName("32x32")
    private CRESTReference ref32;

    //@SerializedName("64x64")
    private CRESTReference ref64;

    //@SerializedName("128x128")
    private CRESTReference ref128;

    //@SerializedName("256x256")
    private CRESTReference ref256;

    public CRESTReference get128() {
        return ref128;
    }

    public CRESTReference get64() {
        return ref64;
    }

    public CRESTReference get32() {
        return ref32;
    }

    public CRESTReference get256() {
        return ref256;
    }
}
