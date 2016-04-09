package com.eve0.fleetmob.app.crest;

import android.content.Context;
import android.net.Uri;

import com.eve0.fleetmob.app.util.Applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CrestModule {
    private static final Logger LOG = LoggerFactory.getLogger(CrestModule.class);

    private final CrestClient client;
    private final Uri loginURI;

    public CrestModule(final Context context) {
        this.client = new CrestClient(
                Applications.getMetaData(context, "crest.id"),
                Applications.getMetaData(context, "crest.key"));

        this.loginURI =
                Uri.parse(new StringBuilder()
                .append("https://login.eveonline.com/oauth/authorize/?response_type=code&redirect_uri=")
                .append(Applications.getMetaData(context, "crest.redirect"))
                .append("&client_id=")
                .append(Applications.getMetaData(context, "crest.id"))
                .append("&scope=characterContactsRead%20characterContactsWrite%20publicData")
                .toString());
    }

    @Provides
    @Named("loginURI")
    public Uri provideURI() {
        return this.loginURI;
    }

    @Provides
    public CrestClient provideClient() {
        return this.client;
    }
}
