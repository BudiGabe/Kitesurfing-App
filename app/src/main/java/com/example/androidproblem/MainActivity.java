package com.example.androidproblem;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;

import data.model.GetAllSpotsCallback;
import data.model.TokenPost;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ImageButton favButton;
    private APIService mApiService;
    static String EMAIL_VALID = "t1@gmail.com";
    String token;
    Drawable starOn;
    Drawable starOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        mApiService = ApiUtils.getAPIService();
        starOn = getDrawable(R.drawable.star_on);
        starOff = getDrawable(R.drawable.star_off);

        //get token from backend
        mApiService.getToken(EMAIL_VALID).enqueue(new Callback<TokenPost>() {
            @Override
            public void onResponse(Call<TokenPost> call, Response<TokenPost> response) {
                token = response.body().getResult().getToken();
                //get spot list from backend, after getting token
                mApiService.getAllSpots(token,"","").enqueue(new GetAllSpotsCallback(token, tableLayout, favButton, starOn, starOff, mApiService, getApplicationContext()));
            }
            @Override
            public void onFailure(Call<TokenPost> call, Throwable t) {
            }
        });

    }



}
