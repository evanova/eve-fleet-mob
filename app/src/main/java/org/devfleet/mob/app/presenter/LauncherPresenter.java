package org.devfleet.mob.app.presenter;

import com.karumi.rosie.domain.usecase.UseCaseHandler;

import org.devfleet.mob.app.BuildConfig;
import org.devfleet.mob.app.presenter.usecase.InitUseCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class LauncherPresenter extends AbstractPresenter<LauncherPresenter.View> {
    private static final Logger LOG = LoggerFactory.getLogger(LauncherPresenter.class);

    public interface View extends AbstractPresenter.View {

        void showError(final String error);

        void show(final String operation);

        void close();
    }

    private final InitUseCases init;

    @Inject
    public LauncherPresenter(
            final UseCaseHandler useCaseHandler,
            final InitUseCases init) {
        super(useCaseHandler);
        this.init = init;
    }

    public void init() {
        LOG.error("init: {}");

       final Observable<Boolean> check =
                Observable.defer(() -> Observable.just(init.check()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        final Observable<Boolean> locations =
                Observable.defer(() -> Observable.just(init.initLocations()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.concat(check, locations).subscribe(new Subscriber<Boolean>() {
            private int count = 0;

            @Override
            public void onCompleted() {
                LOG.error("onCompleted: {}");
                unsubscribe();
                getView().close();
            }

            @Override
            public void onError(Throwable e) {
                unsubscribe();
                LOG.error(e.getLocalizedMessage(), e);
                getView().showError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                getView().show("OP " + count + "=" + aBoolean);
                count = count + 1;
            }
        });

    }
}
