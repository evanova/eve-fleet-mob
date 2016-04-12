package com.eve0.fleetmob.app.data;

import android.content.Context;

import com.eve0.fleetmob.app.model.EveCharacter;


import java.util.ArrayList;
import java.util.List;

class EveRepositoryImpl implements EveRepository {

    private final EveDatabase database;

    public EveRepositoryImpl(final Context context) {
        this.database = EveDatabase.from(context);
    }

    public List<EveCharacter> listCharacters() {
        final List<CharacterEntity> entities = this.database.listCharacters();
        final List<EveCharacter> returned = new ArrayList<>();
        for (CharacterEntity e: entities) {
            returned.add(RepositoryMapper.map(e));
        }
        return returned;
    }

    @Override
    public EveCharacter getCharacter(long charID) {
        return RepositoryMapper.map(this.database.getCharacter(charID));
    }

    @Override
    public EveCharacter firstCharacter() {
        final List<CharacterEntity> entities = this.database.listCharacters(1);
        return (entities.isEmpty()) ? null : RepositoryMapper.map(entities.get(0));
    }

    public void addCharacter(final EveCharacter character) {
        this.database.addCharacter(RepositoryMapper.map(character));
    }


    public void deleteCharacter(final long charID) {
        this.database.removeCharacter(charID);
    }
}
