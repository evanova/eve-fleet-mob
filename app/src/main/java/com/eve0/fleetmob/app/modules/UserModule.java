package com.eve0.fleetmob.app.modules;

import javax.inject.Inject;
import com.eve0.fleetmob.crest.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public final class UserModule extends ClientModule {

    @Inject
    public UserModule(final String accessToken) {
        super(accessToken);
    }

    @Provides
    public UserService provideContactService() {
        return getClient().create(UserService.class);
    }

}
