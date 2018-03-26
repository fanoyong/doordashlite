package com.doordash.doordashlite.db;

import com.doordash.doordashlite.db.entity.RestaurantDetailEntity;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class that holds values to be used for testing.
 */
public class TestData {
    static final RestaurantEntryEntity
            RESTAURANT_ENTRY_ENTITY =
            new RestaurantEntryEntity(1, "name1", "desc1", "url1", "status1", "deliveryFee1", "rating1");
    static final RestaurantEntryEntity
            RESTAURANT_ENTRY_ENTITY2 =
            new RestaurantEntryEntity(2, "name2", "desc2", "url2", "status2", "deliveryFee2", "rating2");

    static final List<RestaurantEntryEntity> RESTAURANT_ENTRY = Arrays.asList(RESTAURANT_ENTRY_ENTITY, RESTAURANT_ENTRY_ENTITY2);

    static final RestaurantDetailEntity
            RESTAURANT_DETAIL_ENTITY =
            new RestaurantDetailEntity(1, "name1", "desc1", "url1", "status1", "deliveryFee1", "rating1");

}
