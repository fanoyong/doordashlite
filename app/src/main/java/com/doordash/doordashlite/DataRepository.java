package com.doordash.doordashlite;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.doordash.doordashlite.db.RestaurantDatabase;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

/**
 * Main data repository for {@link RestaurantDetailEntity} and {@link RestaurantEntryEntity}
 */
public class DataRepository {
    private static DataRepository sInstance;
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

    public static DataRepository getInstance(final RestaurantDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<RestaurantEntryEntity>> getRestaurants() {
        return mObservableRestaurantEntry;
    }

    public LiveData<List<RestaurantDetailEntity>> getRestaurantDetail() {
        return mObservableRestaurantDetail;
    }
}
