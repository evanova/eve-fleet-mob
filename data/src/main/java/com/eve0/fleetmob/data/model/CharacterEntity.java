package com.eve0.fleetmob.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "characters")
public class CharacterEntity {

    @Id
    @Column
    private long id;

    @Column
    private long corporationID;

    @Column
    private boolean isNPC;

    @Column
    private String href;

    @Column
    private String capsuleer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCorporationID() {
        return corporationID;
    }

    public void setCorporationID(long corporationID) {
        this.corporationID = corporationID;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public void setNPC(boolean NPC) {
        isNPC = NPC;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCapsuleer() {
        return capsuleer;
    }

    public void setCapsuleer(String capsuleer) {
        this.capsuleer = capsuleer;
    }


}
