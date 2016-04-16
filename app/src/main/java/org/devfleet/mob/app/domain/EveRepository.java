package org.devfleet.mob.app.domain;

import org.devfleet.mob.app.model.EveCharacter;

import java.util.List;

public interface EveRepository {
    String getToken(final long charID);

    List<EveCharacter> listCharacters();

    EveCharacter getCharacter(final long charID);

    EveCharacter firstCharacter();

    void addCharacter(final EveCharacter character);

    void deleteCharacter(final long charID);
}
