package com.eve0.fleetmob.app.crest;

import android.content.Context;
import android.net.Uri;

import com.eve0.fleetmob.app.util.Applications;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.Relay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.schedulers.Schedulers;

@Module
public class CrestModule {
    private static final Logger LOG = LoggerFactory.getLogger(CrestModule.class);

    private final CrestClient client;
    private final Uri loginURI;

    private final Relay<String, String> subject;
    private final Observable<CrestService> service;

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

        this.subject = BehaviorRelay.create();
        this.service =
            this.subject
            .asObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map(authCode -> {
                try {
                    return this.client.authorize(authCode);
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            });
    }

    @Provides
    @Singleton
    @Named("loginURI")
    public Uri provideURI() {
        return this.loginURI;
    }

    @Provides
    @Singleton
    public CrestClient provideClient() {
        return this.client;
    }

    @Provides
    @Singleton
    public CrestService.CrestAuthenticator provideAuthenticator() {
        return (authCode -> subject.call(authCode));
    }

    @Provides
    @Singleton
    public Observable<CrestService> provideService() {
        return this.service;
    }
}
