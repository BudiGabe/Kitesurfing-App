package data.model.listeners;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;


import java.util.HashMap;
import java.util.Map;

import data.model.Spot;
import data.model.posts.AddFavPOST;
import data.model.posts.RemoveFavPOST;
import data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButtonListener implements View.OnClickListener{
    private String token;
    private APIService mApiService;
    private Drawable starOn;
    private Drawable starOff;
    private Spot spot;
    private Map<String,String> data;
    private static final String LOG_TAG = "ButtonListener";

    public ButtonListener(String token, APIService mApiService, Drawable starOn, Drawable starOff,
                          Spot spot){
        this.token = token;
        this.mApiService = mApiService;
        this.starOn = starOn;
        this.starOff = starOff;
        this.spot = spot;
        this.data = new HashMap<>();
    }

    @Override
    public void onClick(final View v) {
        // Get id of spot
        final String spotIdCurrent = v.getTag().toString();
        // Add spotId to map, then set it as parameter for the POST Request
        data.put("spotId", spotIdCurrent);
        if(spot.getIsFavorite()){
            v.setBackground(starOff);
            spot.setIsFavorite(false);
            mApiService.remSpotFav(token, data).enqueue(new Callback<RemoveFavPOST>() {
                @Override
                public void onResponse(Call<RemoveFavPOST> call, Response<RemoveFavPOST> response) {
                    Log.d(LOG_TAG, "Remove favorite successful");
                }

                @Override
                public void onFailure(Call<RemoveFavPOST> call, Throwable t) {
                    Log.e(LOG_TAG, "Remove favorite failed");
                }
            });
        }
        else{
            v.setBackground(starOn);
            spot.setIsFavorite(true);
            mApiService.addSpotFav(token, data).enqueue(new Callback<AddFavPOST>() {
                @Override
                public void onResponse(Call<AddFavPOST> call, Response<AddFavPOST> response) {
                    Log.d(LOG_TAG, "Add favorite successful");
                }

                @Override
                public void onFailure(Call<AddFavPOST> call, Throwable t) {
                    Log.e(LOG_TAG, "Add favorite failed");
                }
            });
        }
    }
}
