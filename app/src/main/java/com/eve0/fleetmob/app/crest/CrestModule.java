package com.eve0.fleetmob.app.crest;

import android.content.Context;
import android.net.Uri;

import com.eve0.crest.CrestService;
import com.eve0.crest.retrofit.CrestClient;
import com.eve0.fleetmob.app.inject.AbstractModule;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.Relay;

import org.apache.commons.lang.StringUtils;
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
public class CrestModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(CrestModule.class);

    private final CrestClient client;
    private final Uri loginURI;

    private final Relay<String, String> subject;
    private final Observable<EveService> service;

    public CrestModule(final Context context) {
        super(context);

        this.client = new CrestClient(
                getStringMetaData("crest.id"),
                getStringMetaData("crest.key"));

        this.loginURI =
                Uri.parse(new StringBuilder()
                .append("https://login.eveonline.com/oauth/authorize/?response_type=code&redirect_uri=")
                .append(getStringMetaData("crest.redirect"))
                .append("&client_id=")
                .append(getStringMetaData("crest.id"))
                .append("&scope=")
                .append(StringUtils.replace(getStringMetaData("crest.scopes"), ",", "%20"))
                .toString());

        this.subject = BehaviorRelay.create();
        this.service =
            this.subject
            .asObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map(authCode -> {
                try {
                    final CrestService crest = this.client.getFromAuthCode(authCode);
                    return CrestMapper.map(crest);
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
    public CrestAuthenticator provideAuthenticator() {
        return (authCode -> subject.call(authCode));
    }

    @Provides
    @Singleton
    public Observable<EveService> provideService() {
        return this.service;
    }
}
