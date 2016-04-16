package org.devfleet.mob.app.domain.eve;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.devfleet.crest.retrofit.CrestClient;
import org.devfleet.mob.app.domain.EveService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class EveModule {

    private final EveService.Authenticator authenticator;

    public EveModule(final Context context) {
        CrestClient.Builder builder =
                CrestClient.TQ()
                .id(getStringMetaData(context, "crest.id"))
                .key(getStringMetaData(context, "crest.key"))
                .redirect(getStringMetaData(context, "crest.redirect"));

        final String[] scopes = getArrayMetaData(context, "crest.scopes");
        if (ArrayUtils.isNotEmpty(scopes)) {
            builder = builder.scopes(getArrayMetaData(context, "crest.scopes"));
        }
        this.authenticator = new EveAuthenticatorImpl(builder);
    }

    @Provides
    @Singleton
    @Named("loginURI")
    public Uri getLoginUri() {
        return this.authenticator.getLoginUri();
    }

    @Provides
    @Singleton
    public EveService.Authenticator provideAuthenticator() {
        return this.authenticator;
    }

    protected static final String getStringMetaData(final Context context, final String key) {
        try {
            final ApplicationInfo info =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        }
        catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    protected static final String[] getArrayMetaData(final Context context, final String key) {
        try {
            final String csv = getStringMetaData(context, key);
            return StringUtils.split(csv, ",");
        }
        catch (IllegalStateException e) {
            return null;
        }
    }
}
