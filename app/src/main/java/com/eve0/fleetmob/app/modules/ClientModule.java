package com.eve0.fleetmob.app.modules;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


class ClientModule {

    private final Retrofit client;

    public ClientModule(final String accessToken) {
        final OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(chain -> {
                            Request.Builder builder = chain
                                    .request()
                                    .newBuilder()
                                    .addHeader("Authorization", "Bearer " + accessToken)
                                    .addHeader("Host", "crest-tq.eveonline.com")
									.addHeader("User-Agent", "FleetMob (https://github.com/evanova/eve-fleet-mob)");
                            return chain.proceed(builder.build());
                        })
                        .build();

        this.client =
                new Retrofit.Builder()
                        .baseUrl("https://crest-tq.eveonline.com/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .client(client)
                        .build();
    }

    protected Retrofit getClient() {
        return this.client;
    }
}
