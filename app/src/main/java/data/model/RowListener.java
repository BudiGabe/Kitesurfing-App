package data.model;

import android.content.Context;
import android.content.Intent;;
import android.view.View;

import com.example.androidproblem.Details;

public class RowListener implements View.OnClickListener {

    private Context context;
    private String token;
    private String spotId;
    private boolean isFavorite;

    public RowListener(Context context, String token, String spotId, boolean isFavorite){
        this.context = context;
        this.token = token;
        this.spotId = spotId;
        this.isFavorite = isFavorite;
    }
    @Override
    public void onClick(View v) {
        //create a new intent to start Details activity
        Intent intent = new Intent(context, Details.class);
        //will need token and spotId for next POST Request
        intent.putExtra("token", token);
        intent.putExtra("spotId", spotId);
        intent.putExtra("isFavorite", isFavorite);
        context.startActivity(intent);
    }
}
