package com.eve0.fleetmob.app.ui;

import android.net.Uri;

import com.eve0.fleetmob.app.crest.CrestAuthenticator;
import com.eve0.fleetmob.app.crest.EveService;
import com.eve0.fleetmob.app.util.RX;

import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

@Singleton
public class LoginPresenter extends EvePresenter<LoginView> {

    private final CrestAuthenticator auth;

    private final Uri loginURI;

    private String authCode = null;
    private Subscription subscription;

    @Inject
    public LoginPresenter(
            final Observable<EveService> eve,
            final CrestAuthenticator auth,
            final @Named("loginURI") Uri loginURI) {
        super(eve);
        this.auth = auth;
        this.loginURI = loginURI;
    }

    public void setAuthentication(final Uri uri) {
        setAuthentication((null == uri) ? null : uri.getQueryParameter("code"));
    }

    public void startAuthentication(final boolean refresh) {
        if ((null == authCode) || (refresh)) {
            getView().showLogin(this.loginURI);
        }
    }

    @Override
    protected void onServiceChanged(EveService service) {
        if (null != this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        if (null == service) {
            getView().showCharacter(null);
            return;
        }
        this.subscription =
            RX.subscribe(() -> service.getCharacter(), (c) -> getView().showCharacter(c));
    }

    private void setAuthentication(final String authCode) {
        if (StringUtils.isBlank(authCode)) {
            return;
        }
        this.authCode = authCode;
        this.auth.setAuthCode(authCode);
    }
}
