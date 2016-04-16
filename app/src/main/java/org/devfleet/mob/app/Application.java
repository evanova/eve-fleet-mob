package org.devfleet.mob.app;

import com.karumi.rosie.application.RosieApplication;
import com.squareup.leakcanary.LeakCanary;

import org.devfleet.mob.app.domain.eve.EveModule;
import org.devfleet.mob.app.domain.repository.EveRepositoryModule;

import java.util.Arrays;
import java.util.List;


public class Application extends RosieApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

    @Override protected List<Object> getApplicationModules() {
        return Arrays.asList(
                new ApplicationModule(this),
                new EveRepositoryModule(this),
                new EveModule(this));
    }
}
