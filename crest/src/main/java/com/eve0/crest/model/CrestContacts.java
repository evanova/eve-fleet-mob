package com.eve0.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public final class CrestContacts extends CrestEntity {

    private int pageCount;

    private List<CrestContactItem> items;

    @JsonProperty
    @JsonDeserialize(using = RefDeserializer.class)
    private String next;

    private int totalCount;

    public int getPageCount() {
        return pageCount;
    }

    public List<CrestContactItem> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public String getNext() {
        return next;
    }

}
