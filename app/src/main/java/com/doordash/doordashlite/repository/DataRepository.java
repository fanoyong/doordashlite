package com.doordash.doordashlite.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.doordash.common.AppExecutors;
import com.doordash.common.api.DoorDashApi;
import com.doordash.common.model.RestaurantEntry;
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

    private @Nullable
    RestaurantEntryEntity getRestaurantEntryForId(int id) {
        for (RestaurantEntryEntity restaurantEntryEntity : mObservableRestaurantEntry.getValue()) {
            if (restaurantEntryEntity.getId() == id) {
                return restaurantEntryEntity;
            }
        }
        return null;
    }


    public void switchFavoriteAndUpdate(RestaurantEntry restaurant) {
        final RestaurantEntryEntity restaurantEntryEntity = getRestaurantEntryForId(restaurant.getId());
        if (restaurantEntryEntity != null) {
            restaurantEntryEntity.setIsFavorite(!restaurantEntryEntity.isFavorite());
            mDatabase.updateRestaurant(mAppExecutors, restaurantEntryEntity);
            mObservableRestaurantEntry.setValue(mDatabase.entryDao().loadAllRestaurants().getValue());
        }
    }


    public void fetchDataFromServer() {
        Log.v(TAG, "fetchDataFromServer");
        mAppExecutors.networkIO().execute(() -> {
            // TODO Come up with general coordinate get
            try {
                Response<List<RestaurantEntryEntity>> response = mDoorDashApi.getRestaurants(37.422740, -122.139956).execute();
                if (response != null && response.isSuccessful()) {
                    Log.v(TAG, "size:" + response.body().size());
                    mDatabase.insertRestaurantList(mAppExecutors, response.body());
                    mObservableRestaurantEntry.addSource(mDatabase.entryDao().loadAllRestaurants(), restaurantEntryEntities -> {
                        if (mDatabase.getDatabaseCreated().getValue() != null) {
                            Log.v(TAG, "fetchDataFromServer:post");
                            mObservableRestaurantEntry.postValue(restaurantEntryEntities);
                        }
                    });
                }
            } catch (IOException e) {
                Log.e(TAG, "fetchDataFromServer:IOException:" + e.getMessage());
                // no-op
            }
        });
    }

    public void fetchRestaurantFromServer(final int restaurantId) {
        Log.v(TAG, "fetchRestaurantFromServer:" + restaurantId);
        mAppExecutors.networkIO().execute(() -> {
            try {
                Response<RestaurantDetailEntity> response = mDoorDashApi.getRestaurant(restaurantId).execute();
                if (response != null && response.isSuccessful()) {
                    Log.v(TAG, "fetchRestaurantFromServer");
                    mDatabase.insertRestaurantDetail(mAppExecutors, response.body());
                    Log.v(TAG, "fetchRestaurantFromServer:" + response.body().getName());
                    Log.v(TAG, "fetchRestaurantFromServer:" + response.body().getPhoneNumber());
                    mObservableRestaurantDetail.addSource(mDatabase.detailDao().loadAllRestaurants(), restaurantDetailEntities -> {
                        if (mDatabase.getDatabaseCreated().getValue() != null) {
                            Log.v(TAG, "fetchRestaurantFromServer:post");
                            mObservableRestaurantDetail.postValue(restaurantDetailEntities);
                        }
                    });
                }
            } catch (IOException e) {
                Log.e(TAG, "fetchRestaurantFromServer:IOException:" + e.getMessage());
                // no-op
            }
        });
    }

    public LiveData<List<RestaurantEntryEntity>> getRestaurantEntry() {
        return mObservableRestaurantEntry;
    }

    public LiveData<List<RestaurantDetailEntity>> getRestaurantDetail(int restaurantId) {
        fetchRestaurantFromServer(restaurantId);
        return mObservableRestaurantDetail;
    }
}
