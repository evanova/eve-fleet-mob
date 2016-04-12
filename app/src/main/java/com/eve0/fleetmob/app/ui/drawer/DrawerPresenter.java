package com.eve0.fleetmob.app.ui.drawer;

import com.eve0.fleetmob.app.data.EveRepository;
import com.eve0.fleetmob.app.eve.EveService;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.ui.EveServicePresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public final class DrawerPresenter extends EveServicePresenter<DrawerView> {

    private final EveRepository data;

    @Inject
    public DrawerPresenter(final Observable<EveService> eve, final EveRepository data) {
        super(eve);
        this.data = data;
    }

    @Override
    public void attachView(DrawerView view) {
        super.attachView(view);
        Observable
                .defer(() -> Observable.just(data.listCharacters()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> {
                    getView().setCharacters(characters);
                });
    }

    @Override
    protected void onServiceChanged(final EveService service) {
        super.onServiceChanged(service);
        if (null == service) {
            return;
        }

        final EveCharacter character = service.getCharacter();
        if (null == character) {
            return;
        }

        Observable
                .defer(() -> Observable.just(data.listCharacters()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> {
                    getView().setCharacters(characters);
                    getView().setSelectedCharacter(character);
                });
    }
}
