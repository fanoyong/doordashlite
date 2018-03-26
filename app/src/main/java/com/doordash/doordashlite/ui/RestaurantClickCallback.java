package com.doordash.doordashlite.ui;


import com.doordash.common.model.RestaurantEntry;

/**
 * Callback when user clicks {@link RestaurantEntry} from the list
 */
public interface RestaurantClickCallback {
    void onClick(RestaurantEntry restaurantEntry);
}
