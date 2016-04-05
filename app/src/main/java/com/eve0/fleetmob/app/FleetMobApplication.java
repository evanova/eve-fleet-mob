package com.eve0.fleetmob.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


public class FleetMobApplication extends Application {
    private FleetMobComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        if (appComponent == null) {
            appComponent =
                    DaggerFleetMobComponent
                            .builder()
                            .fleetMobModule(new FleetMobModule(this))
                            .build();
        }
    }

    public FleetMobComponent getAppComponent() {
        return appComponent;
    }
}
