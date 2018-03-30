package com.doordash.doordashlite.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doordash.common.model.RestaurantEntry;
import com.doordash.doordashlite.R;
import com.doordash.doordashlite.databinding.RestaurantEntryBinding;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantEntryViewHolder> {
    private static final String TAG = "RestaurantListAdapter";
    List<? extends RestaurantEntry> mRestaurantList;
    @Nullable
    private final RestaurantClickCallback mRestaurantClickCallback;
    private final FavoriteOnClickCallback mFavoriteOnClickCallback;


    public RestaurantListAdapter(@Nullable RestaurantClickCallback clickCallback, FavoriteOnClickCallback favoriteOnClickCallback) {
        mRestaurantClickCallback = clickCallback;
        mFavoriteOnClickCallback = favoriteOnClickCallback;
    }

    public void setRestaurantEntryList(final List<? extends RestaurantEntry> restaurantList) {
        if (mRestaurantList == null) {
            mRestaurantList = restaurantList;
            notifyItemRangeInserted(0, restaurantList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRestaurantList.size();
                }

                @Override
                public int getNewListSize() {
                    return mRestaurantList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mRestaurantList.get(oldItemPosition).getId() ==
                            mRestaurantList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return false;
                }
            });
            mRestaurantList = restaurantList;
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public RestaurantEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RestaurantEntryBinding
                binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.restaurant_entry,
                        parent,
                        false);
        binding.setCallback(mRestaurantClickCallback);
        binding.setCallbackFavorite(mFavoriteOnClickCallback);
        return new RestaurantEntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RestaurantEntryViewHolder holder, int position) {
        holder.mBinding.setRestaurant(mRestaurantList.get(position));
        holder.mBinding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mRestaurantList == null ? 0 : mRestaurantList.size();
    }

    static class RestaurantEntryViewHolder extends RecyclerView.ViewHolder {
        final RestaurantEntryBinding mBinding;

        public RestaurantEntryViewHolder(RestaurantEntryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }


}
