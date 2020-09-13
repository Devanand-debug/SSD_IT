package com.example.otp_gen4;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Interface.API;
import modles.PersonalResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {
    private String strToken;
    private String strType;
    private API service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        dialog = new ProgressDialog(this);

        strToken = getIntent().getExtras().getString("Token", "");
        strToken = getIntent().getExtras().getString("Type", "");

//        getCustomerDetails();

    }


    private void getCustomerDetails() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.165.253.203/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(API.class);

        Call<PersonalResponse> getPersonalDetails = service.personalDetail(strType + " " + strToken);
        dialog.setMessage("Processing...");
        dialog.show();

        getPersonalDetails.enqueue(new Callback<PersonalResponse>() {
            @Override
            public void onResponse(Call<PersonalResponse> call, Response<PersonalResponse> response) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                int statuscode = response.code();

                PersonalResponse PersonalResponse = response.body();
                Log.d("Personal Details: ", "onResponce" + statuscode);
            }

            @Override
            public void onFailure(Call<PersonalResponse> call, Throwable t) {
                Log.d("Personal Details: ", "onResponce" + t.getMessage());
                Toast.makeText(WelcomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });


    }
}