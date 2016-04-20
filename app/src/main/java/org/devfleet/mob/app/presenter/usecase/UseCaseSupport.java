package org.devfleet.mob.app.presenter.usecase;

import android.net.Uri;

import org.devfleet.mob.app.domain.EveRepository;
import org.devfleet.mob.app.domain.EveService;

import javax.inject.Inject;
import javax.inject.Named;

class UseCaseSupport {

    protected final EveService.Authenticator authenticator;
    protected final EveRepository repository;

    public UseCaseSupport(
            final EveService.Authenticator authenticator,
            final EveRepository repository) {
        this.authenticator = authenticator;
        this.repository = repository;
    }
}
