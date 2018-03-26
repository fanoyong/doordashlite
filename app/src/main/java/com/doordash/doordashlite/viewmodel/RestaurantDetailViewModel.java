package com.doordash.doordashlite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.doordash.doordashlite.DoorDashLiteApplication;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;

import java.util.List;

public class RestaurantDetailViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RestaurantDetailEntity>> mObservableRestaurantDetail;

    public RestaurantDetailViewModel(Application application) {
        super(application);

        mObservableRestaurantDetail = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableRestaurantDetail.setValue(null);

        LiveData<List<RestaurantDetailEntity>>
                restaurants =
                ((DoorDashLiteApplication) application).getRepository().getRestaurantDetail();

        // observe the changes of the restaurants from the database and forward them
        mObservableRestaurantDetail.addSource(restaurants, mObservableRestaurantDetail::setValue);
    }

    public MediatorLiveData<List<RestaurantDetailEntity>> getRestaurantDetail() {
        return mObservableRestaurantDetail;
    }
}
