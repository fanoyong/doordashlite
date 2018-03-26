package com.doordash.doordashlite.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import com.doordash.common.AppExecutors;
import com.doordash.common.api.DoorDashApi;
import com.doordash.doordashlite.db.RestaurantDatabase;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Main data repository for {@link RestaurantDetailEntity} and {@link RestaurantEntryEntity}
 */
public class DataRepository {

    private static final String TAG = "DataRepository";

    private static DataRepository sInstance;
    private static AppExecutors mAppExecutors;
    private static DoorDashApi mDoorDashApi;
    private final RestaurantDatabase mDatabase;
    private final MediatorLiveData<List<RestaurantEntryEntity>> mObservableRestaurantEntry;
    private final MediatorLiveData<List<RestaurantDetailEntity>> mObservableRestaurantDetail;

    private DataRepository(final RestaurantDatabase database) {
        mDatabase = database;
        mObservableRestaurantEntry = new MediatorLiveData<>();
        mObservableRestaurantDetail = new MediatorLiveData<>();

        mObservableRestaurantEntry.addSource(mDatabase.entryDao().loadAllRestaurants(), restaurantEntryEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                mObservableRestaurantEntry.postValue(restaurantEntryEntities);
            }
        });

        mObservableRestaurantDetail.addSource(mDatabase.detailDao().loadAllRestaurants(), restaurantDetailEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                mObservableRestaurantDetail.postValue(restaurantDetailEntities);
            }
        });
    }

    public static DataRepository getInstance(final RestaurantDatabase database, final AppExecutors appExecutors, final DoorDashApi doorDashApi) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                    mAppExecutors = appExecutors;
                    mDoorDashApi = doorDashApi;
                }
            }
        }
        return sInstance;
    }

    public void fetchDataFromServer() {
        Log.v(TAG, "fetchDataFromServer");
        mAppExecutors.networkIO().execute(() -> {
            // TODO Come up with general coordinate get
            try {
                Response<List<RestaurantEntryEntity>> response = mDoorDashApi.getRestaurants(37.422740, -122.139956).execute();
                if (response != null && response.isSuccessful()) {
                    Log.v(TAG, "size:" + response.body().size());
                    mDatabase.entryDao().insertAll(response.body());
                }
            } catch (IOException e) {
                Log.e(TAG, "fetchDataFromServer:IOException:" + e.getMessage());
                // no-op
            }
        });
    }

    public LiveData<List<RestaurantEntryEntity>> getRestaurantEntry() {
        return mObservableRestaurantEntry;
    }

    public LiveData<List<RestaurantDetailEntity>> getRestaurantDetail() {
        return mObservableRestaurantDetail;
    }
}
