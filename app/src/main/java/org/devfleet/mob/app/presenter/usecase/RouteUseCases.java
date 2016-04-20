package org.devfleet.mob.app.presenter.usecase;

import org.devfleet.mob.app.domain.EveRepository;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public final class RouteUseCases extends UseCaseSupport {
    private static final Logger LOG = LoggerFactory.getLogger(RouteUseCases.class);

    @Inject
    public RouteUseCases(EveService.Authenticator authenticator, EveRepository repository) {
        super(authenticator, repository);
    }

    public boolean setRoute(final long charID, final List<String> waypoints) {
        final EveRoute route = newRoute(waypoints);

        final EveService service = authenticator.fromToken(repository.getToken(charID));
        return service.setRoute(route);
    }

    public boolean addRoute(final long charID, final List<String> waypoints) {
        final EveRoute route = newRoute(waypoints);

        final EveService service = authenticator.fromToken(repository.getToken(charID));
        return service.addRoute(route);
    }

    public EveRoute getRoute(final List<String> waypoints, final int type) {
        final EveRoute route = newRoute(waypoints);
        route.setType(type);

        final EveService service = authenticator.fromPublic();
        return service.getRoute(route);
    }

    private EveRoute newRoute(final List<String> waypoints) {
        final EveRoute route = new EveRoute();
        for (String wp: waypoints) {
            final EveLocation l = repository.findLocation(wp);
            if (null == l) {
                LOG.error("location not found '{}'", wp);
            }
            else {
                route.addLocation(l);
            }
        }
        return route;
    }

}
