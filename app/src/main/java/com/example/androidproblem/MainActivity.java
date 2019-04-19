package com.example.androidproblem;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.model.AddFavPOST;
import data.model.GetAllSpotsPOST;
import data.model.GetAllSpotsResult;
import data.model.RemoveFavPOST;
import data.model.TokenPost;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ScrollView sv;
    private TableLayout tableLayout;
    ImageButton favButton;
    private APIService mApiService;
    static String EMAIL_VALID = "t1@gmail.com";
    String token;
    Drawable star;
    Drawable starOn;
    Drawable starOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (ScrollView) findViewById(R.id.sv);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        mApiService = ApiUtils.getAPIService();
        starOn = getDrawable(R.drawable.star_on);
        starOff = getDrawable(R.drawable.star_off);

        //get token
        mApiService.getToken(EMAIL_VALID).enqueue(new Callback<TokenPost>() {
            @Override
            public void onResponse(Call<TokenPost> call, Response<TokenPost> response) {
                token = response.body().getResult().getToken();
                //get spot list
                mApiService.getAllSpots(token,"","").enqueue(new Callback<GetAllSpotsPOST>() {
                    @Override
                    public void onResponse(Call<GetAllSpotsPOST> call, Response<GetAllSpotsPOST> response) {
                        List<GetAllSpotsResult> spotList = response.body().getResult();
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        for(int i = 0; i < spotList.size(); i++){
                            TableRow row = new TableRow(getApplicationContext());
                            LinearLayout textLayout = new LinearLayout(getApplicationContext());
                            LinearLayout buttonLayout = new LinearLayout(getApplicationContext());
                            textLayout.setOrientation(LinearLayout.VERTICAL);
                            GetAllSpotsResult spotCurrent = spotList.get(i);
                            String spotName = spotCurrent.getName();
                            String spotCountry = spotCurrent.getCountry();
                            String spotId = spotCurrent.getId();
                            addSpotText(spotName, textLayout, lp);
                            addSpotText(spotCountry, textLayout, lp);
                            addSpotText(spotId, textLayout, lp);
                            addFavButton(lp, spotCurrent, buttonLayout);
                            row.addView(textLayout);
                            row.addView(buttonLayout);
                            tableLayout.addView(row);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllSpotsPOST> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<TokenPost> call, Throwable t) {
            }
        });

    }


    private void addSpotText(String string, LinearLayout textLayout, LinearLayout.LayoutParams lp){
        TextView spot = new TextView(getApplicationContext());
        spot.setText(string);
        spot.setLayoutParams(lp);
        textLayout.addView(spot);
    }

    private void addFavButton(LinearLayout.LayoutParams lp, GetAllSpotsResult spotCurrent, LinearLayout buttonLayout){
        favButton = new ImageButton(getApplicationContext());
        favButton.setLayoutParams(lp);
        favButton.setTag(spotCurrent.getId());
        if(spotCurrent.getIsFavorite()){
            favButton.setBackground(getDrawable(R.drawable.star_on));
        }
        else{
            favButton.setBackground(getDrawable(R.drawable.star_off));
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //get id of spot and favorite status
                String spotIdCurrent = v.getTag().toString();
                star = v.getBackground();
                //compare to see if favorited using the drawables
                if(star.getConstantState().equals(starOn.getConstantState())){
                    mApiService.remSpotFav(token, spotIdCurrent).enqueue(new Callback<RemoveFavPOST>() {
                        @Override
                        public void onResponse(Call<RemoveFavPOST> call, Response<RemoveFavPOST> response) {
                            //backend wil unfavorite by itself, i just set image resource
                            v.setBackground(getDrawable(R.drawable.star_off));
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
                            v.setBackground(getDrawable(R.drawable.star_on));
                        }

                        @Override
                        public void onFailure(Call<AddFavPOST> call, Throwable t) {

                        }
                    });
                }
            }
        });
        buttonLayout.addView(favButton);
    }

}
