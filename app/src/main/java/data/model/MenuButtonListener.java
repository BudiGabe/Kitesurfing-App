package data.model;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuButtonListener implements MenuItem.OnMenuItemClickListener {

    private String token;
    private APIService mApiService;
    private Drawable starOn;
    private Drawable starOff;
    private String spotId;

    public MenuButtonListener(String token, APIService mApiService, Drawable starOn, Drawable starOff, String spotId){
        this.token = token;
        this.mApiService = mApiService;
        this.starOn = starOn;
        this.starOff = starOff;
        this.spotId = spotId;
    }

    @Override
    public boolean onMenuItemClick(final MenuItem item){
        //get favorite status;
        Drawable star = item.getIcon();
        //compare to see if favorited using the drawables
        if(star.getConstantState().equals(starOn.getConstantState())){
            mApiService.remSpotFav(token, spotId).enqueue(new Callback<RemoveFavPOST>() {
                @Override
                public void onResponse(Call<RemoveFavPOST> call, Response<RemoveFavPOST> response) {
                    //backend wil unfavorite by itself, i just set image resource
                    item.setIcon(starOff);
                }

                @Override
                public void onFailure(Call<RemoveFavPOST> call, Throwable t) {

                }
            });
        }
        else{
            mApiService.addSpotFav(token, spotId).enqueue(new Callback<AddFavPOST>() {
                @Override
                public void onResponse(Call<AddFavPOST> call, Response<AddFavPOST> response) {
                    //backend will favorite by itself, i just set image resource
                    item.setIcon(starOn);
                }

                @Override
                public void onFailure(Call<AddFavPOST> call, Throwable t) {

                }
            });
        }
        return true;
    }

}
