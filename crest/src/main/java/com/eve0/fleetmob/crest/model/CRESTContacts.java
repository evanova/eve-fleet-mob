package com.eve0.fleetmob.crest.model;

import java.util.List;

/**
 * Created by w9jds on 3/26/2016.
 */
public final class CRESTContacts {

    private int pageCount;

    private List<CRESTContactItem> items;

    private CRESTReference next;

    private int totalCount;

    public int getPageCount() {
        return pageCount;
    }

    public List<CRESTContactItem> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public CRESTReference getNext() {
        return next;
    }

}
