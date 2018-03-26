package com.doordash.doordashlite.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.doordash.common.model.RestaurantEntry;

/**
 * Contract for {@link RestaurantEntry} and Room
 */
@Entity(tableName = "restaurant_entry")
public class RestaurantEntryEntity implements RestaurantEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "coverImageUrl")
    private String mCoverImageUrl;
    @ColumnInfo(name = "status")
    private String mStatus;
    @ColumnInfo(name = "deliveryFee")
    private String mDeliveryFee;

    public RestaurantEntryEntity(int id, String name, String description, String coverImageUrl, String status, String deliveryFee) {
        mId = id;
        mName = name;
        mDescription = description;
        mCoverImageUrl = coverImageUrl;
        mStatus = status;
        mDeliveryFee = deliveryFee;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public int getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String getName() {
        return mName;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }


    public void setCoverImageUrl(String coverImageUrl) {
        mCoverImageUrl = coverImageUrl;
    }

    @Override
    public String getCoverImageUrl() {
        return mCoverImageUrl;
    }

    @Override
    public String getStatus() {
        return mStatus;
    }


    public void setStatus(String status) {
        mStatus = status;
    }

    @Override
    public String getDeliveryFee() {
        return mDeliveryFee;
    }


    public void setDeliveryFee(String deliveryFee) {
        mDeliveryFee = deliveryFee;
    }
}