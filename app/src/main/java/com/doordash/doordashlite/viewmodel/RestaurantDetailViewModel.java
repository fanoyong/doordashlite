package com.doordash.doordashlite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.doordash.doordashlite.DoorDashLiteApplication;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;

import java.util.List;

public class RestaurantDetailViewModel extends AndroidViewModel {

    private static final String TAG = "RestaurantDetailVM";

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RestaurantDetailEntity>> mObservableRestaurantDetail;

    private final int mRestaurantId;

    public RestaurantDetailViewModel(Application application, int restaurantId) {
        super(application);

        mRestaurantId = restaurantId;

        mObservableRestaurantDetail = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
//        mObservableRestaurantDetail.setValue(null);

        LiveData<List<RestaurantDetailEntity>>
                restaurants =
                ((DoorDashLiteApplication) application).getRepository().getRestaurantDetail(mRestaurantId);

        //Log.v(TAG, "restaurant:" + restaurants.getValue().size());

        // observe the changes of the restaurants from the database and forward them
        mObservableRestaurantDetail.addSource(restaurants, mObservableRestaurantDetail::setValue);
    }

    public MediatorLiveData<List<RestaurantDetailEntity>> getRestaurantDetail() {
        return mObservableRestaurantDetail;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final int mRestaurantId;

        public Factory(@NonNull Application application, int restaurantId) {
            mApplication = application;
            mRestaurantId = restaurantId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RestaurantDetailViewModel(mApplication, mRestaurantId);
        }
    }
}
