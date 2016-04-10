package com.eve0.fleetmob.app.inject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import org.apache.commons.lang.StringUtils;

public class AbstractModule {

    private final Context context;

    protected AbstractModule(Context context) {
        this.context = context.getApplicationContext();
    }

    protected final Context getContext() {
        return context;
    }

    protected final String getStringMetaData(final String key) {
        try {
            final ApplicationInfo info =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        }
        catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    protected final String[] getArrayMetaData(final String key) {
        final String md = getStringMetaData(key);
        return (null == md) ? null : StringUtils.split(md, ",");
    }
}
