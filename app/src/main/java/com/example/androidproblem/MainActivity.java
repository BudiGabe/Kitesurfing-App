package com.example.androidproblem;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import data.model.GetAllSpotsPOST;
import data.model.GetAllSpotsResult;
import data.model.TokenPost;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    ImageButton favButton;
    private APIService mApiService;
    static String EMAIL_VALID = "t1@gmail.com";
    String token;
    Drawable starOn;
    Drawable starOff;
    LinearLayout.LayoutParams lp;
    List<GetAllSpotsResult> spotList;
    ButtonListener buttonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        mApiService = ApiUtils.getAPIService();
        starOn = getDrawable(R.drawable.star_on);
        starOff = getDrawable(R.drawable.star_off);

        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //get token from backend
        mApiService.getToken(EMAIL_VALID).enqueue(new Callback<TokenPost>() {
            @Override
            public void onResponse(Call<TokenPost> call, Response<TokenPost> response) {
                token = response.body().getResult().getToken();
                //get spot list from backend, after getting token
                mApiService.getAllSpots(token,"","").enqueue(new Callback<GetAllSpotsPOST>() {
                    @Override
                    public void onResponse(Call<GetAllSpotsPOST> call, Response<GetAllSpotsPOST> response) {
                        spotList = response.body().getResult();
                        for(int i = 0; i < spotList.size(); i++){
                            //initialize all needed objects
                            TableRow row = new TableRow(getApplicationContext());
                            LinearLayout textLayout = new LinearLayout(getApplicationContext());
                            LinearLayout buttonLayout = new LinearLayout(getApplicationContext());
                            textLayout.setOrientation(LinearLayout.VERTICAL);
                            //get all needed spot information
                            GetAllSpotsResult spotCurrent = spotList.get(i);
                            String spotName = spotCurrent.getName();
                            String spotCountry = spotCurrent.getCountry();
                            //add spot information to row
                            addSpotText(spotName, textLayout, lp);
                            addSpotText(spotCountry, textLayout, lp);
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
        //create every button
        favButton = new ImageButton(getApplicationContext());
        favButton.setLayoutParams(lp);
        //set tag for easier access to spotId
        favButton.setTag(spotCurrent.getId());
        //set button image
        if(spotCurrent.getIsFavorite()){
            favButton.setBackground(getDrawable(R.drawable.star_on));
        }
        else{
            favButton.setBackground(getDrawable(R.drawable.star_off));
        }
        favButton.setOnClickListener(new ButtonListener(token,mApiService,starOn,starOff));
        buttonLayout.addView(favButton);
    }

}
