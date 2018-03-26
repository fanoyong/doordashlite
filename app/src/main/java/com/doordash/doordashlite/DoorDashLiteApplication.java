package com.doordash.doordashlite;


import android.app.Application;

import com.doordash.common.AppExecutors;
import com.doordash.common.api.DoorDashApi;
import com.doordash.doordashlite.db.RestaurantDatabase;
import com.doordash.doordashlite.repository.DataRepository;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoorDashLiteApplication extends Application {
    private AppExecutors mAppExecutors;

    private DoorDashApi mDoorDashApi;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public RestaurantDatabase getDatabase() {
        return RestaurantDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), mAppExecutors, getDoorDashApi());
    }

    public DoorDashApi getDoorDashApi() {
        if (mDoorDashApi == null) {
            mDoorDashApi = new Retrofit.Builder()
                    .baseUrl("https://api.doordash.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build()
                    .create(DoorDashApi.class);
        }
        return mDoorDashApi;
    }
}
