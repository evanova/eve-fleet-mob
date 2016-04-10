package com.eve0.fleetmob.app;

import android.content.Context;

import com.eve0.fleetmob.app.inject.AbstractModule;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule extends AbstractModule {

    public ApplicationModule(final Context context) {
        super(context);
    }

    @Provides
    public Context provideContext() {
        return this.getContext();
    }

    @Provides
    public ApplicationPreferences providePreferences() {
        return new ApplicationPreferences(this.getContext());
    }
}
