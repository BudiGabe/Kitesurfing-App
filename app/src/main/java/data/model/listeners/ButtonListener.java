package data.model.listeners;

import android.graphics.drawable.Drawable;
import android.view.View;


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

    public ButtonListener(String token, APIService mApiService, Drawable starOn, Drawable starOff,
                          Spot spot){
        this.token = token;
        this.mApiService = mApiService;
        this.starOn = starOn;
        this.starOff = starOff;
        this.spot = spot;
    }

    @Override
    public void onClick(final View v) {
        //get id of spot
        final String spotIdCurrent = v.getTag().toString();
        if(spot.getIsFavorite()){
            v.setBackground(starOff);
            spot.setIsFavorite(false);
            mApiService.remSpotFav(token, spotIdCurrent).enqueue(new Callback<RemoveFavPOST>() {
                @Override
                public void onResponse(Call<RemoveFavPOST> call, Response<RemoveFavPOST> response) {
                }

                @Override
                public void onFailure(Call<RemoveFavPOST> call, Throwable t) {

                }
            });
        }
        else{
            v.setBackground(starOn);
            spot.setIsFavorite(true);
            mApiService.addSpotFav(token, spotIdCurrent).enqueue(new Callback<AddFavPOST>() {
                @Override
                public void onResponse(Call<AddFavPOST> call, Response<AddFavPOST> response) {
                }

                @Override
                public void onFailure(Call<AddFavPOST> call, Throwable t) {

                }
            });
        }
    }
}
