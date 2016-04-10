package com.eve0.fleetmob.app.data;

import com.eve0.fleetmob.app.model.EveCharacter;

final class RepositoryMapper {

    private RepositoryMapper() {}

    public static EveCharacter map(final CharacterEntity entity) {
        final EveCharacter character = new EveCharacter();
        character.setID(entity.getId());
        character.setName(entity.getCharacterName());

        return character;
    }

    public static CharacterEntity map(final EveCharacter character) {
        final CharacterEntity entity = new CharacterEntity();
        entity.setId(character.getID());
        entity.setCharacterName(character.getName());
        entity.setToken(character.getAccessToken());
        return entity;
    }
}
