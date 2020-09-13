package Interface;

import android.media.session.MediaSession;

import modles.OTPRequest;
import modles.OTPResponce;
import modles.PersonalResponse;
import modles.RegisterUserResponse;
import modles.TokenRequest;
import modles.TokenResponce;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    @POST("registerUser")
    Call<TokenResponce> getTokenAccess(@Body TokenRequest tokenRequest);

    @POST("OTPVerification")
    Call<OTPResponce> otpverification(@Body OTPRequest otpRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("personalDetailsCustomer")
    Call<PersonalResponse> personalDetail(@Header("Authorization") String auth);


}
