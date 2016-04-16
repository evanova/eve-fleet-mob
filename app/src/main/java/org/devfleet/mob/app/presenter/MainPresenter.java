package org.devfleet.mob.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;

import com.karumi.rosie.domain.usecase.UseCaseHandler;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.domain.usecase.CharacterUseCase;
import org.devfleet.mob.app.domain.usecase.LoginUseCase;
import org.devfleet.mob.app.model.EveCharacter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class MainPresenter extends AbstractPresenter<MainPresenter.View> {

    public interface View extends AbstractPresenter.View {
        int MAIN = 0;
        int CONTACTS = 1;
        int FITTINGS = 2;
        int FLEETS = 3;

        void showPage(final int page);

        void showError(final String error);

        void showLoading(final boolean loading);

        void setCharacters(final List<EveCharacter> characters);

        void loginCharacter(final EveCharacter character);

        void startActivity(final Intent intent);
    }

    private final Context context;

    private final CharacterUseCase characters;
    private final LoginUseCase login;

    private long lastLoggedIn = 0;

    @Inject
    public MainPresenter(
            final Context context,
            final UseCaseHandler useCaseHandler,
            final CharacterUseCase characters,
            final LoginUseCase login) {
        super(useCaseHandler);
        this.login = login;
        this.characters = characters;
        this.context = context.getApplicationContext();
    }

    @Override
    public void update() {
        super.update();
        Observable
            .defer(() -> Observable.just(this.characters.listCharacters()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(l -> {
                getView().setCharacters(l);
            });
    }

    public boolean onDrawerItemSelected(final long selectedChar, final int itemID) {
        switch (itemID) {
            case R.id.menu_drawer_add: {
                final Intent intent = new Intent(Intent.ACTION_VIEW, login.getUri());
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, this.context.getPackageName());
                getView().startActivity(intent);
                return true;
            }
            case R.id.menu_drawer_market: {
                //final Intent intent = new Intent(this.context, MarketActivity.class);
                //getView().startActivity(intent);
                return true;
            }
            case R.id.menu_drawer_character: {
                getView().showPage(View.MAIN);
                return true;
            }
            case R.id.menu_drawer_contacts: {
                getView().showPage(View.CONTACTS);
                return true;
            }
            case R.id.menu_drawer_fittings: {
                getView().showPage(View.FITTINGS);
                return true;
            }
            case R.id.menu_drawer_fleets: {
                getView().showPage(View.FLEETS);
                return true;
            }
            default:
                return false;
        }
    }

    public boolean login(final long withCharID) {
        if ((withCharID == 0) || (withCharID == this.lastLoggedIn)) {
            return false;
        }
        login(Observable.defer(() -> Observable.just(this.login.loginWithCharacter(withCharID))));
        return true;
    }

    //executes on main thread
    protected void doOnLoggedIn(final EveCharacter character) {

    }

    public void login(final Uri withUri) {
        login(Observable.defer(() -> Observable.just(this.login.loginWithUri(withUri))));
    }

    private void login(final Observable<EveService> observable) {
        getView().loginCharacter(null);
        getView().showLoading(true);
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(service -> (null == service) ? null : service.getCharacter())
            .doOnError(e -> {
                getView().showLoading(false);
                getView().showError(e.getLocalizedMessage());
            })
            .subscribe(character -> {
                this.lastLoggedIn = (null == character) ? 0 : character.getID();
                getView().showLoading(false);
                getView().loginCharacter(character);
                doOnLoggedIn(character);
            });
    }

}
