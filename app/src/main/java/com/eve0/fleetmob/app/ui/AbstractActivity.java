package com.eve0.fleetmob.app.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.eve0.fleetmob.app.Application;
import com.eve0.fleetmob.app.ApplicationComponent;
import com.eve0.fleetmob.app.R;

public abstract class AbstractActivity extends AppCompatActivity {

    protected abstract void inject(final ApplicationComponent component);

    protected abstract int getLayoutId();

    @Override
    public void setTitle(int rId) {
        final ActionBar ab = getSupportActionBar();
        if (null == ab) {
            setTitle(rId);
            return;
        }

        ab.setTitle(rId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }

        final ApplicationComponent appComponent = ((Application) getApplication()).getAppComponent();
        inject(appComponent);
    }

    protected final String r(final int rID) {
        try {
            return getResources().getString(rID);
        }
        catch (Resources.NotFoundException e) {
            return "";
        }
    }

    protected final String r(final int rID, final Object... format) {
        try {
            return getResources().getString(rID, format);
        }
        catch (Resources.NotFoundException e) {
            return "";
        }
    }
}
