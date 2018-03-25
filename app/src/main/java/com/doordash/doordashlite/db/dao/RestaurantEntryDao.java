package com.doordash.doordashlite.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.doordash.common.model.RestaurantEntry;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

/**
 * Room's DAO for {@link RestaurantEntry}
 */
public interface RestaurantEntryDao {
    @Query("SELECT * FROM restaurants")
    LiveData<List<RestaurantEntryEntity>> loadAllRestaurants();

    // Update if existing data has been updated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RestaurantEntryEntity> restaurants);

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    LiveData<RestaurantEntryEntity> loadRestaurants(int restaurantId);

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    RestaurantEntryEntity loadRestaurant(int restaurantId);
}
