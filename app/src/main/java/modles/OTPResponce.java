package modles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPResponce {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("isPaymentDetailsFilled")
    @Expose
    private Boolean isPaymentDetailsFilled;
    @SerializedName("isPersonalDetailFilled")
    @Expose
    private Boolean isPersonalDetailFilled;

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Boolean getPaymentDetailsFilled() {
        return isPaymentDetailsFilled;
    }

    public Boolean getPersonalDetailFilled() {
        return isPersonalDetailFilled;
    }
}
