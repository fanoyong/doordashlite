package com.doordash.doordashlite.ui;

import android.view.View;

import com.doordash.common.model.RestaurantEntry;

public interface FavoriteOnClickCallback {

    void onClick(View view, RestaurantEntry restaurantEntry);
}
