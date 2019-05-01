package com.example.androidproblem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.model.Resources;


public class FilterActivity extends AppCompatActivity {

    private EditText country;
    private EditText windProb;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        country = (EditText) findViewById(R.id.country);
        windProb = (EditText) findViewById(R.id.windProb);
        Button apply = (Button) findViewById(R.id.apply);

        // Get bundle from intent
        Bundle data = getIntent().getExtras();
        // Get res object from parcelable
        res = data.getParcelable("Resources");

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryString = country.getText().toString();
                String windProbString = windProb.getText().toString();
                res.setCountry(countryString);
                res.setWindProb(windProbString);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Resource", res);
                startActivity(intent);

            }
        });

    }
}
