package data.model;

import android.graphics.drawable.Drawable;
import android.view.View;


import data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButtonListener implements View.OnClickListener{
    private String token;
    private APIService mApiService;
    private Drawable starOn;
    private Drawable starOff;

    public ButtonListener(String token, APIService mApiService, Drawable starOn, Drawable starOff){
        this.token = token;
        this.mApiService = mApiService;
        this.starOn = starOn;
        this.starOff = starOff;
    }

    @Override
    public void onClick(final View v) {
        //get id of spot and favorite status
        String spotIdCurrent = v.getTag().toString();
        Drawable star = v.getBackground();
        //compare to see if favorited using the drawables
        if(star.getConstantState().equals(starOn.getConstantState())){
            mApiService.remSpotFav(token, spotIdCurrent).enqueue(new Callback<RemoveFavPOST>() {
                @Override
                public void onResponse(Call<RemoveFavPOST> call, Response<RemoveFavPOST> response) {
                    //backend wil unfavorite by itself, i just set image resource
                    v.setBackground(starOff);
                }

                @Override
                public void onFailure(Call<RemoveFavPOST> call, Throwable t) {

                }
            });
        }
        else{
            mApiService.addSpotFav(token, spotIdCurrent).enqueue(new Callback<AddFavPOST>() {
                @Override
                public void onResponse(Call<AddFavPOST> call, Response<AddFavPOST> response) {
                    //backend will favorite by itself, i just set image resource
                    v.setBackground(starOn);
                }

                @Override
                public void onFailure(Call<AddFavPOST> call, Throwable t) {

                }
            });
        }
    }
}
