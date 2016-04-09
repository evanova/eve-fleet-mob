package com.eve0.fleetmob.app.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.eve0.fleetmob.app.Application;
import com.eve0.fleetmob.app.ApplicationComponent;

public abstract class AbstractActivity extends AppCompatActivity {

    protected abstract void inject(final ApplicationComponent component);

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
        //setContentView(R.layout.activity);

        final ApplicationComponent appComponent = ((Application) getApplication()).getAppComponent();
        inject(appComponent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
