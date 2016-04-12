package com.eve0.fleetmob.app.data;

import com.eve0.fleetmob.app.model.EveCharacter;

final class RepositoryMapper {

    private RepositoryMapper() {}

    public static EveCharacter map(final CharacterEntity entity) {
        if (null == entity) {
            return null;
        }

        final EveCharacter character = new EveCharacter();
        character.setID(entity.getId());
        character.setName(entity.getCharacterName());
        character.setToken(entity.getRefreshToken());
        return character;
    }

    public static CharacterEntity map(final EveCharacter character) {
        if (null == character) {
            return null;
        }

        final CharacterEntity entity = new CharacterEntity();
        entity.setId(character.getID());
        entity.setCharacterName(character.getName());
        entity.setRefreshToken(character.getToken());
        return entity;
    }
}
