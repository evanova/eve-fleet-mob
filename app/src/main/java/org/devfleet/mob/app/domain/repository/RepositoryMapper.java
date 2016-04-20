package org.devfleet.mob.app.domain.repository;

import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveLocation;

final class RepositoryMapper {

    private RepositoryMapper() {}

    public static EveCharacter map(final CharacterEntity charEntity, final CorporationEntity corpEntity) {
        if (null == charEntity) {
            return null;
        }

        final EveCharacter character = new EveCharacter();
        character.setID(charEntity.getId());
        character.setName(charEntity.getCharacterName());
        character.setToken(charEntity.getRefreshToken());

        if (null != corpEntity) {
            character.setCorporationID(corpEntity.getId());
            character.setCorporationName(corpEntity.getCorporationName());
        }
        return character;
    }

    public static CharacterEntity map(final EveCharacter character) {
        if (null == character) {
            return null;
        }

        final CharacterEntity entity = new CharacterEntity();
        entity.setId(character.getID());
        entity.setCorporationId(character.getCorporationID());
        entity.setCharacterName(character.getName());
        entity.setRefreshToken(character.getToken());
        return entity;
    }

    public static LocationEntity map(final EveLocation location) {
        if (null == location) {
            return null;
        }
        final LocationEntity entity = new LocationEntity();

        entity.setId(location.getSolarSystemID());
        entity.setName(location.getSolarSystemName());

        entity.setRegionId(location.getRegionID());
        entity.setRegionName(location.getRegionName());

        entity.setHolderId(location.getHolderID());
        entity.setHolderName(location.getHolderName());

        entity.setSecurityStatus(location.getSecurityStatus());
        return entity;
    }

    public static EveLocation map(final LocationEntity entity) {
        if (null == entity) {
            return null;
        }

        final EveLocation location = new EveLocation();
        location.setSolarSystemID(entity.getId());
        location.setSolarSystemName(entity.getName());
        location.setHolderID(entity.getRegionId());
        location.setRegionName(entity.getRegionName());
        location.setSecurityStatus(entity.getSecurityStatus());
        location.setHolderID(entity.getHolderId());
        location.setHolderName(entity.getHolderName());

        return location;
    }
}
