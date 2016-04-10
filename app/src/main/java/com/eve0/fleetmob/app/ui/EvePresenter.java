package com.eve0.fleetmob.app.ui;

import com.eve0.fleetmob.app.ApplicationPreferences;
import com.eve0.fleetmob.app.crest.CrestAuthenticator;
import com.eve0.fleetmob.app.crest.EveService;
import com.eve0.fleetmob.app.data.ApplicationRepository;
import com.eve0.fleetmob.app.model.EveCharacter;

import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

@Singleton
public class EvePresenter<V> extends ViewPresenter<V> {

    private final Observable<EveService> crest;
    private final CrestAuthenticator authenticator;

    private final ApplicationRepository data;
    private final ApplicationPreferences preferences;

    private Subscription subscription;

    @Inject
    public EvePresenter(
            final ApplicationRepository data,
            final ApplicationPreferences preferences,
            final CrestAuthenticator authenticator,
            final Observable<EveService> crest) {
        this.crest = crest;
        this.authenticator = authenticator;
        this.preferences = preferences;
        this.data = data;
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);

        this.subscription = this.crest.subscribe(service -> {
            final EveCharacter character = (null == service) ? null : service.getCharacter();
            if (null != character) {
                data.addCharacter(character);
                preferences.setSavedCharacter(character.getID());
                preferences.setCharacterToken(character.getID(), character.getAccessToken());
            }
            onServiceChanged(service, character);
        });

        final long lastCharacter = preferences.getSavedCharacter();
        if (lastCharacter == -1) {
            return;
        }
        final String lastToken = preferences.getCharacterToken(lastCharacter);
        if (StringUtils.isBlank(lastToken)) {
            return;
        }

        this.authenticator.setAuthToken(lastToken);
    }

    @Override
    public void detachView(boolean retainInstance) {
        this.subscription.unsubscribe();
        this.subscription = null;
        super.detachView(retainInstance);
    }

    protected void onServiceChanged(final EveService service, final EveCharacter character) {

    }

    protected final ApplicationRepository getData() {
        return data;
    }
}
