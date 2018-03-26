package com.doordash.common.model;

/**
 * Model for detailed restaurant information.
 */
public interface RestaurantDetail {
    int getId();

    String getName();

    String getPhoneNumber();

    String getDescription();

    String getCoverImageUrl();

    String getStatus();

    String getDeliveryFee();

    String getAverageRating();
    // TODO Add more fields
}
