package com.eve0.fleetmob.app.modules;

import javax.inject.Inject;
import com.eve0.fleetmob.crest.ContactService;
import dagger.Module;
import dagger.Provides;

@Module
public final class ContactModule extends ClientModule {

    @Inject
    public ContactModule(final String accessToken) {
        super(accessToken);
    }

    @Provides
    public ContactService provideContactService() {
        return getClient().create(ContactService.class);
    }

}
