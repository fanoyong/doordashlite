package com.doordash.doordashlite.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.doordash.common.model.RestaurantDetail;
import com.google.gson.annotations.SerializedName;

/**
 * Contract for {@link RestaurantDetail} and Room
 */
@Entity(tableName = "restaurant_detail")
public class RestaurantDetailEntity implements RestaurantDetail {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name = "phoneNumber")
    @SerializedName("phone_number")
    private String mPhoneNumber;

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
    private String mAverageRating;

    public RestaurantDetailEntity(int id, String name, String description, String coverImageUrl, String status, String deliveryFee, String averageRating) {
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

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    @Override
    public String getPhoneNumber() {
        return mPhoneNumber;
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

    public void setAverageRating(String averageRating) {
        mAverageRating = averageRating;
    }

    @Override
    public String getAverageRating() {
        return mAverageRating;
    }
}