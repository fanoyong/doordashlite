package com.doordash.doordashlite.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doordash.doordashlite.DoorDashLiteApplication;
import com.doordash.doordashlite.R;
import com.doordash.doordashlite.databinding.RestaurantListFragmentBinding;
import com.doordash.doordashlite.viewmodel.RestaurantListViewModel;

public class RestaurantListFragment extends Fragment {

    static final String TAG = "RestaurantListFragment";

    private RestaurantListAdapter mRestaurantListAdapter;
    private RestaurantListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.restaurant_list_fragment, container, false);

        mRestaurantListAdapter = new RestaurantListAdapter(mRestaurantClickCallback, mFavoriteClickCallback);
        mBinding.restaurantList.setAdapter(mRestaurantListAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DoorDashLiteApplication) getActivity().getApplication()).getRepository().fetchDataFromServer();

        subscribeUi();
    }

    private void subscribeUi() {
        final RestaurantListViewModel
                viewModel =
                ViewModelProviders.of(this).get(RestaurantListViewModel.class);
        // Update the list when the data changes
        viewModel.getRestaurants().observe(this, restaurants -> {
            if (restaurants != null) {
                mBinding.setIsLoading(false);
                mRestaurantListAdapter.setRestaurantEntryList(restaurants);
                Log.v(TAG, "subscribeUi: getRestaurants not null");
            } else {
                mBinding.setIsLoading(true);
                Log.v(TAG, "subscribeUi: getRestaurants null");
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private final RestaurantClickCallback mRestaurantClickCallback = restaurant -> {

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(restaurant);
        }
    };

    private final FavoriteOnClickCallback mFavoriteClickCallback = (view, restaurantEntry) -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Log.v(TAG, "mFavoriteClickCallback:before:" + restaurantEntry.isFavorite());
            ((DoorDashLiteApplication) getActivity().getApplication()).getRepository().switchFavoriteAndUpdate(restaurantEntry);
            Log.v(TAG, "mFavoriteClickCallback:after:" + restaurantEntry.isFavorite());
            subscribeUi();
        }
    };
}
