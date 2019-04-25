package data.model.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import data.model.results.GetSpotDetResult;

public class GetSpotDetPOST {

    @SerializedName("result")
    @Expose
    private GetSpotDetResult result;

    public GetSpotDetResult getResult() {
        return result;
    }

    public void setResult(GetSpotDetResult result) {
        this.result = result;
    }

}