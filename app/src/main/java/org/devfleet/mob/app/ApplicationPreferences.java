package org.devfleet.mob.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class ApplicationPreferences {

    private static final String KEY_TOKEN = "ApplicationPreferences.saved.tokens.";
    private static final String KEY_LAST = "ApplicationPreferences.saved.character";

    private static final String KEY_VERSION = "ApplicationPreferences.version";

    private final SharedPreferences preferences;

    public ApplicationPreferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public void setSavedCharacter(final long charID) {
        preferences.edit().putLong(KEY_LAST, charID).commit();
    }

    public long getSavedCharacter() {
        return preferences.getLong(KEY_LAST, -1);
    }

    public void setCharacterToken(final long charID, final String token) {
        preferences.edit().putString(KEY_TOKEN + charID, token).commit();
    }

    public String getCharacterToken(final long charID) {
        return preferences.getString(KEY_TOKEN + charID, null);
    }

    public void setCrestVersion(final String version) {
        preferences.edit().putString(KEY_VERSION, version).commit();
    }

    public String getCrestVersion() {
        return preferences.getString(KEY_VERSION, null);
    }
}
