package com.eve0.fleetmob.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Provides
    public ApplicationPreferences providePreferences() {
        return new ApplicationPreferences(this.context);
    }
}
