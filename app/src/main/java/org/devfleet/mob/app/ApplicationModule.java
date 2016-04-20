package org.devfleet.mob.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.karumi.rosie.domain.usecase.error.ErrorFactory;
import com.karumi.rosie.domain.usecase.error.ErrorHandler;
import com.karumi.rosie.view.Presenter;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import org.devfleet.mob.app.domain.EveImages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        complete = false,
        injects = {Application.class})
public class ApplicationModule {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationModule.class);

    private final Context context;

    public ApplicationModule(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    public EveImages provideImages() {
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
        return new EveImages(this.context);
    }

    @Provides
    @Singleton
    //@ROSIE the default ErrorHandler hides too many things
    public ErrorHandler providesErrorHandler() {
        return new ErrorHandler(new ErrorFactory() {
            @Override
            public Error create(Exception e) {
                LOG.error(e.getLocalizedMessage(), e);
                return new Error(e.getMessage(), e);
            }
        });
    }

    @Provides
    @Singleton
    public ApplicationPreferences providePreferences() {
        return new ApplicationPreferences(this.context);
    }
}
