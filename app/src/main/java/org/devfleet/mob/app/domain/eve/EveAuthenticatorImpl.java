package org.devfleet.mob.app.domain.eve;

import android.net.Uri;

import org.devfleet.crest.retrofit.CrestClient;
import org.devfleet.mob.app.domain.EveService;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

class EveAuthenticatorImpl implements EveService.Authenticator {

    private final EveServiceImpl publicImpl;
    private final Map<String, WeakReference<EveService>> impls;

    private final CrestClient.Builder builder;
    private final Uri loginUri;

    public EveAuthenticatorImpl(final CrestClient.Builder builder) {
        this.impls = new HashMap<>();

        this.builder = builder;

        final CrestClient publicClient = this.builder.build();
        this.loginUri = Uri.parse(publicClient.getLoginUri());
        this.publicImpl = new EveServiceImpl(publicClient);
    }

    @Override
    public Uri getLoginUri() {
        return this.loginUri;
    }

    @Override
    public EveService fromPublic() {
        return this.publicImpl;
    }

    @Override
    public EveService fromAuthCode(String authCode) {
        final EveServiceImpl impl = new EveServiceImpl(this.builder.build());
        impl.setClientAuth(authCode);
        return impl;
    }

    @Override
    public EveService fromToken(String refreshToken) {
        final WeakReference<EveService> r = this.impls.get(refreshToken);
        EveService service = (null == r) ? null : r.get();
        if (null == service) {
            final EveServiceImpl impl = new EveServiceImpl(this.builder.build());
            impl.setClientToken(refreshToken);
            this.impls.put(refreshToken, new WeakReference<>(impl));
            return impl;
        }
        return service;
    }
}
