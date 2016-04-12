package com.eve0.fleetmob.app.ui;

import com.eve0.fleetmob.app.eve.EveService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscription;

public class EveServicePresenter<V> extends ViewPresenter<V> {
    private static final Logger LOG = LoggerFactory.getLogger(EveServicePresenter.class);

    private final Observable<EveService> eve;

    private Subscription subscription;
    private EveService service;

    protected EveServicePresenter(final Observable<EveService> eve) {
        this.eve = eve;
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);

        this.subscription = this.eve.subscribe(service -> {
            EveServicePresenter.this.service = service;
            LOG.error("onServiceChanged {}", service);
            onServiceChanged(service);
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        this.service = null;
        this.subscription.unsubscribe();
        this.subscription = null;
        super.detachView(retainInstance);
    }

    protected void onServiceChanged(final EveService service) {

    }

    protected final EveService getService() {return service;}
}
