package org.devfleet.mob.app.ui;

import android.content.Intent;
import android.widget.TextView;

import com.karumi.rosie.view.Presenter;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.presenter.LauncherPresenter;

import javax.inject.Inject;

import butterknife.Bind;

public class LauncherActivity extends AbstractActivity implements LauncherPresenter.View {

    @Inject
    @Presenter
    LauncherPresenter presenter;

    @Bind(R.id.activityLauncherText)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    public void show(String operation) {
        textView.setText(operation);
    }

    @Override
    public void close() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.init();
    }
}
