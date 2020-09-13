package com.example.otp_gen4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Interface.API;
import modles.OTPRequest;
import modles.OTPResponce;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OTPVerification extends AppCompatActivity {

    TextView otpkey, otp;
    Button verifyotp;
    API otpAPI;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        dialog = new ProgressDialog(this);

        otpkey = findViewById(R.id.otpkey);
        otp = findViewById(R.id.otp);
        verifyotp = findViewById(R.id.verify_otp);

        otpkey.setText(getIntent().getExtras().getString("otpkey", ""));
        otp.setText(getIntent().getExtras().getString("otp", ""));

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String motp = otp.getText().toString().trim();
                String motpkey = otpkey.getText().toString().trim();

                if (motpkey.isEmpty()) {
                    otpkey.setError("Usertype Is Empty");
                    otpkey.requestFocus();
                    return;
                } else if (motp.isEmpty()) {
                    otp.setError("Usertype Is Empty");
                    otp.requestFocus();
                    return;
                } else {
                    otpVerification();
                }
            }


        });
    }


    private void otpVerification() {

        try {
            Retrofit retrofit = APIClient.getClient();
            otpAPI = retrofit.create(API.class);

            OTPRequest otpRequest = new OTPRequest();

            otpRequest.setOtpkey(otpkey.getText().toString());
            otpRequest.setOtp(otp.getText().toString());

            Call<OTPResponce> otpResponceCall = otpAPI.otpverification(otpRequest);
            dialog.setMessage("Processing...");
            dialog.show();

            otpResponceCall.enqueue(new Callback<OTPResponce>() {
                @Override
                public void onResponse(Call<OTPResponce> call, Response<OTPResponce> response) {

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    int statuscode = response.code();

                    OTPResponce otpResponce = response.body();

                    Log.d("OTP Activity", "onResponce" + statuscode);

                    Intent intent = new Intent(OTPVerification.this, WelcomeActivity.class);
                    intent.putExtra("Type", otpResponce.getType());
                    intent.putExtra("Token", otpResponce.getToken());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<OTPResponce> call, Throwable t) {
                    Log.d("OTP Activity", "onResponce" + t.getMessage());

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}