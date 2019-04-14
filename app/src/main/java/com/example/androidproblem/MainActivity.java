package com.example.androidproblem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import data.model.TokenPost;
import data.remote.APIService;
import data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    Button getToken;
    TextView token;
    private APIService mApiService;
    static String EMAIL_VALID = "t1@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToken = (Button) findViewById(R.id.getToken);
        token = (TextView) findViewById(R.id.token);

        mApiService = ApiUtils.getAPIService();

        getToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.getToken(EMAIL_VALID).enqueue(new Callback<TokenPost>() {
                    @Override
                    public void onResponse(Call<TokenPost> call, Response<TokenPost> response) {
                        if(response.isSuccessful()){
                            showResponse(response.body().getResult().getToken());
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenPost> call, Throwable t) {

                    }
                    public void showResponse(String response){
                        token.setText(response);
                    }
                });

            }
        });

    }

}
