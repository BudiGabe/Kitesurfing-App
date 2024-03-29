package data.model.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResult {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("email")
    @Expose
    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}