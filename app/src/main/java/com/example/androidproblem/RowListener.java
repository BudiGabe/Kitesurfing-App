package com.example.androidproblem;

import android.content.Context;
import android.content.Intent;;
import android.view.View;

public class RowListener implements View.OnClickListener {

    private Context context;
    private String token;

    public RowListener(Context context, String token){
        this.context = context;
        this.token = token;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, Details.class);
        intent.putExtra("token", token);
        intent.putExtra("spotId", v.getTag().toString());
        context.startActivity(intent);
    }
}
