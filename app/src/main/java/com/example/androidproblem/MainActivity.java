package com.example.androidproblem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TableLayout;

import java.util.HashMap;
import java.util.Map;

import data.model.Params;
import data.model.callbacks.GetAllSpotsCallback;
import data.model.Resources;
import data.model.posts.TokenPost;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ImageButton favButton;
    private APIService mApiService;
    private static final String EMAIL_VALID = "t1@gmail.com";
    private Map<String, String> tokenParam = new HashMap<>();
    private Map<String, String> getAllParam = new HashMap<>();
    private Params params = new Params();
    private String token;
    private Drawable starOn;
    private Drawable starOff;
    private Resources res;
    private SharedPreferences.Editor editor;
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        res = new Resources("", "");
        mApiService = ApiUtils.getAPIService();
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        token = prefs.getString("token", null);
        editor = prefs.edit();
        tokenParam.put("email", EMAIL_VALID);
        getToken();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        starOn = getDrawable(R.drawable.star_on);
        starOff = getDrawable(R.drawable.star_off);

        if(getIntent().getExtras() == null){
            getAllParam.put("country", "");
            getAllParam.put("windProbability", "");
            mApiService.getAllSpots(token,getAllParam).enqueue(
                    new GetAllSpotsCallback(token,
                            tableLayout,
                            favButton,
                            starOn,
                            starOff,
                            mApiService,
                            getApplicationContext()));
        }
        else{
            res = getIntent().getExtras().getParcelable("Resource");
            String country = res.getCountry();
            float windProb = Float.valueOf(res.getWindProb());
            params.setCountry(country);
            params.setWindProb(windProb);
            mApiService.getAllSpotsFiltered(token,params).enqueue(
                    new GetAllSpotsCallback(token,
                            tableLayout,
                            favButton,
                            starOn,
                            starOff,
                            mApiService,
                            getApplicationContext()));
        }
    }

    // Create action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Open new activity on button press
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
        // Put res in intent as parcelable to modify in FilterActivity
        intent.putExtra("Resources", res);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private synchronized void getToken(){
        if(token == null){
            // Get token from backend
            mApiService.getToken(tokenParam).enqueue(new Callback<TokenPost>() {
                @Override
                public void onResponse(Call<TokenPost> call, Response<TokenPost> response){
                    token = response.body().getResult().getToken();
                    editor.putString("token", token);
                    editor.apply();
                }

                @Override
                public void onFailure(Call<TokenPost> call, Throwable t) {
                    Log.e(LOG_TAG, "Failed to get token");
                }
            });
        }
    }
}




