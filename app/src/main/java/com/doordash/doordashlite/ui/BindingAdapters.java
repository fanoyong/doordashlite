package com.doordash.doordashlite.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.doordash.common.model.RestaurantEntry;
import com.squareup.picasso.Picasso;

public class BindingAdapters {
    private static final String TAG = "BindingAdapters";

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.get().load(url).error(error).into(view);
    }

    @BindingAdapter("favorite")
    public static void setFavorite(ImageView view, RestaurantEntry restaurantEntry) {
        Log.v(TAG, "setFavorite:" + restaurantEntry.isFavorite());
        if (restaurantEntry.isFavorite()) {
            view.setImageDrawable(view.getResources().getDrawable(android.R.drawable.btn_star_big_on));
        } else {
            view.setImageDrawable(view.getResources().getDrawable(android.R.drawable.btn_star_big_off));
        }
    }
}