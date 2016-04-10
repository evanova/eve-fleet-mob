package com.eve0.fleetmob.app.ui;

import android.net.Uri;

import com.eve0.fleetmob.app.ApplicationPreferences;
import com.eve0.fleetmob.app.crest.CrestAuthenticator;
import com.eve0.fleetmob.app.crest.EveService;
import com.eve0.fleetmob.app.data.ApplicationRepository;
import com.eve0.fleetmob.app.model.EveCharacter;

import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

@Singleton
public class LoginPresenter extends EvePresenter<LoginView> {

    private final CrestAuthenticator auth;

    private final Uri loginURI;
    private Subscription subscription;

    @Inject
    public LoginPresenter(
            final ApplicationRepository data,
            final ApplicationPreferences preferences,
            final CrestAuthenticator authenticator,
            final Observable<EveService> eve,
            final CrestAuthenticator auth,
            final @Named("loginURI") Uri loginURI) {
        super(data, preferences, authenticator, eve);
        this.auth = auth;
        this.loginURI = loginURI;
    }

    public void setAuthentication(final Uri uri) {
        setAuthentication((null == uri) ? null : uri.getQueryParameter("code"));
    }

    public void startAuthentication(final boolean refresh) {
        if ((null == subscription) || (refresh)) {
            getView().showLogin(this.loginURI);
            return;
        }

    }

    @Override
    protected void onServiceChanged(EveService service, EveCharacter character) {
        if (null != this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }

        this.subscription =
                Observable.just(character)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> getView().showCharacter(c));
    }

    private void setAuthentication(final String authCode) {
        if (StringUtils.isBlank(authCode)) {
            return;
        }
        this.auth.setAuthCode(authCode);
    }
}
