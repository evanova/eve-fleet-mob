package com.eve0.fleetmob.app.ui;

import android.net.Uri;

import com.eve0.fleetmob.app.model.EveCharacter;

public interface LoginView {

    void showLogin(final Uri uri);

    void showCharacter(final EveCharacter pilot);

}
