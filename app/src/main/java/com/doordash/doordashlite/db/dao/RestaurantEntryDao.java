package com.doordash.doordashlite.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.doordash.common.model.RestaurantEntry;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

/**
 * Room's DAO for {@link RestaurantEntry}
 */
@Dao
public interface RestaurantEntryDao {
    // Update if existing data has been updated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RestaurantEntryEntity> restaurants);

    @Query("SELECT * FROM restaurant_entry")
    LiveData<List<RestaurantEntryEntity>> loadAllRestaurants();

    @Query("SELECT * FROM restaurant_entry WHERE id = :restaurantId")
    LiveData<RestaurantEntryEntity> loadRestaurant(int restaurantId);
}
