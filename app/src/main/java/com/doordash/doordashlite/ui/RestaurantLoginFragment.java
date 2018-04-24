package com.doordash.doordashlite.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doordash.common.auth.AuthHelper;
import com.doordash.common.auth.Token;
import com.doordash.common.auth.User;
import com.doordash.doordashlite.DoorDashLiteApplication;
import com.doordash.doordashlite.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantLoginFragment extends Fragment {


    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.restaurant_login_fragment, container, false);
        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mButton = view.findViewById(R.id.button);

        mButton.setOnClickListener(v -> {
            // TODO Handle failure
            User user = new User(mEmail.getText().toString(), mPassword.getText().toString());
            DoorDashLiteApplication doorDashLiteApplication = ((DoorDashLiteApplication) getActivity().getApplication());
            doorDashLiteApplication.getDoorDashApi().login(user).enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    doorDashLiteApplication.getSharedPreferecnes()
                            .updateToken(AuthHelper.getJWTTokenString(response.body()));

                    ((MainActivity) getActivity()).showListFragment();
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    // TODO Handle failure
                }
            });
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
