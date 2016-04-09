package com.eve0.fleetmob.app.ui;

import android.net.Uri;

import com.eve0.fleetmob.app.crest.CrestService;
import com.eve0.fleetmob.app.util.RX;

import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LoginPresenter extends CrestPresenter<LoginView> {

    private final CrestService.CrestAuthenticator auth;

    private final Uri loginURI;

    private String authCode = null;

    @Inject
    public LoginPresenter(
            final Observable<CrestService> crest,
            final CrestService.CrestAuthenticator auth,
            final @Named("loginURI") Uri loginURI) {
        super(crest);
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
    protected void onServiceChanged(CrestService service) {
        RX.subscribe(() -> service.getCharacter(), (c) -> getView().showCharacter(c));
    }

    private void setAuthentication(final String authCode) {
        if (StringUtils.isBlank(authCode)) {
            return;
        }
        this.authCode = authCode;
        this.auth.setCode(authCode);
    }
}
