package org.devfleet.mob.app.domain.repository;

import org.devfleet.mob.app.model.EveCharacter;

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
}
