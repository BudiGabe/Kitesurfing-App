package data.model.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidproblem.Details;

import data.model.Spot;

public class RowListener implements View.OnClickListener {

    private Context context;
    private String token;
    private Spot spot;

    public RowListener(Context context, String token, Spot spot){
        this.context = context;
        this.token = token;
        this.spot = spot;
    }
    @Override
    public void onClick(View v) {
        //create a new intent to start Details activity
        Intent intent = new Intent(context, Details.class);
        //will need token and spotId for next POST Request
        intent.putExtra("token", token);
        intent.putExtra("spotId", spot.getId());
        intent.putExtra("isFavorite", spot.getIsFavorite());
        context.startActivity(intent);
    }
}
