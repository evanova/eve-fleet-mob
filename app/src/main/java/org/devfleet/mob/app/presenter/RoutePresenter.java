package org.devfleet.mob.app.presenter;

import com.karumi.rosie.domain.usecase.UseCaseHandler;

import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.presenter.usecase.RouteUseCases;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class RoutePresenter extends AbstractPresenter<RoutePresenter.View> {

    public interface View extends AbstractPresenter.View {

        void setRoute(final EveRoute route);
    }

    private final RouteUseCases routes;

    @Inject
    public RoutePresenter(
            final UseCaseHandler useCaseHandler,
            final RouteUseCases routes) {
        super(useCaseHandler);
        this.routes = routes;
    }

    public void route(final List<String> waypoints) {
        final Observable<EveRoute> fastest =
                Observable.defer(() -> Observable.just(this.routes.getRoute(waypoints, EveRoute.FASTEST)));

        final Observable<EveRoute> high =
                Observable.defer(() -> Observable.just(this.routes.getRoute(waypoints, EveRoute.HIGHSEC)));

        final Observable<EveRoute> low =
                Observable.defer(() -> Observable.just(this.routes.getRoute(waypoints, EveRoute.LOWSEC)));

        Observable
                .concat(fastest, high, low)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(route -> getView().setRoute(route));
    }
}
