package data.model.posts;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import data.model.results.GetAllSpotsResult;

public class GetAllSpotsPOST {

    @SerializedName("result")
    @Expose
    private List<GetAllSpotsResult> result = null;

    public List<GetAllSpotsResult> getResult() {
        return result;
    }

    public void setResult(List<GetAllSpotsResult> result) {
        this.result = result;
    }

}