package com.eve0.fleetmob.app;

import com.eve0.fleetmob.app.crest.CrestModule;
import com.squareup.leakcanary.LeakCanary;


public class Application extends android.app.Application {
    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        if (appComponent == null) {
            appComponent =
                    DaggerApplicationComponent
                            .builder()
                            .applicationModule(new ApplicationModule(this))
                            .crestModule(new CrestModule(this))
                            .build();
        }
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

}
