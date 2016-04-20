package org.devfleet.mob.app.presenter;

import com.karumi.rosie.domain.usecase.UseCaseHandler;

import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.presenter.usecase.CharacterUseCases;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class FittingPresenter extends AbstractPresenter<FittingPresenter.View> {
    public interface View extends AbstractPresenter.View {

        void setFittings(final List<EveFitting> fittings);

    }

    private final CharacterUseCases characters;
    private long characterID = 0;

    @Inject
    public FittingPresenter(
            final UseCaseHandler useCaseHandler,
            final CharacterUseCases characters) {
        super(useCaseHandler);
        this.characters = characters;
    }

    @Override
    public void update() {
        super.update();
        setCharacter(this.characterID);
    }

    public void setCharacter(final long charID) {
        if (charID == this.characterID) {
            return;
        }

        if (charID == 0) {
            this.characterID = 0;
            getView().setFittings(Collections.emptyList());
            return;
        }

        this.characterID = charID;

        getView().showLoading(true);
        getView().setFittings(Collections.emptyList());
        Observable
                .defer(() -> Observable.just(this.characters.listFittings(charID)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e -> {
                    getView().showLoading(false);
                    getView().showError(e.getLocalizedMessage());
                })
                .subscribe(l -> {
                    getView().showLoading(false);
                    getView().setFittings(l);
                });
    }
}
