package org.devfleet.mob.app.model;

public class EveLocation {

    private long regionID;
    private String regionName;

    private long solarSystemID;
    private String solarSystemName;

    private long holderID;
    private String holderName;

    private float securityStatus;

    private int shipKills;
    private int shipJumps;

    public long getRegionID() {
        return regionID;
    }

    public void setRegionID(long regionID) {
        this.regionID = regionID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public long getSolarSystemID() {
        return solarSystemID;
    }

    public void setSolarSystemID(long solarSystemID) {
        this.solarSystemID = solarSystemID;
    }

    public String getSolarSystemName() {
        return solarSystemName;
    }

    public void setSolarSystemName(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }

    public float getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(float securityStatus) {
        this.securityStatus = securityStatus;
    }

    public long getHolderID() {
        return holderID;
    }

    public void setHolderID(long holderID) {
        this.holderID = holderID;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getShipKills() {
        return shipKills;
    }

    public void setShipKills(int shipKills) {
        this.shipKills = shipKills;
    }

    public int getShipJumps() {
        return shipJumps;
    }

    public void setShipJumps(int shipJumps) {
        this.shipJumps = shipJumps;
    }
}
