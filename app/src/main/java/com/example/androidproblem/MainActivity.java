package com.example.androidproblem;

import android.app.ActionBar;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import data.model.GetAllSpotsPOST;
import data.model.GetAllSpotsResult;
import data.model.TokenPost;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ScrollView sv;
    private TableLayout tableLayout;
    private APIService mApiService;
    static String EMAIL_VALID = "t1@gmail.com";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sv = (ScrollView) findViewById(R.id.sv);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        mApiService = ApiUtils.getAPIService();

        //get token
        mApiService.getToken(EMAIL_VALID).enqueue(new Callback<TokenPost>() {
            @Override
            public void onResponse(Call<TokenPost> call, Response<TokenPost> response) {
                token = response.body().getResult().getToken();
                //get spot list
                mApiService.getAllSpots(token,"","").enqueue(new Callback<GetAllSpotsPOST>() {
                    @Override
                    public void onResponse(Call<GetAllSpotsPOST> call, Response<GetAllSpotsPOST> response) {
                        TextView spotName;
                        TextView spotCountry;
                        Button favButton;
                        List<GetAllSpotsResult> result = response.body().getResult();
                        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        for(int i = 0; i < result.size(); i++){
                            TableRow row = new TableRow(getApplicationContext());
                            LinearLayout textLayout = new LinearLayout(getApplicationContext());
                            textLayout.setOrientation(LinearLayout.VERTICAL);
                            LinearLayout buttonLayout = new LinearLayout(getApplicationContext());
                            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                            buttonLayout.setGravity(Gravity.RIGHT);
                            spotName = new TextView(getApplicationContext());
                            spotCountry = new TextView(getApplicationContext());
                            favButton = new  Button(getApplicationContext());
                            spotName.setText(result.get(i).getName());
                            spotName.setLayoutParams(lpView);
                            spotCountry.setText(result.get(i).getCountry());
                            spotCountry.setLayoutParams(lpView);
                            textLayout.addView(spotName);
                            textLayout.addView(spotCountry);
                            row.addView(textLayout);
                            favButton.setLayoutParams(lpView);
                            buttonLayout.addView(favButton);
                            row.addView(buttonLayout);
                            tableLayout.addView(row);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllSpotsPOST> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<TokenPost> call, Throwable t) {
            }
        });


    }

}
