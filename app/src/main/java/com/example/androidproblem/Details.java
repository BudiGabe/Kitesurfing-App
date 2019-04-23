package com.example.androidproblem;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import data.model.POSTS.AddFavPOST;
import data.model.POSTS.GetSpotDetPOST;
import data.model.Results.GetSpotDetResult;
import data.model.POSTS.RemoveFavPOST;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {

    APIService mApiService;
    String token;
    String spotId;
    Bundle extras;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lp;
    TableRow row;
    TableLayout tableLayout;
    boolean isFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        mApiService = ApiUtils.getAPIService();
        extras = getIntent().getExtras();
        token = extras.getString("token");
        spotId = extras.getString("spotId");

        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mApiService.getSpotDet(token, spotId).enqueue(new Callback<GetSpotDetPOST>() {
            @Override
            public void onResponse(Call<GetSpotDetPOST> call, Response<GetSpotDetPOST> response) {
                GetSpotDetResult details = response.body().getResult();
                isFavorite = response.body().getResult().getIsFavorite();
                setTitle(details.getName());
                addRow("Country", details.getCountry());
                addRow("Latitude",details.getLatitude().toString());
                addRow("Longitude", details.getLongitude().toString());
                addRow("Wind probability", details.getWindProbability().toString());
                addRow("When To Go", details.getWhenToGo());
            }

            @Override
            public void onFailure(Call<GetSpotDetPOST> call, Throwable t) {

            }
        });
    }

    //create action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.dmenu, menu);
        if(isFavorite){
            menu.getItem(0).setIcon(R.drawable.star_on);
        }
        else{
            menu.getItem(0).setIcon(R.drawable.star_off);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //handle button activities
    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
        //get id of selected item
        int id = item.getItemId();

        //if selected id matches my button's id
        if(id == R.id.favoriteButton){
            final Drawable starOn = getDrawable(R.drawable.star_on);
            final Drawable starOff = getDrawable(R.drawable.star_off);
            //compare to see if favorited using the drawables
            if(isFavorite){
                item.setIcon(starOff);

                mApiService.remSpotFav(token, spotId).enqueue(new Callback<RemoveFavPOST>() {
                    @Override
                    public void onResponse(Call<RemoveFavPOST> call,
                                           Response<RemoveFavPOST> response) {
                    }

                    @Override
                    public void onFailure(Call<RemoveFavPOST> call, Throwable t) {

                    }
                });
            }
            else{
                item.setIcon(starOn);
                mApiService.addSpotFav(token, spotId).enqueue(new Callback<AddFavPOST>() {
                    @Override
                    public void onResponse(Call<AddFavPOST> call, Response<AddFavPOST> response) {
                    }

                    @Override
                    public void onFailure(Call<AddFavPOST> call, Throwable t) {

                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSpotText(String string, LinearLayout linearLayout){
        TextView textView = new TextView(getApplicationContext());
        textView.setText(string);
        textView.setLayoutParams(lp);
        linearLayout.addView(textView);
    }

    private void addRow(String title, String detail){
        row = new TableRow(getApplicationContext());
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addSpotText(title, linearLayout);
        addSpotText(detail, linearLayout);
        row.addView(linearLayout);
        tableLayout.addView(row);
    }
}

