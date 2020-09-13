package com.example.otp_gen4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import Interface.API;
import modles.TokenRequest;
import modles.RegisterUserResponse;
import modles.TokenResponce;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText usertype,phone,deviceid,device;
    Button login;
    API service;
    OkHttpClient httpClient;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);

        usertype=findViewById(R.id.usertype);
        phone=findViewById(R.id.phone);
        deviceid=findViewById(R.id.deviceid);
        device=findViewById(R.id.device);
        login=findViewById(R.id.login);

        phone.setText("+91");

         String mdeviceid = UUID.randomUUID().toString();
        deviceid.setText(mdeviceid);

        String mdevice = android.os.Build.MODEL;
        device.setText(mdevice);

          login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String musertype = usertype.getText().toString().trim();
                String mphone = phone.getText().toString().trim();

                if (musertype.isEmpty()) {
                    usertype.setError("Usertype Is Empty");
                    usertype.requestFocus();
                    return;
                }else if (mphone.isEmpty()  || mphone.length()<10) {
                    phone.setError("Enter Valid Phone Number");
                    phone.requestFocus();
                    return;
                }else {
                    registerUser();
                }
            }
        });
    }

    private void registerUser(){

        try {
            Retrofit retrofit = APIClient.getClient();
            service = retrofit.create(API.class);
            TokenRequest tr = new TokenRequest();
            tr.setUsertype(usertype.getText().toString());
            tr.setPhone(phone.getText().toString());
            tr.setDeviceid(deviceid.getText().toString());
            tr.setDevice(device.getText().toString());

            Call<TokenResponce> call=service.getTokenAccess(tr);

            dialog.setMessage("Processing...");
            dialog.show();
            call.enqueue(new Callback<TokenResponce>() {
                @Override
                public void onResponse(Call<TokenResponce> call, Response<TokenResponce> response) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    int statuscode=response.code();

                    TokenResponce registerUserResponse=response.body();

                    Log.d("Login Activity","onResponce: "+ statuscode);

                    Intent intent=new Intent(MainActivity.this,OTPVerification.class);
                    intent.putExtra("otpkey",registerUserResponse.getOtpkey());
                    intent.putExtra("otp",registerUserResponse.getOtp());
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<TokenResponce> call, Throwable t) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Log.d("Login Activity","onFailure"+t.getMessage());
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}