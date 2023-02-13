package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.graphics.PorterDuff;
import android.widget.SeekBar;

import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {
    SwitchCompat red;
    SwitchCompat def;
    SwitchCompat cust1;
    SwitchCompat cust2;
    SeekBar red_slider;
    SeekBar green_slider;
    SeekBar blue_slider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = findViewById(R.id.red_switch);
        def = findViewById(R.id.default_switch);
        cust1 = findViewById(R.id.custom_switch1);
        cust2 = findViewById(R.id.custom_switch2);
        red_slider = findViewById(R.id.red_slider);
        green_slider = findViewById(R.id.green_slider);
        blue_slider = findViewById(R.id.blue_slider);
        red_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                def.setChecked(false);
                cust1.setChecked(false);
                red.setChecked(false);
                cust2.setChecked(false);
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
                def.setChecked(false);
                cust1.setChecked(false);
                red.setChecked(false);
                cust2.setChecked(false);
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
                def.setChecked(false);
                cust1.setChecked(false);
                red.setChecked(false);
                cust2.setChecked(false);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void selectDefault(View view) {
        red.setChecked(false);
        cust1.setChecked(false);
        cust2.setChecked(false);
        red_slider.setProgress(0);
        green_slider.setProgress(0);
        blue_slider.setProgress(0);
    }

    public void selectRed(View view) {
        def.setChecked(false);
        cust1.setChecked(false);
        cust2.setChecked(false);
        red_slider.setProgress(0);
        green_slider.setProgress(0);
        blue_slider.setProgress(0);
    }
    public void selectCustom1(View view) {
        red.setChecked(false);
        def.setChecked(false);
        cust2.setChecked(false);
        red_slider.setProgress(0);
        green_slider.setProgress(0);
        blue_slider.setProgress(0);
    }

    public void selectCustom2(View view) {
        def.setChecked(false);
        cust1.setChecked(false);
        red.setChecked(false);
        red_slider.setProgress(0);
        green_slider.setProgress(0);
        blue_slider.setProgress(0);
    }

    public void applyFilter(View view) {
        ImageView mountains = findViewById(R.id.image);
        if (red.isChecked()) {
            mountains.getDrawable().setColorFilter(0xffff7276, PorterDuff.Mode.MULTIPLY);
        } else if (def.isChecked()) {
            mountains.getDrawable().clearColorFilter();
        } else if (cust1.isChecked()) {
            mountains.getDrawable().setColorFilter(0xFF3700B3, PorterDuff.Mode.MULTIPLY);
        } else if (cust2.isChecked()) {
            mountains.getDrawable().setColorFilter(0xFF018786, PorterDuff.Mode.MULTIPLY);
        } else if (red_slider.getProgress() == 0 && green_slider.getProgress() == 0 && blue_slider.getProgress() == 0){
            mountains.getDrawable().clearColorFilter();
        } else {
            mountains.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
        }
    }
    public void selectImage1(View view) {
        SwitchCompat im2 = findViewById(R.id.select_image2);
        im2.setChecked(false);
    }

    public void selectImage2(View view) {
        SwitchCompat im1 = findViewById(R.id.select_image1);
        im1.setChecked(false);
    }
    public void applyImage(View view) {
        SwitchCompat im1 = findViewById(R.id.select_image1);
        SwitchCompat im2 = findViewById(R.id.select_image2);
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