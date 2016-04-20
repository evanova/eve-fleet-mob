package org.devfleet.mob.app.domain.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "locations")
final class LocationEntity {

    @Id
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private long regionId;

    @Column
    private String regionName;

    @Column
    private float securityStatus;

    @Column
    private String holderName;

    @Column
    private long holderId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public float getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(float securityStatus) {
        this.securityStatus = securityStatus;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public long getHolderId() {
        return holderId;
    }

    public void setHolderId(long holderId) {
        this.holderId = holderId;
    }
}
