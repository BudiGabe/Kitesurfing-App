package data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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