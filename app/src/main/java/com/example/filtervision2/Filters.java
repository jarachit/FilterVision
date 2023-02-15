package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.graphics.PorterDuff;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.AppCompatSeekBar;
public class Filters extends AppCompatActivity {
    RadioButton def;
    RadioButton red;
    RadioButton green;
    RadioButton blue;
    RadioButton custom;
    SeekBar red_slider;
    SeekBar green_slider;
    SeekBar blue_slider;
    RadioButton select_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        def = findViewById(R.id.default_button);
        def.setChecked(true);
        red = findViewById(R.id.red_button);
        green = findViewById(R.id.green_button);
        blue = findViewById(R.id.blue_button);
        custom = findViewById(R.id.custom_button);

        red_slider = findViewById(R.id.red_slider);
        green_slider = findViewById(R.id.green_slider);
        blue_slider = findViewById(R.id.blue_slider);

        select_image = findViewById(R.id.select_image2);
        select_image.setChecked(true);
        red_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
//                def.setChecked(false);
//                red.setChecked(false);
//                green.setChecked(false);
//                blue.setChecked(false);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        green_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
//                def.setChecked(false);
//                red.setChecked(false);
//                green.setChecked(false);
//                blue.setChecked(false);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blue_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
//                def.setChecked(false);
//                red.setChecked(false);
//                green.setChecked(false);
//                blue.setChecked(false);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void onClick(View view) {
//        red_slider.setProgress(0);
//        green_slider.setProgress(0);
//        blue_slider.setProgress(0);

        switch(view.getId()){
            case R.id.red_button:
                if(!red.isChecked()){
                    red.setChecked(true);
                }
        }
        // more functionality, if needed
    }

    public void applyFilter(View view) {
        ImageView mountains = findViewById(R.id.image);
        if (def.isChecked()) {
            mountains.getDrawable().clearColorFilter();
        } else if (red.isChecked()) {
            mountains.getDrawable().setColorFilter(0xffff7276, PorterDuff.Mode.MULTIPLY);
        } else if (green.isChecked()) {
            mountains.getDrawable().setColorFilter(0xFF018786, PorterDuff.Mode.MULTIPLY);
        } else if (blue.isChecked()) {
            mountains.getDrawable().setColorFilter(0xFF3700B3, PorterDuff.Mode.MULTIPLY);
        } else if (red_slider.getProgress() == 0 && green_slider.getProgress() == 0 && blue_slider.getProgress() == 0) {
            mountains.getDrawable().clearColorFilter();
        }  else if (custom.isChecked()) {
            mountains.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
        } else {
            mountains.getDrawable().clearColorFilter();
        }
    }

    public void applyImage(View view) {
        RadioButton im1 = findViewById(R.id.select_image1);
        RadioButton im2 = findViewById(R.id.select_image2);
        ImageView image = findViewById(R.id.image);
        if (im1.isChecked()) {
            image.setImageResource(R.drawable.palm_tree);
            applyFilter(image);
        } else if (im2.isChecked()) {
            image.setImageResource(R.drawable.mountains);
            applyFilter(image);
        }
    }

}