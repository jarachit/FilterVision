package com.example.filtervision2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.graphics.PorterDuff;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.AppCompatSeekBar;
public class Filters extends AppCompatActivity {

    private static final float[] INVERTED = {
            -1F, 0, 0, 0, 255F,
            0, -1F, 0, 0, 255F,
            0, 0, 1F, 0, 255F,
            0, 0, 0, 1F, 0,
    };

    private static final float[] GRAYSCALE = {
            0.3F, 0.59F, 0.11F, 0F, 0,
            0.3F, 0.59F, 0.11F, 0F, 0,
            0.3F, 0.59F, 0.11F, 0F, 0,
            0F, 0F, 0F, 1F, 0,
    };
    private static final float[] PROTANOPIA = {
            0.567F, 0.433F, 0F, 0F, 0,
            0.558F, 0.442F, 0F, 0F, 0,
            0F, 0.242F, 0.758F, 0F, 0,
            0F, 0F, 0F, 1F, 0,
    };

    private static final float[] DEUTERANOPIA = {
            0.625F, 0.375F, 0, 0, 0,
            0.7F, 0.3F, 0, 0, 0,
            0, 0.3F, 0.7F, 0, 0,
            0, 0, 0, 1, 0,
    };

    private static final float[] TRITANOPIA = {
            0.95F, 0.05F, 0, 0, 0,
            0, 0.433F, 0.567F, 0, 0,
            0, 0.475F, 0.525F, 0, 0,
            0, 0, 0, 1, 0,
    };

    private static final int RESULT_LOAD_IMAGE = 1;
    RadioButton def;
    RadioButton red;
    RadioButton green;
    RadioButton blue;
    RadioButton protan;
    RadioButton deuteran;
    RadioButton tritan;
    RadioButton gray;
    RadioButton invert;
    RadioButton custom;
    SeekBar red_slider;
    SeekBar green_slider;
    SeekBar blue_slider;
    private final int GALLERY_REQ_CODE = 1;
    ImageView imgAfter;
    ImageView imgBefore;
    int redVal;
    int greenVal;
    int blueVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        def = findViewById(R.id.default_button);
        def.setChecked(true);
        red = findViewById(R.id.red_button);
        green = findViewById(R.id.green_button);
        blue = findViewById(R.id.blue_button);
        protan = findViewById(R.id.protan);
        deuteran = findViewById(R.id.deuteran);
        tritan = findViewById(R.id.tritan);
        gray = findViewById(R.id.gray);
        invert = findViewById(R.id.invert);
        custom = findViewById(R.id.custom_button);

        red_slider = findViewById(R.id.red_slider);
        green_slider = findViewById(R.id.green_slider);
        blue_slider = findViewById(R.id.blue_slider);

        imgAfter = findViewById(R.id.img_after);
        imgBefore = findViewById(R.id.img_before);
        Button btnGallery = findViewById(R.id.btn_gallery);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
        red_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redVal = progress;
                def.setChecked(false);
                red.setChecked(false);
                green.setChecked(false);
                blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                imgAfter.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        green_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                greenVal = progress;
                def.setChecked(false);
                red.setChecked(false);
                green.setChecked(false);
                blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                imgAfter.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blue_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blueVal = progress;
                def.setChecked(false);
                red.setChecked(false);
                green.setChecked(false);
                blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                imgAfter.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onClick(View view) {
        if (def.isChecked()) {
            imgAfter.getDrawable().clearColorFilter();
        } else if (red.isChecked()) {
            imgAfter.getDrawable().setColorFilter(0xFFFF7276, PorterDuff.Mode.MULTIPLY);
        } else if (green.isChecked()) {
            imgAfter.getDrawable().setColorFilter(0xFF018786, PorterDuff.Mode.MULTIPLY);
        } else if (blue.isChecked()) {
            imgAfter.getDrawable().setColorFilter(0xFF3700B3, PorterDuff.Mode.MULTIPLY);
        } else if (protan.isChecked()) {
            imgAfter.setColorFilter(new ColorMatrixColorFilter(PROTANOPIA));
        } else if (deuteran.isChecked()) {
            imgAfter.setColorFilter(new ColorMatrixColorFilter(DEUTERANOPIA));
        } else if (tritan.isChecked()) {
            imgAfter.setColorFilter(new ColorMatrixColorFilter(TRITANOPIA));
        } else if (gray.isChecked()) {
            imgAfter.setColorFilter(new ColorMatrixColorFilter(GRAYSCALE));
        } else if (invert.isChecked()) {
            imgAfter.setColorFilter(new ColorMatrixColorFilter(INVERTED));
        } else if (red_slider.getProgress() == 0 && green_slider.getProgress() == 0 && blue_slider.getProgress() == 0) {
            imgAfter.getDrawable().clearColorFilter();
        }  else if (custom.isChecked()) {
            imgAfter.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
        } else {
            imgAfter.getDrawable().clearColorFilter();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == GALLERY_REQ_CODE) {

                imgAfter.setImageURI(data.getData());
                onClick(imgAfter);
                imgBefore.setImageURI(data.getData());
                imgBefore.clearColorFilter();
            }
        }

    }
}