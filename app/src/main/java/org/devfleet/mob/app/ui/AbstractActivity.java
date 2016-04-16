package org.devfleet.mob.app.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.karumi.rosie.view.RosieAppCompatActivity;

import org.devfleet.mob.app.MainModule;
import org.devfleet.mob.app.R;
import org.devfleet.mob.app.widgets.Snacks;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractActivity extends RosieAppCompatActivity {

    @Override
    protected abstract int getLayoutId();

    @Override
    protected List<Object> getActivityScopeModules() {
        return Arrays.asList(new MainModule(this));
    }

    @Override
    public void setTitle(int rId) {
        final ActionBar ab = getSupportActionBar();
        if (null == ab) {
            setTitle(rId);
            return;
        }

        ab.setTitle(rId);
    }

    public void showError(String error) {
        Snacks.show(this, error, true);
    }

    public void showLoading(boolean loading) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
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
