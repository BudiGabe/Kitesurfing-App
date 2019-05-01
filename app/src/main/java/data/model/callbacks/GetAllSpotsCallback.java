package data.model.callbacks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.androidproblem.R;

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
    private LinearLayout.LayoutParams lpText;
    private RelativeLayout.LayoutParams lpButton;
    private TableLayout.LayoutParams lpRow;
    private Drawable starOn;
    private Drawable starOff;
    private APIService mApiService;
    private Context context;
    private int titleColor;
    private int subtitleColor;
    private static final String LOG_TAG = "getAllSpotsCallBack";

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
        this.lpText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.lpButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.lpRow = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        this.titleColor = ContextCompat.getColor(context, R.color.colorTitle);
        this.subtitleColor = ContextCompat.getColor(context, R.color.colorSubtitle);
    }

    @Override
    public void onResponse(Call<GetAllSpotsPOST> call, Response<GetAllSpotsPOST> response) {
        List<GetAllSpotsResult> spotList = response.body().getResult();
        for(int i = 0; i < spotList.size(); i++){
            // Initialize all needed objects
            TableRow row = new TableRow(context);
            LinearLayout textLayout = new LinearLayout(context);
            RelativeLayout buttonLayout = new RelativeLayout(context);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            // Get all needed spot information
            GetAllSpotsResult spotCurrent = spotList.get(i);
            //create new spot with information
            Spot spot = new Spot(spotCurrent.getName(),
                    spotCurrent.getCountry(),
                    spotCurrent.getId(),
                    spotCurrent.getIsFavorite());
            // Add spot information to row
            addSpotText(spot.getName(), textLayout, 18, titleColor);
            addSpotText(spot.getCountry(), textLayout, 14, subtitleColor);
            addFavButton(spot, buttonLayout);
            row.addView(textLayout);
            row.addView(buttonLayout);
            // Set up the row
            lpRow.setMargins(0,15,0,15);
            row.setLayoutParams(lpRow);
            row.setOnClickListener(new RowListener(context, token, spot));
            // Add row to table
            tableLayout.addView(row);

        }
    }

    @Override
    public void onFailure(Call<GetAllSpotsPOST> call, Throwable t) {
        Log.e(LOG_TAG, "Getting spots failed");
    }

    // Method to create TextViews in rows
    private void addSpotText(String string, LinearLayout textLayout, float size, int color){
        TextView spot = new TextView(context);
        spot.setText(string);
        spot.setTextSize(size);
        spot.setTextColor(color);
        lpText.setMargins(0,15,0,15);
        spot.setLayoutParams(lpText);
        textLayout.addView(spot);
    }

    // Method to create ImageButton in rows
    private void addFavButton(Spot spot, RelativeLayout buttonLayout){
        favButton = new ImageButton(context);
        lpButton.setMargins(0,50,0,50);
        lpButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        favButton.setLayoutParams(lpButton);
        // Set tag for easier access to spotId
        favButton.setTag(spot.getId());
        // Set button image
        if(spot.getIsFavorite()){
            favButton.setBackground(starOn);
        }
        else{
            favButton.setBackground(starOff);
        }
        favButton.setOnClickListener(new ButtonListener(token,mApiService, starOn, starOff, spot));
        buttonLayout.addView(favButton);
    }
}
