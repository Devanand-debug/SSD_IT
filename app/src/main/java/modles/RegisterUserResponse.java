package modles;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterUserResponse implements Serializable {

    @SerializedName("otpkey")
    private String otpkey;
    @SerializedName("otp")
    private String otp;
    @SerializedName("expires_in")
    private int expires_in;

    /**
     * No args constructor for use in serialization
     */
    public RegisterUserResponse() {
    }

    /**
     * @param expiresIn
     * @param otpkey
     * @param otp
     */
    public RegisterUserResponse(String otpkey, String otp, int expiresIn) {
        super();
        this.otpkey = otpkey;
        this.otp = otp;
        this.expires_in = expiresIn;
    }

    public String getOtpkey() {
        return otpkey;
    }

    public void setOtpkey(String otpkey) {
        this.otpkey = otpkey;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}