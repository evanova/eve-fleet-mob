package com.eve0.fleetmob.app.modules;

import org.apache.commons.codec.binary.Base64;
import com.eve0.fleetmob.crest.LoginService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class LoginModule {
    private static final String CLIENT_ID = "84c122b173f94063a488c177d7e0b433";
    private static final String SECRET_KEY = "Ro0FkbQujNwNmWEXguGj8cRTpsRIPq7kX5e8qMNZ";
    private static final String AUTH_HEADER = CLIENT_ID + ":" + SECRET_KEY;

    private LoginService service;

    public LoginModule() {
        final OkHttpClient login =
                new OkHttpClient.Builder()
                        .addInterceptor(chain -> {
                            Request request = chain.request();

                            Request.Builder builder = request.newBuilder()
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                    .addHeader("Host", "login.eveonline.com");

                            if (request.header("Authorization") == null || request.header("Authorization").equals("")) {
                                builder.addHeader("Authorization", "Basic " +
                                        Base64.encodeBase64String(AUTH_HEADER.getBytes("UTF-8")).replaceAll("(\\r|\\n)", ""));
                            }
							builder.addHeader("User-Agent", "FleetMob (https://github.com/evanova/eve-fleet-mob)");
                            return chain.proceed(builder.build());
                        })
                        .build();

        this.service =
                new Retrofit.Builder()
                .baseUrl("https://login.eveonline.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(login)
                .build()
                .create(LoginService.class);

    }

    @Provides
    public LoginService provideLogin() {
        return this.service;
    }
}
