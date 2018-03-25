package com.doordash.common.model;

/**
 * Model for summarized restaurant information to be used for list UI
 */
public interface RestaurantEntry {
    String getId();

    String getName();

    String getDescription();

    String getCoverImageUril();

    String getStatue();

    String getDeliveryFee();
}