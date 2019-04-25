package data.model.callbacks;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import data.model.Spot;
import data.model.listeners.ButtonListener;
import data.model.listeners.RowListener;
import data.model.posts.GetAllSpotsPOST;
import data.model.results.GetAllSpotsResult;
import data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllSpotsCallback implements Callback<GetAllSpotsPOST> {
    private String token;
    private TableLayout tableLayout;
    private ImageButton favButton;
    private LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private RelativeLayout.LayoutParams lpButton = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private Drawable starOn;
    private Drawable starOff;
    private APIService mApiService;
    private Context context;

    public GetAllSpotsCallback(String token, TableLayout tableLayout, ImageButton favButton,
                               Drawable starOn,
                               Drawable starOff,
                               APIService mApiService,
                               Context context){
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
            RelativeLayout buttonLayout = new RelativeLayout(context);
            Spot spot = new Spot();
            textLayout.setOrientation(LinearLayout.VERTICAL);
            //get all needed spot information
            GetAllSpotsResult spotCurrent = spotList.get(i);
            spot.setName(spotCurrent.getName());
            spot.setCountry(spotCurrent.getCountry());
            spot.setId(spotCurrent.getId());
            spot.setIsFavorite(spotCurrent.getIsFavorite());
            //add spot information to row
            addSpotText(spot.getName(), textLayout, Typeface.BOLD, 20);
            addSpotText(spot.getCountry(), textLayout, Typeface.NORMAL, 14);
            addFavButton(lpButton, spot, buttonLayout);
            row.addView(textLayout);
            row.addView(buttonLayout);
            row.setOnClickListener(new RowListener(context, token, spot));
            tableLayout.addView(row);

        }
    }

    @Override
    public void onFailure(Call<GetAllSpotsPOST> call, Throwable t) {

    }

    //method to create TextViews in rows
    private void addSpotText(String string, LinearLayout textLayout, int style, float size){
        TextView spot = new TextView(context);
        spot.setText(string);
        spot.setTextSize(size);
        spot.setTypeface(null, style);
        lpText.setMargins(0,25,0,25);
        spot.setLayoutParams(lpText);
        textLayout.addView(spot);
    }
    //method to create ImageButton in rows
    private void addFavButton(RelativeLayout.LayoutParams lp, Spot spot,
                              RelativeLayout buttonLayout){
        //create every button
        favButton = new ImageButton(context);
        lp.setMargins(0,25,0,25);
        favButton.setLayoutParams(lp);
        //set tag for easier access to spotId
        favButton.setTag(spot.getId());
        //set button image
        if(spot.getIsFavorite()){
            favButton.setBackground(starOn);
        }
        else{
            favButton.setBackground(starOff);
        }
        favButton.setOnClickListener(new ButtonListener(token,mApiService, starOn, starOff,
                                                        spot));
        buttonLayout.addView(favButton);
    }
}
