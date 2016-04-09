package com.eve0.fleetmob.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class ApplicationPreferences {

    private static final String KEY_LAST_AUTH = "ApplicationPreferences.saved.token";

    private final SharedPreferences preferences;

    public ApplicationPreferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public String getAuthenticationToken() {
        return preferences.getString(KEY_LAST_AUTH, null);
    }

    public void setAuthenticationToken(final String token) {
        preferences.edit().putString(KEY_LAST_AUTH, token).commit();
    }
}
