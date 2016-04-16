package org.devfleet.mob.app;

import android.content.Context;

import org.devfleet.mob.app.ui.MainActivity;
import org.devfleet.mob.app.ui.MainFragment;
import org.devfleet.mob.app.ui.contacts.ContactFragment;
import org.devfleet.mob.app.ui.fittings.FittingFragment;

import javax.inject.Inject;

import dagger.Module;

@Module(
        library = true,
        complete = false,
        injects = {
                MainActivity.class,
                MainFragment.class,
                ContactFragment.class,
                FittingFragment.class})
public class MainModule {

    private final Context context;

    @Inject
    public MainModule(final Context context) {
        this.context = context.getApplicationContext();
    }
}
