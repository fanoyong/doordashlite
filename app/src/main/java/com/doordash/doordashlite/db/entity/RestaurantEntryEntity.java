package com.doordash.doordashlite.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.doordash.common.model.RestaurantEntry;
import com.google.gson.annotations.SerializedName;

/**
 * Contract for {@link RestaurantEntry} and Room
 */
@Entity(tableName = "restaurant_entry")
public class RestaurantEntryEntity implements RestaurantEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String mDescription;

    @ColumnInfo(name = "coverImageUrl")
    @SerializedName("cover_img_url")
    private String mCoverImageUrl;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String mStatus;

    @ColumnInfo(name = "deliveryFee")
    @SerializedName("delivery_fee")
    private String mDeliveryFee;

    @ColumnInfo(name = "averageRating")
    @SerializedName("average_rating")
    private Double mAverageRating;

    public RestaurantEntryEntity(Integer id, String name, String description, String coverImageUrl, String status, String deliveryFee, Double averageRating) {
        mId = id;
        mName = name;
        mDescription = description;
        mCoverImageUrl = coverImageUrl;
        mStatus = status;
        mDeliveryFee = deliveryFee;
        mAverageRating = averageRating;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public Integer getId() {
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

    public void setStatus(String status) {
        mStatus = status;
    }

    @Override
    public String getStatus() {
        return mStatus;
    }

    public void setDeliveryFee(String deliveryFee) {
        mDeliveryFee = deliveryFee;
    }

    @Override
    public String getDeliveryFee() {
        return mDeliveryFee;
    }

    public void setAverageRating(Double averageRating) {
        mAverageRating = averageRating;
    }

    @Override
    public Double getAverageRating() {
        return mAverageRating;
    }
}