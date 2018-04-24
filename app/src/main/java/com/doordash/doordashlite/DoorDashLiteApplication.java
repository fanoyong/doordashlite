package com.doordash.doordashlite;


import android.app.Application;
import android.content.Context;

import com.doordash.common.AppExecutors;
import com.doordash.common.api.DoorDashApi;
import com.doordash.common.auth.AuthPreferences;
import com.doordash.doordashlite.db.RestaurantDatabase;
import com.doordash.doordashlite.repository.DataRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoorDashLiteApplication extends Application {

    // TODO Better naming
    private final static String SHARED_PREFERECES = "doordash_sharedprefereces";

    private AppExecutors mAppExecutors;

    private DoorDashApi mDoorDashApi;

    private AuthPreferences mDoorDashPrefereces;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

        // TODO Better abstraction
        mDoorDashPrefereces = new AuthPreferences(this.getSharedPreferences(SHARED_PREFERECES, Context.MODE_PRIVATE));
    }

    public AuthPreferences getSharedPreferecnes() {
        return mDoorDashPrefereces;
    }

    public RestaurantDatabase getDatabase() {
        return RestaurantDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), mAppExecutors, getDoorDashApi());
    }

    public DoorDashApi getDoorDashApi() {
        if (mDoorDashApi == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", getSharedPreferecnes().getTokenString())

//                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            });


            mDoorDashApi = new Retrofit.Builder()
                    .baseUrl("https://api.doordash.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())

                    .build()
                    .create(DoorDashApi.class);


        }
        return mDoorDashApi;
    }

    // TODO Once new token is set create new API instance

}
