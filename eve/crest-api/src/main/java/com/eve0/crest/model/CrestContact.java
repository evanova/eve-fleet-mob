package com.eve0.crest.model;

public final class CrestContact extends CrestItem {

    private double standing;

    private CrestCharacter character;

    private CrestContact contact;

    private String contactType;

    private boolean watched;

    private boolean blocked;

    public double getStanding() {
        return standing;
    }

    public void setStanding(double standing) {
        this.standing = standing;
    }

    public CrestCharacter getCharacter() {
        return character;
    }

    public void setCharacter(CrestCharacter character) {
        this.character = character;
    }

    public CrestContact getContact() {
        return contact;
    }

    public void setContact(CrestContact contact) {
        this.contact = contact;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
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
}
