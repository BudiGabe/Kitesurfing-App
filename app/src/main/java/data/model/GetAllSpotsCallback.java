package data.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllSpotsCallback implements Callback<GetAllSpotsPOST> {
    private String token;
    private TableLayout tableLayout;
    private ImageButton favButton;
    private LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Drawable starOn;
    private Drawable starOff;
    private APIService mApiService;
    private Context context;

    public GetAllSpotsCallback(String token, TableLayout tableLayout, ImageButton favButton, Drawable starOn, Drawable starOff, APIService mApiService, Context context){
        this.token = token;
        this.tableLayout = tableLayout;
        this.favButton = favButton;
        this.starOn = starOn;
        this.starOff = starOff;
        this.mApiService = mApiService;
        this.context = context;
    }

    @Override
    public void onResponse(Call<GetAllSpotsPOST> call, Response<GetAllSpotsPOST> response) {
        List<GetAllSpotsResult> spotList = response.body().getResult();
        for(int i = 0; i < spotList.size(); i++){
            //initialize all needed objects
            TableRow row = new TableRow(context);
            LinearLayout textLayout = new LinearLayout(context);
            LinearLayout buttonLayout = new LinearLayout(context);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            //get all needed spot information
            GetAllSpotsResult spotCurrent = spotList.get(i);
            String spotName = spotCurrent.getName();
            String spotCountry = spotCurrent.getCountry();
            String spotId = spotCurrent.getId();
            Boolean isFavorite = spotCurrent.getIsFavorite();
            //add spot information to row
            addSpotText(spotName, textLayout);
            addSpotText(spotCountry, textLayout);
            addFavButton(lp, spotCurrent, buttonLayout);
            row.addView(textLayout);
            row.addView(buttonLayout);
            row.setTag(spotId);
            row.setOnClickListener(new RowListener(context, token, spotId, isFavorite));
            tableLayout.addView(row);
        }
    }

    @Override
    public void onFailure(Call<GetAllSpotsPOST> call, Throwable t) {

    }

    private void addSpotText(String string, LinearLayout textLayout){
        TextView spot = new TextView(context);
        spot.setText(string);
        spot.setLayoutParams(lp);
        textLayout.addView(spot);
    }

    private void addFavButton(LinearLayout.LayoutParams lp, GetAllSpotsResult spotCurrent, LinearLayout buttonLayout){
        //create every button
        favButton = new ImageButton(context);
        favButton.setLayoutParams(lp);
        //set tag for easier access to spotId
        favButton.setTag(spotCurrent.getId());
        //set button image
        if(spotCurrent.getIsFavorite()){
            favButton.setBackground(starOn);
        }
        else{
            favButton.setBackground(starOff);
        }
        favButton.setOnClickListener(new ButtonListener(token,mApiService,starOn,starOff));
        buttonLayout.addView(favButton);
    }
}
