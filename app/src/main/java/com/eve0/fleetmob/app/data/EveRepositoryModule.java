package com.eve0.fleetmob.app.data;

import android.content.Context;

import com.eve0.fleetmob.app.inject.AbstractModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EveRepositoryModule extends AbstractModule {

    public EveRepositoryModule(Context context) {
        super(context);
    }

    @Provides
    @Singleton
    EveRepository provideData() {
        return new EveRepositoryImpl(getContext());
    }
}
