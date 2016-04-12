package com.eve0.fleetmob.app;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.eve0.fleetmob.app.data.EveRepositoryModule;
import com.eve0.fleetmob.app.eve.EveModule;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;


public class Application extends android.app.Application {
    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        initDagger();
        initImages();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

    private void initDagger() {
        if (appComponent == null) {
            appComponent =
                DaggerApplicationComponent
                        .builder()
                        .applicationModule(new ApplicationModule(this))
                        .eveModule(new EveModule(this))
                        .eveRepositoryModule(new EveRepositoryModule(this))
                        .build();
        }
    }

    private void initImages() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
    }
}
