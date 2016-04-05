package com.eve0.fleetmob.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ContactItems")
public final class ContactItemEntity {

    /*

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY, "
            + OWNER_COLUMN + " REAL, "
            + STANDING_COLUMN + " REAL, "
            + HREF_COLUMN + " TEXT, "
            + CONTACT_TYPE_COLUMN + " TEXT, "
            + CHARACTER_ID_COLUMN + " INTEGER, "
            + CONTACT_ID_COLUMN + " INTEGER, "
            + WATCHED_COLUMN + " BOOLEAN, "
            + BLOCKED_COLUMN + " BOOLEAN, "
            + SYNCED_COLUMN + " BOOLEAN, "
            + " FOREIGN KEY (" + CONTACT_ID_COLUMN + ") REFERENCES "
            + ContactEntry.TABLE_NAME + " (" + ContactEntry._ID + "), "
            + " FOREIGN KEY (" + CHARACTER_ID_COLUMN + ") REFERENCES "
            + CharacterEntry.TABLE_NAME + " (" + CharacterEntry._ID + "), "
            + " FOREIGN KEY (" + OWNER_COLUMN + ") REFERENCES "
            + UsersEntry.TABLE_NAME + " (" + UsersEntry._ID + "), "
            + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";*/

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
