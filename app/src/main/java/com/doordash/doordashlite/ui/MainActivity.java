package com.doordash.doordashlite.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doordash.doordashlite.R;

/**
 * Activity for TBD
 */
public class MainActivity extends AppCompatActivity {

    private View mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mContainer = findViewById(R.id.fragment_container);
    }
}
