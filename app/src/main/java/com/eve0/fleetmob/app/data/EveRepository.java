package com.eve0.fleetmob.app.data;

import com.eve0.fleetmob.app.model.EveCharacter;

import java.util.List;

public interface EveRepository {

    List<EveCharacter> listCharacters();

    EveCharacter getCharacter(final long charID);

    EveCharacter firstCharacter();

    void addCharacter(final EveCharacter character);

    void deleteCharacter(final long charID);
}
