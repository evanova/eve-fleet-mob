package com.eve0.fleetmob.app.model;

public class EveCharacter {

    private long ID;
    private String name;
    private String href;

    private boolean npc;

    private String refreshToken;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isNpc() {
        return npc;
    }

    public void setNpc(boolean npc) {
        this.npc = npc;
    }

    public String getToken() {
        return refreshToken;
    }

    public void setToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
