package com.doordash.common.api;

import com.doordash.common.model.RestaurantDetail;
import com.doordash.common.model.RestaurantEntry;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Common API for DoorDash
 */
public class DoorDashApi {
    public interface DoorDashAPI {
        /**
         * Get list of restaurants given GPS coordinate
         *
         * @param latitude  GPS latitude
         * @param longitude GPS longitude
         */
        @GET("restaurant/")
        List<RestaurantEntry> getRestaurants(@Query("lat") Double latitude, @Query("lng") Double longitude);

        /**
         * Get detailed information for given restaurant id
         *
         * @param restaurantId restaurant id
         */
        @GET("restaurant/{restId}/")
        RestaurantDetail getRestaurantDetails(@Path("restId") Integer restaurantId);
    }
}
