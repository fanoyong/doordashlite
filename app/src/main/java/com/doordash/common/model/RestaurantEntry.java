package com.doordash.common.model;

/**
 * Model for summarized restaurant information to be used for list UI
 */
public interface RestaurantEntry {
    Integer getId();

    String getName();

    String getDescription();

    String getCoverImageUrl();

    String getStatus();

    String getDeliveryFee();

    Double getAverageRating();
}
