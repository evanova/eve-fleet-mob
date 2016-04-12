package com.eve0.fleetmob.app.ui.drawer;

import com.eve0.fleetmob.app.model.EveCharacter;

import java.util.List;

public interface DrawerView {

    void openDrawer();

    void closeDrawer();

    boolean isDrawerOpened();

    void setCharacters(final List<EveCharacter> characters);

    void setSelectedCharacter(final EveCharacter character);
}
