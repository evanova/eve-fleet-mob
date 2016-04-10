package com.eve0.fleetmob.app.ui;

import com.eve0.fleetmob.app.crest.EveService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

@Singleton
public class EvePresenter<V> extends ViewPresenter<V> {

    private final Observable<EveService> crest;
    private Subscription subscription;

    @Inject
    public EvePresenter(final Observable<EveService> crest) {
        this.crest = crest;
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        this.subscription = this.crest.subscribe(service -> onServiceChanged(service));
    }

    @Override
    public void detachView(boolean retainInstance) {
        this.subscription.unsubscribe();
        this.subscription = null;
        super.detachView(retainInstance);
    }

    protected void onServiceChanged(final EveService service) {

    }
}
