package com.eve0.fleetmob.app.crest;

import android.content.Context;
import android.net.Uri;

import com.eve0.crest.retrofit.CrestClient;
import com.eve0.fleetmob.app.inject.AbstractModule;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.Relay;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.schedulers.Schedulers;

@Module
public class CrestModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(CrestModule.class);

    private final EveServiceImpl impl;
    private final Uri loginURI;

    private final Relay<EveService, EveService> subject;

    private final Observable<EveService> observable;

    public CrestModule(final Context context) {
        super(context);
        this.impl = new EveServiceImpl(
            new CrestClient(
                getStringMetaData("crest.id"),
                getStringMetaData("crest.key")));

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
        this.observable =
                this.subject
                .share()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    @Provides
    @Singleton
    @Named("loginURI")
    public Uri provideURI() {
        return this.loginURI;
    }

    @Provides
    @Singleton
    public CrestAuthenticator provideAuthenticator() {
        return new CrestAuthenticator() {
            @Override
            public void setAuthCode(String authCode) {
                Observable.just(authCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(c -> subject.call(impl.setClientAuth(c) ? impl : null));
            }

            @Override
            public void setAuthToken(String authToken) {
                Observable.just(authToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(c -> subject.call(impl.setClientToken(c) ? impl : null));
            }
        };
    }

    @Provides
    @Singleton
    public Observable<EveService> provideService() {
        return this.observable;
    }
}
