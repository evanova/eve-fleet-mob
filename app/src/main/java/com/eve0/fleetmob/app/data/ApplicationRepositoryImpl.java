package com.eve0.fleetmob.app.data;

import android.content.Context;

import com.eve0.fleetmob.app.model.EveCharacter;


import java.util.ArrayList;
import java.util.List;

class ApplicationRepositoryImpl implements ApplicationRepository {

    private final ApplicationDatabase database;

    public ApplicationRepositoryImpl(final Context context) {
        this.database = ApplicationDatabase.from(context);
    }

    public List<EveCharacter> listCharacters() {
        final List<CharacterEntity> entities = this.database.listCharacters();
        final List<EveCharacter> returned = new ArrayList<>();
        for (CharacterEntity e: entities) {
            returned.add(RepositoryMapper.map(e));
        }
        return returned;
    }

    public void addCharacter(final EveCharacter character) {
        this.database.addCharacter(RepositoryMapper.map(character));
    }


    public void deleteCharacter(final long charID) {
        this.database.removeCharacter(charID);
    }
}
