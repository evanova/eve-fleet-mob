package com.eve0.fleetmob.app.ui;

import android.net.Uri;

import com.eve0.fleetmob.app.crest.CrestClient;
import com.eve0.fleetmob.app.crest.CrestService;
import com.eve0.fleetmob.app.util.RX;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class LoginPresenter extends ViewPresenter<LoginView> {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPresenter.class);

    private final CrestClient client;
    private final Uri loginURI;

    private CrestService service = null;
    private String authCode = null;

    @Inject
    public LoginPresenter(final CrestClient client, final @Named("loginURI") Uri loginURI) {
        this.client = client;
        this.loginURI = loginURI;
    }

    public void setAuthentication(final Uri uri) {
        try {
            final String authCode = (null == uri) ? null : uri.getQueryParameter("code");
            if (StringUtils.isBlank(authCode)) {
                throw new IOException("no auth code");
            }
            this.authCode = authCode;
            RX.defer(() -> {
                try {
                    return this.client.authorize(authCode);
                }
                catch (IOException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                    return null;
                }
            })
            .subscribe(service -> {
                this.service = service;
                if (null == service) {
                    return;
                }
                this.service
                    .getCharacter()
                    .subscribe(c -> getView().showCharacter(c));
            });

        }
        catch (IOException e) {
            this.authCode = null;
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    public void startAuthentication(final boolean refresh) {
        if ((null == authCode) || (refresh)) {
            getView().showLogin(this.loginURI);
        }
    }
}
