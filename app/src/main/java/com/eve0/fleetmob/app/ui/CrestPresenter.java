package com.eve0.fleetmob.app.ui;

import com.eve0.fleetmob.app.crest.CrestService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

@Singleton
public class CrestPresenter<V> extends ViewPresenter<V> {

    private final Observable<CrestService> crest;
    private Subscription subscription;

    @Inject
    public CrestPresenter(final Observable<CrestService> crest) {
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

    protected void onServiceChanged(final CrestService service) {

    }
}
