package com.eve0.fleetmob.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "fleet")
public class FleetEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private long ownerID;

}
