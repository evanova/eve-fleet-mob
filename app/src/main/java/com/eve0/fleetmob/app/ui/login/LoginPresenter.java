package com.eve0.fleetmob.app.ui.login;

import android.net.Uri;

import com.eve0.fleetmob.app.ApplicationPreferences;
import com.eve0.fleetmob.app.data.EveRepository;
import com.eve0.fleetmob.app.eve.EveAuthenticator;
import com.eve0.fleetmob.app.eve.EveService;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.ui.EveServicePresenter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class LoginPresenter extends EveServicePresenter<LoginView> {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPresenter.class);

    private final EveAuthenticator auth;
    private final EveRepository data;
    private final ApplicationPreferences preferences;

    private final Uri loginURI;
    private Subscription subscription;

    @Inject
    public LoginPresenter(
            final Observable<EveService> eve,
            final EveAuthenticator auth,
            final EveRepository data,
            final ApplicationPreferences preferences,
            final @Named("loginURI") Uri loginURI) {
        super(eve);

        this.auth = auth;
        this.data = data;
        this.preferences = preferences;
        this.loginURI = loginURI;
    }

    public boolean setAuthentication(final Uri uri) {
        final String authCode = (null == uri) ? null : uri.getQueryParameter("code");
        if (StringUtils.isBlank(authCode)) {
            return false;
        }
        this.auth.setAuthCode(authCode);
        return true;
    }

    public void login() {
        /*if (null != subscription) {
            return;
        }

        if (tryFromPreferences(this.preferences, this.auth)) {
            return;
        }
        if (tryFromRepository(this.data, this.auth)) {
            return;
        }*/
        getView().showLogin(this.loginURI);
    }

    public void login(final long withCharID) {
        LOG.error("login {} ", withCharID);
        Observable
            .defer(() -> Observable.just(data.getCharacter(withCharID)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(c -> {
                LOG.error("login {}", ToStringBuilder.reflectionToString(c));
                if ((null == c) || StringUtils.isBlank(c.getToken())) {
                    getView().showLogin(this.loginURI);;
                }
                else {
                    auth.setAuthToken(c.getToken());
                }
            });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
    }

    @Override
    protected void onServiceChanged(EveService service) {
        final EveCharacter character = (null == service) ? null : service.getCharacter();
        LOG.error("onServiceChanged char {}", character);
        if (null != character) {
            data.addCharacter(character);
            preferences.setSavedCharacter(character.getID());
            preferences.setCharacterToken(character.getID(), character.getToken());
        }

        if (null != this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }

        this.subscription =
                Observable.just(character)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> {
                    LOG.error("onServiceChanged {}", ToStringBuilder.reflectionToString(c));
                    getView().showCharacter(c);
                });
    }

    private static boolean tryFromPreferences(final ApplicationPreferences preferences, final EveAuthenticator auth) {
        final long lastCharacter = preferences.getSavedCharacter();
        if (lastCharacter == -1) {
            return false;
        }
        final String lastToken = preferences.getCharacterToken(lastCharacter);
        if (StringUtils.isBlank(lastToken)) {
            return false;
        }

        auth.setAuthToken(lastToken);
        return true;
    }

    private static boolean tryFromRepository(final EveRepository repository, final EveAuthenticator auth) {
        final EveCharacter first = repository.firstCharacter();
        if (null == first) {
            return false;
        }
        final String token = first.getToken();
        if (StringUtils.isBlank(token)) {
            return false;
        }
        auth.setAuthToken(token);
        return true;
    }
}
