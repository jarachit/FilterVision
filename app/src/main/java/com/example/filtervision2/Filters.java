package com.example.filtervision2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ColorFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.PorterDuff;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Vector;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.AppCompatSeekBar;
public class Filters extends AppCompatActivity {
    private SharedMemory mSharedMemory;
    private ToggleButton mToggleButton;

    private static final float[] INVERTED = {
            -1F, 0, 0, 0, 255F,
            0, -1F, 0, 0, 255F,
            0, 0, 1F, 0, 255F,
            0, 0, 0, 1F, 0,
    };

    private static final float[] GRAYSCALE = {
            0.33F, 0.33F, 0.33F, 0F, 0,
            0.33F, 0.33F, 0.33F, 0F, 0,
            0.33F, 0.33F, 0.33F, 0F, 0,
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
    private static final float[] defaultFilter = {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0,
    };
    private static final float[] redFilter = {
            1, 1, 1, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0,
    };
    private static final float[] greenFilter = {
            0, 0, 0, 0, 0,
            1, 1, 1, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0,
    };

    private static final float[] blueFilter = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            1, 1, 1, 0, 0,
            0, 0, 0, 1, 0,
    };
    private float[] customFilter = {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0,
    };

    private ColorMatrixColorFilter currentFilter = new ColorMatrixColorFilter(defaultFilter);
    private ColorMatrixColorFilter prevFilter;

    private Vector<ColorMatrixColorFilter> previousFilters = new Vector<ColorMatrixColorFilter>();
    private boolean prevFilterExists = false;

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
    SeekBar alpha_slider;///
    private final int GALLERY_REQ_CODE = 1;
    ImageView imgAfter;
    ImageView imgBefore;
    int redVal;
    int greenVal;
    int blueVal;
    int alphaVal;
    EditText red_number;
    EditText green_number;
    EditText blue_number;
    EditText alpha_number; ////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        def = findViewById(R.id.default_button);
        def.setChecked(true);
        //red = findViewById(R.id.red_button);
        //green = findViewById(R.id.green_button);
        //blue = findViewById(R.id.blue_button);
        protan = findViewById(R.id.protan);
        deuteran = findViewById(R.id.deuteran);
        tritan = findViewById(R.id.tritan);
        gray = findViewById(R.id.gray);
        invert = findViewById(R.id.invert);
        custom = findViewById(R.id.custom_button);


        imgAfter = findViewById(R.id.img_after);
        imgBefore = findViewById(R.id.img_before);
        Button btnGallery = findViewById(R.id.btn_gallery);

        red_number = findViewById(R.id.red_number);
        green_number = findViewById(R.id.green_number);
        blue_number = findViewById(R.id.blue_number);
        alpha_number = findViewById(R.id.alpha_number); ////

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        red_slider = findViewById(R.id.red_slider);
        green_slider = findViewById(R.id.green_slider);
        blue_slider = findViewById(R.id.blue_slider);
        alpha_slider = findViewById(R.id.alpha_slider); ////
        //
        mToggleButton = findViewById(R.id.start_button);
        mSharedMemory = new SharedMemory(this);
        //
//        SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mSharedMemory.setAlpha(0);
//                mSharedMemory.setRed(red_slider.getProgress());
//                mSharedMemory.setGreen(green_slider.getProgress());
//                mSharedMemory.setBlue(blue_slider.getProgress());
//
//                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
//                    Intent i = new Intent(Filters.this, ScreenFilterService.class);
//                    startService(i);
//                }
//                mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_INACTIVE);
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//
//        };
        red_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redVal = progress;
                red_number.setText(Integer.toString(redVal));
                def.setChecked(false);
                //red.setChecked(false);
                //green.setChecked(false);
                //blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                //
                mSharedMemory.setAlpha(alpha_slider.getProgress());
                mSharedMemory.setRed(red_slider.getProgress());
                mSharedMemory.setGreen(green_slider.getProgress());
                mSharedMemory.setBlue(blue_slider.getProgress());

                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
                    Intent i = new Intent(Filters.this, ScreenFilterService.class);
                    startService(i);
                }
                mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE);
                //
                customFilter[0] = (float) red_slider.getProgress() / 255;
                customFilter[6] = (float) green_slider.getProgress() / 255;
                customFilter[12] = (float) blue_slider.getProgress() / 255;
                customFilter[18] = (float) alpha_slider.getProgress() / 255;
                imgAfter.getDrawable().setColorFilter(new ColorMatrixColorFilter(customFilter));
                currentFilter = new ColorMatrixColorFilter(customFilter);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        green_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                greenVal = progress;
                green_number.setText(Integer.toString(greenVal));
                def.setChecked(false);
                //red.setChecked(false);
                //green.setChecked(false);
                //blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                //
                mSharedMemory.setAlpha(alpha_slider.getProgress());
                mSharedMemory.setRed(red_slider.getProgress());
                mSharedMemory.setGreen(green_slider.getProgress());
                mSharedMemory.setBlue(blue_slider.getProgress());

                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
                    Intent i = new Intent(Filters.this, ScreenFilterService.class);
                    startService(i);
                }
                mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE);
                //
                customFilter[0] = (float) red_slider.getProgress() / 255;
                customFilter[6] = (float) green_slider.getProgress() / 255;
                customFilter[12] = (float) blue_slider.getProgress() / 255;
                customFilter[18] = (float) alpha_slider.getProgress() / 255;
                imgAfter.getDrawable().setColorFilter(new ColorMatrixColorFilter(customFilter));
                currentFilter = new ColorMatrixColorFilter(customFilter);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blue_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blueVal = progress;
                blue_number.setText(Integer.toString(blueVal));
                def.setChecked(false);
                //red.setChecked(false);
                //green.setChecked(false);
                //blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                //
                mSharedMemory.setAlpha(alpha_slider.getProgress());
                mSharedMemory.setRed(red_slider.getProgress());
                mSharedMemory.setGreen(green_slider.getProgress());
                mSharedMemory.setBlue(blue_slider.getProgress());

                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
                    Intent i = new Intent(Filters.this, ScreenFilterService.class);
                    startService(i);
                }
                mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE);
                //
                customFilter[0] = (float) red_slider.getProgress() / 255;
                customFilter[6] = (float) green_slider.getProgress() / 255;
                customFilter[12] = (float) blue_slider.getProgress() / 255;
                customFilter[18] = (float) alpha_slider.getProgress() / 255;
                imgAfter.getDrawable().setColorFilter(new ColorMatrixColorFilter(customFilter));
//                imgAfter.getDrawable().setColorFilter(Color.rgb(red_slider.getProgress(), green_slider.getProgress(), blue_slider.getProgress()), PorterDuff.Mode.MULTIPLY);
                currentFilter = new ColorMatrixColorFilter(customFilter);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        alpha_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alphaVal = progress;
                alpha_number.setText(Integer.toString(alphaVal));
                def.setChecked(false);
                //red.setChecked(false);
                //green.setChecked(false);
                //blue.setChecked(false);
                protan.setChecked(false);
                deuteran.setChecked(false);
                tritan.setChecked(false);
                gray.setChecked(false);
                invert.setChecked(false);
                custom.setChecked(true);
                //
                mSharedMemory.setAlpha(alpha_slider.getProgress());
                mSharedMemory.setRed(red_slider.getProgress());
                mSharedMemory.setGreen(green_slider.getProgress());
                mSharedMemory.setBlue(blue_slider.getProgress());

                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
                    Intent i = new Intent(Filters.this, ScreenFilterService.class);
                    startService(i);
                }
                mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE);
                //
                customFilter[0] = (float) red_slider.getProgress() / 255;
                customFilter[6] = (float) green_slider.getProgress() / 255;
                customFilter[12] = (float) blue_slider.getProgress() / 255;
                customFilter[18] = (float) alpha_slider.getProgress() / 255;
                imgAfter.getDrawable().setColorFilter(new ColorMatrixColorFilter(customFilter));
                currentFilter = new ColorMatrixColorFilter(customFilter);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        TextView.OnEditorActionListener editingActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(red_number.getText().toString())) {
                        redVal = 0;
                    }
                    else {
                        redVal = Integer.parseInt(red_number.getText().toString());
                    }
                    red_slider.setProgress(redVal);
                }
                return true;
            }
        };
        red_number.setOnEditorActionListener(editingActionListener);
        TextView.OnEditorActionListener editingActionListener2 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(green_number.getText().toString())) {
                        greenVal = 0;
                    }
                    else {
                        greenVal = Integer.parseInt(green_number.getText().toString());
                    }
                    green_slider.setProgress(greenVal);
                }
                return true;
            }
        };
        green_number.setOnEditorActionListener(editingActionListener2);
        TextView.OnEditorActionListener editingActionListener3 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(blue_number.getText().toString())) {
                        blueVal = 0;
                    }
                    else {
                        blueVal = Integer.parseInt(blue_number.getText().toString());
                    }
                    blue_slider.setProgress(blueVal);
                }
                return true;
            }
        };
        blue_number.setOnEditorActionListener(editingActionListener3);

        TextView.OnEditorActionListener editingActionListener4 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(alpha_number.getText().toString())) {
                        alphaVal = 255;
                    }
                    else {
                        alphaVal = Integer.parseInt(alpha_number.getText().toString());
                    }
                    alpha_slider.setProgress(alphaVal);
                }
                return true;
            }
        };
        alpha_number.setOnEditorActionListener(editingActionListener4);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Filters.this, ScreenFilterService.class);
                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
                    stopService(i);
                } else {
                    startService(i);
                }

            }
        });
    }

    public void applyFilter(View view) {
        imgBefore.setColorFilter(currentFilter);
        if (prevFilterExists) {
            previousFilters.add(prevFilter);
        }
        prevFilter = currentFilter;
        prevFilterExists = true;
        if (previousFilters.size() > 10) {
            previousFilters.remove(0);
        }
    }
    public void revertFilter(View view) {
        if (previousFilters.isEmpty()) {
            prevFilterExists = false;
            return;
        }
        ColorMatrixColorFilter revertedFilter = previousFilters.lastElement();
        imgBefore.setColorFilter(revertedFilter);
        previousFilters.remove(previousFilters.size() - 1);
    }

    public void onClick(View view) {
        if (def.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(defaultFilter);
        } /*else if (red.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(redFilter);
        } else if (green.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(greenFilter);
        } else if (blue.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(blueFilter);
        } */else if (protan.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(PROTANOPIA);
        } else if (deuteran.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(DEUTERANOPIA);
        } else if (tritan.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(TRITANOPIA);
        } else if (gray.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(GRAYSCALE);
        } else if (invert.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(INVERTED);
        } else if (red_slider.getProgress() == 0 && green_slider.getProgress() == 0 && blue_slider.getProgress() == 0 && alpha_slider.getProgress() == 0) {
            currentFilter = new ColorMatrixColorFilter(defaultFilter);
        }  else if (custom.isChecked()) {
            currentFilter = new ColorMatrixColorFilter(customFilter);
        } else {
            currentFilter = new ColorMatrixColorFilter(defaultFilter);
        }
        imgAfter.setColorFilter(currentFilter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == GALLERY_REQ_CODE) {

                imgAfter.setImageURI(data.getData());
                onClick(imgAfter);
                imgBefore.setImageURI(data.getData());
                imgBefore.setColorFilter(new ColorMatrixColorFilter(defaultFilter));
            }
        }

    }
}