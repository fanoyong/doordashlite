package com.doordash.doordashlite.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doordash.doordashlite.R;
import com.doordash.doordashlite.databinding.RestaurantDetailFragmentBinding;
import com.doordash.doordashlite.viewmodel.RestaurantDetailViewModel;

public class RestaurantDetailFragment extends Fragment {
    private static final String KEY_RESTAURANT_ID = "restaurant_id";
    private RestaurantDetailFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.restaurant_detail_fragment, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RestaurantDetailViewModel.Factory
                factory =
                new RestaurantDetailViewModel.Factory(getActivity().getApplication(),
                        getArguments().getInt(KEY_RESTAURANT_ID));

        final RestaurantDetailViewModel
                viewModel =
                ViewModelProviders.of(this, factory).get(RestaurantDetailViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(RestaurantDetailViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getRestaurantDetail().observe(this, restaurants -> {
            if (restaurants != null) {
                mBinding.setIsLoading(false);
                // TODO React to detail load
            } else {
                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    public static RestaurantDetailFragment forRestaurant(int restaurantId) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }
}
