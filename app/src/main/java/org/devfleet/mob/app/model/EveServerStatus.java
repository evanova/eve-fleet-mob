package org.devfleet.mob.app.model;

public class EveServerStatus {

    private String serverName;
    private String serverVersion;

    private int dustCount;
    private boolean dustOnline;

    private int eveCount;
    private boolean eveOnline;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public int getDustCount() {
        return dustCount;
    }

    public void setDustCount(int dustCount) {
        this.dustCount = dustCount;
    }

    public boolean isDustOnline() {
        return dustOnline;
    }

    public void setDustOnline(boolean dustOnline) {
        this.dustOnline = dustOnline;
    }

    public int getEveCount() {
        return eveCount;
    }

    public void setEveCount(int eveCount) {
        this.eveCount = eveCount;
    }

    public boolean isEveOnline() {
        return eveOnline;
    }

    public void setEveOnline(boolean eveOnline) {
        this.eveOnline = eveOnline;
    }
}
