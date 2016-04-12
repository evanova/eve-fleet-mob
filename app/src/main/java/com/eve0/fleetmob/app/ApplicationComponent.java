package com.eve0.fleetmob.app;

import com.eve0.fleetmob.app.eve.EveModule;
import com.eve0.fleetmob.app.data.EveRepositoryModule;
import com.eve0.fleetmob.app.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, EveModule.class, EveRepositoryModule.class})
@Singleton
public interface ApplicationComponent {

    void inject(final MainActivity activity);
}
