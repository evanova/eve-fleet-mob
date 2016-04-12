package com.eve0.crest.retrofit;

import com.eve0.crest.CrestService;
import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContact;
import com.eve0.crest.model.CrestServerStatus;
import com.eve0.crest.model.CrestToken;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

final class CrestServiceImpl implements CrestService {

    private static final Logger LOG = LoggerFactory.getLogger(CrestServiceImpl.class);

    private final LoginService login;

    private ClientService client;
    private CrestToken token;

    public CrestServiceImpl(final String clientID, final String clientKey) {
        Validate.isTrue(StringUtils.isNotBlank(clientID));
        Validate.isTrue(StringUtils.isNotBlank(clientKey));

        this.login = CrestRetrofit.newLogin(clientID, clientKey).create(LoginService.class);
        this.client = CrestRetrofit.newClient().create(ClientService.class);
        this.token = null;
    }

    @Override
    public CrestServerStatus getServerStatus() {
        try {
            return this.client.getServerStatus().execute().body();
        }
        catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public CrestCharacterStatus getCharacterStatus() {
        return refreshToken(this.token);
    }

    @Override
    public CrestCharacter getCharacter() {
        final CrestCharacterStatus status = setNewToken(this.token);
        if (null == status) {
            throw new IllegalStateException("not authenticated");
        }

        try {
            final CrestCharacter character = this.client.getCharacter(status.getCharacterID()).execute().body();
            character.setRefreshToken((null == this.token) ? null : this.token.getRefreshToken());
            return character;
        }
        catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            throw new IllegalStateException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public List<CrestContact> getCharacterContacts() {
        final CrestCharacterStatus status = setNewToken(this.token);
        if (null == status) {
            throw new IllegalStateException("not authenticated");
        }
        try {
            return this.client.getUserContacts(status.getCharacterID()).execute().body().getItems();
        }
        catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            throw new IllegalStateException(e.getLocalizedMessage(), e);
        }
    }

    public void setRefreshToken(final String refreshToken) throws IOException {
        if (StringUtils.isBlank(refreshToken)) {
            setNewToken(null);
            return;
        }

        setNewToken(refreshToken(this.login, refreshToken));
    }

    public void setAuthCode(final String authCode) throws IOException {
        if (StringUtils.isBlank(authCode)) {
            setNewToken(null);
            return;
        }
        setNewToken(obtainToken(this.login, authCode));
    }

    private CrestCharacterStatus refreshToken(final CrestToken token) {
        if (null == token) {
            if (this.token != null) {
                setNewToken(null);
            }
            return null;
        }

        if ((null == this.token) || !StringUtils.equals(this.token.getAccessToken(), token.getAccessToken())) {
            return setNewToken(token);
        }

        //same AccessToken
        if (this.token.expiresOn() < System.currentTimeMillis()) {
            try {
                return setNewToken(refreshToken(this.login, this.token.getRefreshToken()));
            }
            catch (IOException e) {
                LOG.warn(e.getLocalizedMessage(), e);
                return setNewToken(null);
            }
        }

        //in theory, this token is still valid
        try {
            return this.login.getVerification(token.getAccessToken()).execute().body();
        }
        catch (IOException e) {
            LOG.warn(e.getLocalizedMessage());
            try {
                return setNewToken(refreshToken(this.login, token.getRefreshToken()));
            }
            catch (IOException e2) {
                LOG.warn(e2.getLocalizedMessage());
                return null;
            }
        }
    }

    private CrestCharacterStatus setNewToken(final CrestToken token) {
        if (null == token) {
            this.client = CrestRetrofit.newClient().create(ClientService.class);
            this.token = null;
            return null;
        }
        try {
            this.client = CrestRetrofit.newClient(token.getAccessToken()).create(ClientService.class);
            this.token = token;
            return this.login.getVerification(token.getAccessToken()).execute().body();
        }
        catch (IOException e) {
            LOG.warn(e.getLocalizedMessage(), e);
            this.client = CrestRetrofit.newClient().create(ClientService.class);
            this.token = null;
            return null;
        }
    }

    private static CrestToken obtainToken(final LoginService login, final String authCode) throws IOException {
        final Response<CrestToken> response = login.getUserToken("authorization_code", authCode).execute();
        if (!response.isSuccessful()) {
            throw new IOException("token request unsuccessful: " + response.message());
        }
        return response.body();
    }

    private static CrestToken refreshToken(final LoginService login, final String refreshToken) throws IOException {
        final Response<CrestToken> response = login.getUserTokenUsingRefresh("refresh_token", refreshToken).execute();
        if (!response.isSuccessful()) {
            throw new IOException("token request unsuccessful: " + response.message());
        }
        return response.body();
    }
}
