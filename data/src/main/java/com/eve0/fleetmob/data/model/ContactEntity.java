package com.eve0.fleetmob.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "contacts")
public final class ContactEntity {

    @Id
    @Column
    private long id;

    @Column
    private String href;

    @Column
    private String name;
}
