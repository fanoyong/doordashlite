package com.doordash.doordashlite.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.doordash.common.model.RestaurantDetail;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;

import java.util.List;

/**
 * Room's DAO for {@link RestaurantDetail}
 */
public interface RestaurantDetailDao {
    @Query("SELECT * FROM restaurants")
    LiveData<List<RestaurantDetailEntity>> loadAllRestaurants();

    // Update if existing data has been updated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RestaurantDetailEntity restaurant);

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    RestaurantDetailEntity loadRestaurant(int restaurantId);
}
