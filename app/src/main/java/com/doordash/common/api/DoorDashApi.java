package com.doordash.common.api;

import com.doordash.common.auth.Token;
import com.doordash.common.auth.User;
import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    @GET("restaurant/")
    Call<List<RestaurantEntryEntity>> getRestaurants(@Query("lat") Double latitude, @Query("lng") Double longitude);

    /**
     * Get detailed information for given restaurant id
     *
     * @param id restaurant id
     */
    @GET("restaurant/{id}/")
    Call<RestaurantDetailEntity> getRestaurant(@Path("id") int id);

    /**
     * Log in with email and password
     */
    @GET("auth/token/")
    Call<Token> login(@Body User user);

}

