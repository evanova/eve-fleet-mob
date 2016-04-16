package org.devfleet.mob.app.domain.usecase;

import android.net.Uri;

import org.apache.commons.lang.StringUtils;
import org.devfleet.mob.app.domain.EveRepository;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.model.EveCharacter;

import javax.inject.Inject;
import javax.inject.Named;

public class LoginUseCase {

    private final EveService.Authenticator authenticator;
    private final EveRepository repository;

    private final Uri loginUri;

    @Inject
    public LoginUseCase(
            final EveService.Authenticator authenticator,
            final EveRepository repository,
            @Named("loginURI") final Uri loginUri) {
        this.authenticator = authenticator;
        this.repository = repository;
        this.loginUri = loginUri;
    }

    public Uri getUri() {
        return loginUri;
    }

    public EveService loginWithCharacter(final long withCharID) {
        return loginWithToken(this.repository.getToken(withCharID));
    }

    public EveService loginWithToken(final String token) {
        return (StringUtils.isBlank(token)) ? null : doComplete(this.authenticator.fromToken(token));
    }

    public EveService loginWithUri(final Uri uri) {
        final String authCode = (null == uri) ? null: uri.getQueryParameter("code");
        return (StringUtils.isBlank(authCode)) ? null : doComplete(this.authenticator.fromAuthCode(authCode));
    }

    private EveService doComplete(final EveService service) {
        if (null == service) {
            return null;
        }

        final EveCharacter character = service.getCharacter();
        if (null != character) {
            this.repository.addCharacter(character);
        }
        return service;
    }
}
