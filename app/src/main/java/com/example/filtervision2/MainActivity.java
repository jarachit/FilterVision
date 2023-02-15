package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.graphics.PorterDuff;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView filtersCard, testCard, tutorialCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filtersCard = (CardView) findViewById(R.id.filters);
        testCard = (CardView) findViewById(R.id.test);
        tutorialCard = (CardView) findViewById(R.id.tutorial);

        filtersCard.setOnClickListener(this);
        testCard.setOnClickListener(this);
        tutorialCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i ;

        switch (v.getId()) {
            case R.id.filters: i = new Intent(this, Filters.class); startActivity(i); break ;
            case R.id.test: i = new Intent(this, Test.class); startActivity(i); break ;
            case R.id.tutorial: i = new Intent(this, Tutorial.class); startActivity(i); break ;
            default:break ;
        }
    }
}
