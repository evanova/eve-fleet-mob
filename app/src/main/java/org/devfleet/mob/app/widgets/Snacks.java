package org.devfleet.mob.app.widgets;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

public final class Snacks {

    private Snacks() {
    }

    public static void show(View context, int rid) {
        show(context, context.getResources().getString(rid), false);
    }

    public static void show(View context, String text) {
        show(context, text, false);
    }

    public static void show(Fragment context, int rid) {
        show(context.getActivity(), rid);
    }

    public static void show(Fragment context, String rid) {
        show(context.getActivity(), rid);
    }

    public static void show(Activity context, int rid) {
        show(context, context.getResources().getString(rid), false);
    }

    public static void show(Activity context, String text) {
        show(context, text, false);
    }

    public static void show(View context, String text, boolean longDuration) {
        Snackbar.make(
                context,
                text,
                (longDuration) ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    public static void show(Activity context, String text, boolean longDuration) {
        Snackbar.make(
                context.findViewById(android.R.id.content),
                text,
                (longDuration) ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }
}
