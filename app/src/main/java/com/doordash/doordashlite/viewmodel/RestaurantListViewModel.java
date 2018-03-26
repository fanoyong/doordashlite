package com.doordash.doordashlite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.doordash.doordashlite.DoorDashLiteApplication;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

public class RestaurantListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RestaurantEntryEntity>> mObservableRestaurants;

    public RestaurantListViewModel(Application application) {
        super(application);

        mObservableRestaurants = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableRestaurants.setValue(null);

        LiveData<List<RestaurantEntryEntity>>
                restaurants =
                ((DoorDashLiteApplication) application).getRepository().getRestaurantEntry();

        // observe the changes of the restaurants from the database and forward them
        mObservableRestaurants.addSource(restaurants, mObservableRestaurants::setValue);
    }

    public LiveData<List<RestaurantEntryEntity>> getRestaurants() {
        return mObservableRestaurants;
    }
}
