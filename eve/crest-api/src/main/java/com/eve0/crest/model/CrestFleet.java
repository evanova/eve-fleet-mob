package com.eve0.crest.model;

import java.util.List;

public class CrestFleet extends CrestEntity {

    private long fleetID;
    private String fleetName;

    private long ownerID;
    private List<Long> members;

}
