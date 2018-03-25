package com.doordash.doordashlite;


import android.app.Application;

import com.doordash.common.AppExecutors;
import com.doordash.doordashlite.db.RestaurantDatabase;

public class DoorDashLiteApplication extends Application{
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    public RestaurantDatabase getDatabase() {
        return RestaurantDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
