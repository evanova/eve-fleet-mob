package com.eve0.fleetmob.app.crest;

import android.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

final class CrestRetrofit {
    private static final Logger LOG = LoggerFactory.getLogger(CrestRetrofit.class);

    private static final class LoginInterceptor implements  Interceptor {
        private final String auth;

        public LoginInterceptor(String auth) {
            this.auth = auth;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Host", "login.eveonline.com");

            if (request.header("Authorization") == null || request.header("Authorization").equals("")) {
                builder.addHeader("Authorization", "Basic " + auth);
            }
            builder.addHeader("User-Agent", "FleetMob (https://github.com/evanova/eve-fleet-mob)");
            return chain.proceed(builder.build());
        }
    };

    private static final class ClientInterceptor implements  Interceptor {
        private final String token;

        public ClientInterceptor(String token) {
            this.token = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder =
                    chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("Host", "crest-tq.eveonline.com")
                    .addHeader("User-Agent", "FleetMob (https://github.com/evanova/eve-fleet-mob)");
            return chain.proceed(builder.build());
        }
    }


    private CrestRetrofit() {}

    public static Retrofit newLogin(final String clientID, final String clientKey) {
        return newRetrofit(
                newBuilder()
                .addInterceptor(new LoginInterceptor(toAuth(clientID, clientKey)))
                .build(),
                "https://login.eveonline.com/");
    }

    public static Retrofit newClient(final String token) {
        return newRetrofit(newBuilder()
                .addInterceptor(new ClientInterceptor(token))
                .build(),
                "https://crest-tq.eveonline.com/");
    }

    private static OkHttpClient.Builder newBuilder() {
        OkHttpClient.Builder bob = new OkHttpClient.Builder();
       // if (LOG.isDebugEnabled()) {
            bob.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
      //  }
        return bob;
    }

    private static Retrofit newRetrofit(final OkHttpClient client, final String baseUrl) {
        return
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(JacksonConverterFactory.create())
                        .client(client)
                        .build();
    }

    private static String toAuth(final String clientID, final String clientKey) {
        try {
            final byte[] bytes = new StringBuilder()
                .append(clientID)
                .append(":")
                .append(clientKey)
                .toString()
                .getBytes("UTF-8");
            return Base64.encodeToString(bytes, Base64.DEFAULT).replaceAll("(\\r|\\n)", "");
        }
        catch (UnsupportedEncodingException e) {
            //cannot happen
            throw new RuntimeException(e);
        }
    }
}
