package org.devfleet.mob.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;

import com.karumi.rosie.view.Presenter;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.domain.EveImages;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.presenter.MainPresenter;
import org.devfleet.mob.app.ui.contacts.ContactFragment;
import org.devfleet.mob.app.ui.fittings.FittingFragment;
import org.devfleet.mob.app.ui.routes.RouteFragment;
import org.devfleet.mob.app.widgets.FragmentAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends AbstractActivity implements MainPresenter.View {

    @Inject
    EveImages images;

    @Inject
    @Presenter
    MainPresenter presenter;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @Bind(R.id.backdrop)
    ImageView imageView;

    @Bind(R.id.fab)
    com.github.clans.fab.FloatingActionButton fabButton;

    @Bind(R.id.fabMenu)
    com.github.clans.fab.FloatingActionMenu fabMenu;

    @Bind(R.id.appViewPager)
    ViewPager pagerView;

    @Bind(R.id.appScrollView)
    NestedScrollView nestedScrollView;

    private FragmentAdapter pagerAdapter;

    private DrawerSupport drawer;
    private EveCharacter character;

    @Override
    protected final int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public final void setTitle(CharSequence title) {
        toolbar.setTitle(title);
        toolbarLayout.setTitle(title);
    }

    public final void setSubTitle(CharSequence title) {
        toolbar.setSubtitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //see https://stackoverflow.com/questions/30580954/viewpager-in-a-nestedscrollview
        this.nestedScrollView.setFillViewport(true);

        this.pagerAdapter = new FragmentAdapter(getFragmentManager());
        this.pagerAdapter.addFragment(new MainFragment(), r(R.string.drawer_character));
        this.pagerAdapter.addFragment(new ContactFragment(), r(R.string.drawer_contacts));
        this.pagerAdapter.addFragment(new FittingFragment(), r(R.string.drawer_fittings));
        this.pagerAdapter.addFragment(new RouteFragment(), r(R.string.drawer_routes));

        this.pagerView.setId(R.id.pagerMain);
        this.pagerView.setAdapter(this.pagerAdapter);

        this.drawer = new DrawerSupport(this, savedInstanceState) {
            @Override
            protected boolean onMenuItemSelected(int itemID, boolean longClick) {
                if (presenter.onDrawerItemSelected(getActiveCharacter(), itemID)) {
                    closeDrawer();
                    return true;
                }
                return false;
            }

            @Override
            protected boolean onCharacterSelected(final long charID) {
                closeDrawer();
                presenter.login(charID);
                return true;
            }
        };
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            presenter.login(intent.getData());
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawer != null && this.drawer.isDrawerOpened()) {
            this.drawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showLoading(boolean loading) {
        if (loading) {
            fabMenu.hideMenu(false);
            fabMenu.setVisibility(View.GONE);
            fabButton.setVisibility(View.VISIBLE);
            fabButton.setIndeterminate(true);
            fabButton.setShowProgressBackground(true);
        }
        else {
            fabButton.hideProgress();
            fabButton.setVisibility(View.GONE);
            fabMenu.setVisibility(View.VISIBLE);
        }
    }

    public final void showPage(final int page) {
        this.pagerView.setCurrentItem(page, false);
        final AbstractFragment fragment = (AbstractFragment)this.pagerAdapter.getItem(this.pagerView.getCurrentItem());
        setSubTitle("SUB PAGE " + page);
        fragment.setCharacter(this.character);
    }

    public void setCharacters(List<EveCharacter> characters) {
        this.drawer.setCharacters(characters);
    }

    @Override
    public void loginCharacter(EveCharacter character) {
        this.character = character;

        if (null == character) {
            setTitle(R.string.app_name);
            imageView.setImageResource(R.drawable.navigation);
        }
        else {
            setTitle(character.getName());
            images.loadCharacterImage(this.character.getID(), imageView);
        }

        this.drawer.setActiveCharacter(character);
        final AbstractFragment fragment = (AbstractFragment)this.pagerAdapter.getItem(this.pagerView.getCurrentItem());
        fragment.setCharacter(this.character);
    }

    public final void openDrawer() {
        this.drawer.openDrawer();
    }

    public final void closeDrawer() {
        this.drawer.closeDrawer();
    }

    public final boolean isDrawerOpened() {
        return this.drawer.isDrawerOpened();
    }

}
