package com.eve0.fleetmob.app.data;

import com.eve0.fleetmob.app.model.EveCharacter;

import java.util.List;

public interface ApplicationRepository {

    List<EveCharacter> listCharacters();

    void addCharacter(final EveCharacter character);

    void deleteCharacter(final long charID);
}
