package com.eve0.fleetmob.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;

import com.eve0.fleetmob.app.ApplicationComponent;
import com.eve0.fleetmob.app.R;
import com.eve0.fleetmob.app.model.EveCharacter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MainActivity extends AbstractActivity implements LoginView {
    private static final Logger LOG = LoggerFactory.getLogger(MainActivity.class);

    @Inject
    LoginPresenter login;

    @Override
    protected void inject(ApplicationComponent component) {
        component.inject(this);
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
    protected void onStart() {
        super.onStart();
        LOG.error("onStart {}", getIntent());
        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            return;
        }
        this.login.startAuthentication(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.login.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.login.detachView(false);
    }

    @Override
    public void showLogin(Uri uri) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        startActivity(intent);
    }

    @Override
    public void showCharacter(EveCharacter pilot) {
        LOG.error(ToStringBuilder.reflectionToString(pilot));
    }
}
