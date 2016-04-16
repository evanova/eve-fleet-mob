package org.devfleet.mob.app.domain.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "characters")
final class CharacterEntity {

    @Id
    @Column
    private long id;

    @Column
    private long corporationId;

    @Column
    private long expires;

    @Column
    private String characterName;

    @Column
    private String refreshToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public long getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(long corporationId) {
        this.corporationId = corporationId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
