package com.doordash.doordashlite.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doordash.common.model.RestaurantEntry;
import com.doordash.doordashlite.DoorDashLiteApplication;
import com.doordash.doordashlite.R;

/**
 * Activity for {@link RestaurantDetailFragment} and {@link RestaurantListFragment}
 */
public class MainActivity extends AppCompatActivity {

    private View mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mContainer = findViewById(R.id.fragment_container);
        // AndroidViewModel has to be touched
        DoorDashLiteApplication doorDashLiteApplication = ((DoorDashLiteApplication) getApplication());
        if (!doorDashLiteApplication.getSharedPreferecnes().hasToken()) {
            RestaurantLoginFragment restaurantLoginFragment = new RestaurantLoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, restaurantLoginFragment, RestaurantListFragment.TAG)
                    .commit();
        } else if (savedInstanceState == null) {
            showListFragment();
        }
    }


    public void showListFragment() {

        RestaurantListFragment fragment = new RestaurantListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, RestaurantListFragment.TAG)
                .commit();
    }


    public void show(RestaurantEntry restaurant) {

        RestaurantDetailFragment restaurantDetailFragment = RestaurantDetailFragment.forRestaurant(restaurant.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("mRestaurant")
                .replace(R.id.fragment_container, restaurantDetailFragment, null)
                .commit();
    }
}
