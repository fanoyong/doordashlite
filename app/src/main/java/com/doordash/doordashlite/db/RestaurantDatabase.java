package com.doordash.doordashlite.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.doordash.common.AppExecutors;
import com.doordash.doordashlite.db.dao.RestaurantDetailDao;
import com.doordash.doordashlite.db.dao.RestaurantEntryDao;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

@Database(entities = {RestaurantEntryEntity.class, RestaurantDetailEntity.class}, version = 2, exportSchema = false)
public abstract class RestaurantDatabase extends RoomDatabase {
    private static RestaurantDatabase sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "restaurants_db";

    public abstract RestaurantEntryDao entryDao();

    public abstract RestaurantDetailDao detailDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static RestaurantDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (RestaurantDatabase.class) {
                if (sInstance == null) {
                    // TODO Introduce common app executor pattern
                    sInstance = initDatabase(context, executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static RestaurantDatabase initDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, RestaurantDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                executors.diskIO().execute(() -> executors.diskIO().execute(() -> {
                    RestaurantDatabase database = RestaurantDatabase.getInstance(appContext, executors);
                    database.setDatabaseCreated();
                }));

            }
        }).build();
    }


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public void insertRestaurantDetail(final AppExecutors executors, final RestaurantDetailEntity restaurantDetailEntry) {
        synchronized (RestaurantDatabase.class) {
            executors.diskIO().execute(() -> runInTransaction(() -> detailDao().insert(restaurantDetailEntry)));
        }
    }

    public void insertRestaurantList(final AppExecutors executors, final List<RestaurantEntryEntity> restaurantEntryEntities) {
        synchronized (RestaurantDatabase.class) {
            executors.diskIO().execute(() -> runInTransaction(() -> entryDao().insertAll(restaurantEntryEntities)));

        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
