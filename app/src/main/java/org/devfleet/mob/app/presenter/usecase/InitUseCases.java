package org.devfleet.mob.app.presenter.usecase;

import com.jakewharton.rxrelay.PublishRelay;

import org.apache.commons.lang3.StringUtils;
import org.devfleet.mob.app.ApplicationPreferences;
import org.devfleet.mob.app.domain.EveRepository;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.model.EveServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class InitUseCases extends UseCaseSupport {
    private static final Logger LOG = LoggerFactory.getLogger(InitUseCases.class);

    private final ApplicationPreferences preferences;

    @Inject
    public InitUseCases(
            final EveService.Authenticator authenticator,
            final EveRepository repository,
            final ApplicationPreferences preferences) {
        super(authenticator, repository);
        this.preferences = preferences;
    }

    public Boolean check() {
        LOG.error("check: {}");
        final EveService service = this.authenticator.fromPublic();
        if (null == service) {
            LOG.warn("init(): skipped - offline");
            return null;
        }
        final EveServerStatus status = service.getServerStatus();
        if (null == status) {
            return null;
        }
        LOG.error("check: {}",status.getServerVersion());
        return (StringUtils.equals(status.getServerVersion(), preferences.getCrestVersion()));
    }

    public Boolean initLocations() {
        LOG.error("initLocations: {}");
        final Boolean checked = check();
        if (null == checked) {
            return null;
        }
        if (checked) {
            return true;
        }
        LOG.error("initLocations: {}", checked);
        final EveService service = this.authenticator.fromPublic();
        repository.setLocations(service.getLocations());
        return true;
    }

}
