package data.model.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import data.model.results.TokenResult;

public class TokenPost {

    @SerializedName("result")
    @Expose
    private TokenResult tokenResult;

    public TokenResult getResult() {
        return tokenResult;
    }

    public void setResult(TokenResult result) {
        this.tokenResult = result;
    }

}