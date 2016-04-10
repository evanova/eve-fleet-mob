package com.eve0.fleetmob.app.data;

import android.content.Context;

import com.eve0.fleetmob.app.inject.AbstractModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule extends AbstractModule {

    public RepositoryModule(Context context) {
        super(context);
    }

    @Provides
    @Singleton
    ApplicationRepository provideData() {
        return new ApplicationRepositoryImpl(getContext());
    }
}
