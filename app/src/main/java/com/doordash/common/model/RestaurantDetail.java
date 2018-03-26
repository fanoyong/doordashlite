package com.doordash.common.model;

/**
 * Model for detailed restaurant information.
 */
public interface RestaurantDetail {
    int getId();

    String getName();

    String getDescription();

    String getCoverImageUrl();

    String getStatus();

    String getDeliveryFee();

    // TODO Add more fields
}
