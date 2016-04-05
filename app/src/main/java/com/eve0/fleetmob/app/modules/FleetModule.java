package com.eve0.fleetmob.app.modules;

import javax.inject.Inject;
import com.eve0.fleetmob.crest.FleetService;
import dagger.Module;
import dagger.Provides;

@Module
public final class FleetModule extends ClientModule {

    @Inject
    public FleetModule(final String accessToken) {
        super(accessToken);
    }

    @Provides
    public FleetService provideFleetService() {
        return getClient().create(FleetService.class);
    }

}
