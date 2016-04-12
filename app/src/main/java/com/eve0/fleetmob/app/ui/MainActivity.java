package com.eve0.fleetmob.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;

import com.eve0.fleetmob.app.ApplicationComponent;
import com.eve0.fleetmob.app.R;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.ui.drawer.DrawerPresenter;
import com.eve0.fleetmob.app.ui.drawer.DrawerView;
import com.eve0.fleetmob.app.ui.drawer.MainDrawer;
import com.eve0.fleetmob.app.ui.login.LoginPresenter;
import com.eve0.fleetmob.app.ui.login.LoginView;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AbstractActivity implements LoginView, DrawerView {
    private static final Logger LOG = LoggerFactory.getLogger(MainActivity.class);

    private DrawerView drawerView;

    @Inject
    LoginPresenter login;

    @Inject
    DrawerPresenter drawer;

    @Override
    protected void inject(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        LOG.error("onNewIntent {} {} ", login, intent);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            login.setAuthentication(intent.getData());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.drawerView = new MainDrawer(this, savedInstanceState) {
            @Override
            protected void onAddCharacter() {
                login.login();
            }

            @Override
            protected void onSelectCharacter(final long charID) {
                login.login(charID);
            }
        };
        this.drawer.attachView(this);
        this.login.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.drawer.detachView(false);
        this.login.detachView(false);
    }

    @Override
    public void onBackPressed() {
        if (this.drawer != null && this.drawerView.isDrawerOpened()) {
            this.drawerView.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showLogin(Uri uri) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        startActivity(intent);
    }

    @Override
    public void setSelectedCharacter(EveCharacter character) {
        LOG.error("setSelectedCharacter {}", ToStringBuilder.reflectionToString(character));
        this.drawerView.setSelectedCharacter(character);
    }

    @Override
    public void setCharacters(List<EveCharacter> characters) {
        LOG.error("setCharacters {}", characters.size());
        this.drawerView.setCharacters(characters);
    }

    @Override
    public void showCharacter(EveCharacter pilot) {
        LOG.error("showCharacter {}", ToStringBuilder.reflectionToString(pilot));
        if (null == pilot) {
            setTitle("Not logged in");
        }
        else {
            setTitle(pilot.getName());
            this.drawerView.setSelectedCharacter(pilot);
        }
    }

    @Override
    public void openDrawer() {
        this.drawerView.openDrawer();
    }

    @Override
    public void closeDrawer() {
        this.drawerView.closeDrawer();
    }

    @Override
    public boolean isDrawerOpened() {
        return this.drawerView.isDrawerOpened();
    }
}
