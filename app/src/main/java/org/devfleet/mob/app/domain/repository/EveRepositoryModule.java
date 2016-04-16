package org.devfleet.mob.app.domain.repository;

import android.content.Context;

import org.devfleet.mob.app.domain.EveRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class EveRepositoryModule {

    private final EveRepository repository;

    public EveRepositoryModule(Context context) {
        this.repository = new EveRepositoryImpl(context);
    }

    @Provides
    @Singleton
    EveRepository provideData() {
        return this.repository;
    }
}
