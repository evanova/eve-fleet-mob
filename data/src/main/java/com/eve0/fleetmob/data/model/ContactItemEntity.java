package com.eve0.fleetmob.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ContactItems")
public final class ContactItemEntity {

    @Id
    @Column
    private long id;

    @Column
    private long ownerID;

    @Column
    private float standing;

    @Column
    private String href;

    @Column
    private String contactType;

    @Column
    private long characterID;

    @Column
    private long contactID;

    @Column
    private boolean watched;

    @Column
    private boolean blocked;

    @Column
    private boolean synced;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getStanding() {
        return standing;
    }

    public void setStanding(float standing) {
        this.standing = standing;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(long characterID) {
        this.characterID = characterID;
    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }
}
