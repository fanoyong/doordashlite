package com.doordash.common.api;

import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Common API for DoorDash
 */
public interface DoorDashApi {
    /**
     * Get list of restaurants given GPS coordinate
     *
     * @param latitude  GPS latitude
     * @param longitude GPS longitude
     */
    @GET("v2/restaurant/")
    Call<List<RestaurantEntryEntity>> getRestaurants(@Query("lat") double latitude, @Query("lng") double longitude);

    /**
     * Get detailed information for given restaurant id
     *
     * @param id restaurant id
     */
    @GET("v2/restaurant/{id}/")
    Call<RestaurantEntryEntity> getRestaurant(@Path("id") String id);

}

