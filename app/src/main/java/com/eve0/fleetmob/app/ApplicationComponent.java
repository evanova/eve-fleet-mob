package com.eve0.fleetmob.app;

import com.eve0.fleetmob.app.crest.CrestModule;
import com.eve0.fleetmob.app.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, CrestModule.class})
@Singleton
public interface ApplicationComponent {

    void inject(final MainActivity activity);
}
