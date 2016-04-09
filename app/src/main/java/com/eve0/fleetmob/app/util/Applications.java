package com.eve0.fleetmob.app.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class Applications {

    private Applications() {}

    public static String getMetaData(final Context context, final String key) {
        try {
            final ApplicationInfo info =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        }
        catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

}
