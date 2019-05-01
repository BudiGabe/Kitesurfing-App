package com.example.androidproblem;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import data.model.posts.AddFavPOST;
import data.model.posts.GetSpotDetPOST;
import data.model.results.GetSpotDetResult;
import data.model.posts.RemoveFavPOST;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {

    private APIService mApiService;
    private String token;
    private boolean isFavorite;
    private Map<String,String> data = new HashMap<>();
    private Drawable starOn;
    private Drawable starOff;
    private int titleColor;
    private int subtitleColor;
    private static final String LOG_TAG = "Details";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final LinearLayout.LayoutParams lp;
        final TableLayout.LayoutParams lpRow;
        final TableLayout tableLayout;
        Bundle extras;
        String spotId;

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        starOn = getDrawable(R.drawable.star_on_action);
        starOff = getDrawable(R.drawable.star_off_action);
        titleColor = ContextCompat.getColor(getApplicationContext(), R.color.colorTitle);
        subtitleColor = ContextCompat.getColor(getApplicationContext(), R.color.colorSubtitle);

        // Initialize the API
        mApiService = ApiUtils.getAPIService();

        // Get data from intent
        extras = getIntent().getExtras();
        token = extras.getString("token");
        spotId = extras.getString("spotId");
        isFavorite = extras.getBoolean("isFavorite");
        data.put("spotId", spotId);

        // Create new layout params for TextViews
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpRow = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        // Make the call
        mApiService.getSpotDet(token, data).enqueue(new Callback<GetSpotDetPOST>() {
            @Override
            public void onResponse(Call<GetSpotDetPOST> call, Response<GetSpotDetPOST> response) {
                GetSpotDetResult details = response.body().getResult();
                setTitle(details.getName());
                addRow("Country", details.getCountry(), lp, lpRow, tableLayout);
                addRow("Latitude",details.getLatitude().toString(), lp, lpRow, tableLayout);
                addRow("Longitude", details.getLongitude().toString(), lp, lpRow, tableLayout);
                addRow("Wind probability", details.getWindProbability().toString(), lp, lpRow,
                        tableLayout);
                addRow("When To Go", details.getWhenToGo(), lp, lpRow, tableLayout);
            }

            @Override
            public void onFailure(Call<GetSpotDetPOST> call, Throwable t) {

            }
        });
    }

    // Create action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.dmenu, menu);
        if(isFavorite){
            menu.getItem(0).setIcon(starOn);
        }
        else{
            menu.getItem(0).setIcon(starOff);
        }
        return super.onCreateOptionsMenu(menu);
    }

    // Handle button activities
    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
        // Get id of selected item
        int id = item.getItemId();

        // If selected id matches my button's id
        if(id == R.id.favoriteButton){
            // Compare to see if favorited using the drawables
            if(isFavorite){
                item.setIcon(starOff);
                isFavorite = false;
                mApiService.remSpotFav(token, data).enqueue(new Callback<RemoveFavPOST>() {
                    @Override
                    public void onResponse(Call<RemoveFavPOST> call,
                                           Response<RemoveFavPOST> response) {
                        Log.d(LOG_TAG, "Remove favorite successful");
                    }

                    @Override
                    public void onFailure(Call<RemoveFavPOST> call, Throwable t) {
                        Log.e(LOG_TAG, "Remove favorite failed");
                    }
                });
            }
            else{
                item.setIcon(starOn);
                isFavorite = true;
                mApiService.addSpotFav(token, data).enqueue(new Callback<AddFavPOST>() {
                    @Override
                    public void onResponse(Call<AddFavPOST> call, Response<AddFavPOST> response) {
                        Log.d(LOG_TAG, "Add favorite succesfull");
                    }

                    @Override
                    public void onFailure(Call<AddFavPOST> call, Throwable t) {
                        Log.e(LOG_TAG, "Add favorite failed");
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSpotText(String string, LinearLayout linearLayout, LinearLayout.LayoutParams lp, float size, int color){
        TextView textView = new TextView(getApplicationContext());
        textView.setText(string);
        textView.setTextColor(color);
        textView.setTextSize(size);
        lp.setMargins(0,15,0,15);
        textView.setLayoutParams(lp);
        linearLayout.addView(textView);
    }

    private void addRow(String title, String detail, LinearLayout.LayoutParams lp,
                        TableLayout.LayoutParams lpRow, TableLayout tableLayout){
        TableRow row = new TableRow(getApplicationContext());
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addSpotText(title, linearLayout, lp, 18, titleColor);
        addSpotText(detail, linearLayout, lp, 14, subtitleColor);
        row.addView(linearLayout);
        lpRow.setMargins(0,15,0,15);
        row.setLayoutParams(lpRow);
        tableLayout.addView(row);
    }
}

