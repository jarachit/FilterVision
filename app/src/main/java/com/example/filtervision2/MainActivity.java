package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView filtersCard, testCard, tutorialCard, presetsCard, settingsCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filtersCard = (CardView) findViewById(R.id.filters);
        testCard = (CardView) findViewById(R.id.test);
        tutorialCard = (CardView) findViewById(R.id.tutorial);
        presetsCard = (CardView) findViewById(R.id.presets);
        settingsCard = (CardView) findViewById(R.id.settings);



        filtersCard.setOnClickListener(this);
        testCard.setOnClickListener(this);
        tutorialCard.setOnClickListener(this);
        presetsCard.setOnClickListener(this);
        settingsCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i ;

        switch (v.getId()) {
            case R.id.filters: i = new Intent(this, Filters.class); startActivity(i); break ;
            case R.id.test: i = new Intent(this, Test.class); startActivity(i); break ;
            case R.id.tutorial: i = new Intent(this, Tutorial.class); startActivity(i); break ;
            case R.id.presets: i = new Intent(this, Presets.class); startActivity(i); break ;
            default:break ;
        }
    }
}
