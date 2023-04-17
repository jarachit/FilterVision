package com.example.filtervision2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ColorFilter;
import android.net.Uri;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.AppCompatSeekBar;
public class Filters extends AppCompatActivity {
    private SharedMemory mSharedMemory;

    SharedPreferences sp;
    private ToggleButton mToggleButton;

    private static final float[] INVERTED = {
            -1F, 0, 0, 0, 255F,
            0, -1F, 0, 0, 255F,
            0, 0, 1F, 0, 255F,
            0, 0, 0, 1F, 0,
    };
    private static final float[] GRAYSCALE = {
            84, 84, 84, 0, 0,
            84, 84, 84, 0, 0,
            84, 84, 84, 0, 0,
            0, 0, 0, 255, 0,
    };
    private static final float[] PROTANOPIA = {
            145, 110, 0, 0, 0,
            142, 113, 0, 0, 0,
            0, 62, 193, 0, 0,
            0, 0, 0, 255, 0,
    };

    private static final float[] DEUTERANOPIA = {
            159, 96, 0, 0, 0,
            179, 77, 0, 0, 0,
            0, 77, 179, 0, 0,
            0, 0, 0, 255, 0,
    };

    private static final float[] TRITANOPIA = {
            242, 13, 0, 0, 0,
            0, 110, 145, 0, 0,
            0, 121, 134, 0, 0,
            0, 0, 0, 255, 0,
    };
    private static final float[] defaultFilter = {
            255, 0, 0, 0, 0,
            0, 255, 0, 0, 0,
            0, 0, 255, 0, 0,
            0, 0, 0, 255, 0,
    };
    private static final float[] redFilter = {
            255, 255, 255, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 255, 0,
    };
    private static final float[] greenFilter = {
            0, 0, 0, 0, 0,
            255, 255, 255, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 255, 0,
    };

    private static final float[] blueFilter = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            255, 255, 255, 0, 0,
            0, 0, 0, 255, 0,
    };
    private float[] customFilter = {
            255, 0, 0, 0, 0,
            0, 255, 0, 0, 0,
            0, 0, 255, 0, 0,
            0, 0, 0, 255, 0,
    };

    public float[] currentFilter = {
            255, 0, 0, 0, 0,
            0, 255, 0, 0, 0,
            0, 0, 255, 0, 0,
            0, 0, 0, 255, 0,
    };
    private float[] prevFilter;

    private Vector<float[]> previousFilters = new Vector<float[]>();
    private boolean prevFilterExists = false;

    private static final int RESULT_LOAD_IMAGE = 1;
    RadioButton def;
    RadioButton protan;
    RadioButton deuteran;
    RadioButton tritan;
    RadioButton gray;
    RadioButton invert;
    RadioButton custom;
    SeekBar alpha_slider;///
    private final int GALLERY_REQ_CODE = 1;
    ImageView imgAfter;
    ImageView imgBefore;
    int RtoRVal = 255;
    int RtoGVal = 0;
    int RtoBVal = 0;
    int GtoRVal = 0;
    int GtoGVal = 255;
    int GtoBVal = 0;
    int BtoRVal = 0;
    int BtoGVal = 0;
    int BtoBVal = 255;
    int alphaVal = 255;
    EditText RtoR;
    EditText RtoG;
    EditText RtoB;
    EditText GtoR;
    EditText GtoG;
    EditText GtoB;
    EditText BtoR;
    EditText BtoG;
    EditText BtoB;
    EditText alpha_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        def = findViewById(R.id.default_button);
        def.setChecked(true);
        protan = findViewById(R.id.protan);
        deuteran = findViewById(R.id.deuteran);
        tritan = findViewById(R.id.tritan);
        gray = findViewById(R.id.gray);
        invert = findViewById(R.id.invert);
        custom = findViewById(R.id.custom_button);
        imgAfter = findViewById(R.id.img_after);
        imgBefore = findViewById(R.id.img_before);
        Button btnGallery = findViewById(R.id.btn_gallery);
        RtoR = findViewById(R.id.RtoR);
        RtoG = findViewById(R.id.RtoG);
        RtoB = findViewById(R.id.RtoB);
        GtoR = findViewById(R.id.GtoR);
        GtoG = findViewById(R.id.GtoG);
        GtoB = findViewById(R.id.GtoB);
        BtoR = findViewById(R.id.BtoR);
        BtoG = findViewById(R.id.BtoG);
        BtoB = findViewById(R.id.BtoB);
        alpha_number = findViewById(R.id.alpha_number);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
        alpha_slider = findViewById(R.id.alpha_slider);
        mToggleButton = findViewById(R.id.start_button);
        mSharedMemory = new SharedMemory(this);
        alpha_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alphaVal = progress;
                alpha_number.setText(Integer.toString(alphaVal));
                mSharedMemory.setAlpha(alpha_slider.getProgress());
                mSharedMemory.setRed(RtoRVal);
                mSharedMemory.setGreen(GtoGVal);
                mSharedMemory.setBlue(BtoBVal);

                if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
                    Intent i = new Intent(Filters.this, ScreenFilterService.class);
                    startService(i);
                }
                mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE);
                currentFilter[18] = (float) alpha_slider.getProgress();
                float[] filter = new float[currentFilter.length];
                for (int i = 0; i < currentFilter.length; i++) {
                    filter[i] = currentFilter[i] / 255;
                }
                imgAfter.setColorFilter(new ColorMatrixColorFilter(filter));
                updateMatrix();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        TextView.OnEditorActionListener editingActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE ) {
                    if (TextUtils.isEmpty(RtoR.getText().toString())) {
                        RtoRVal = 0;
                        RtoR.setText(Integer.toString(0));
                    }
                    else {
                        RtoRVal = Integer.parseInt(RtoR.getText().toString());
                        if (RtoRVal > 255) {
                            RtoRVal = 255;
                            RtoR.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[0] = RtoRVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        RtoR.setOnEditorActionListener(editingActionListener);
        TextView.OnEditorActionListener editingActionListener2 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(RtoG.getText().toString())) {
                        RtoGVal = 0;
                        RtoG.setText(Integer.toString(0));
                    }
                    else {
                        RtoGVal = Integer.parseInt(RtoG.getText().toString());
                        if (RtoGVal > 255) {
                            RtoGVal = 255;
                            RtoG.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[1] = RtoGVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        RtoG.setOnEditorActionListener(editingActionListener2);
        TextView.OnEditorActionListener editingActionListener3 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(RtoB.getText().toString())) {
                        RtoBVal = 0;
                        RtoB.setText(Integer.toString(0));
                    }
                    else {
                        RtoBVal = Integer.parseInt(RtoB.getText().toString());
                        if (RtoBVal > 255) {
                            RtoBVal = 255;
                            RtoB.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[2] = RtoBVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        RtoB.setOnEditorActionListener(editingActionListener3);
        TextView.OnEditorActionListener editingActionListener4 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(GtoR.getText().toString())) {
                        GtoRVal = 0;
                        GtoR.setText(Integer.toString(0));
                    }
                    else {
                        GtoRVal = Integer.parseInt(GtoR.getText().toString());
                        if (GtoRVal > 255) {
                            GtoRVal = 255;
                            GtoR.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[5] = GtoRVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        GtoR.setOnEditorActionListener(editingActionListener4);
        TextView.OnEditorActionListener editingActionListener5 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(GtoG.getText().toString())) {
                        GtoGVal = 0;
                        GtoG.setText(Integer.toString(0));
                    }
                    else {
                        GtoGVal = Integer.parseInt(GtoG.getText().toString());
                        if (GtoGVal > 255) {
                            GtoGVal = 255;
                            GtoG.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[6] = GtoGVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        GtoG.setOnEditorActionListener(editingActionListener5);
        TextView.OnEditorActionListener editingActionListener6 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(GtoB.getText().toString())) {
                        GtoBVal = 0;
                        GtoB.setText(Integer.toString(0));
                    }
                    else {
                        GtoBVal = Integer.parseInt(GtoB.getText().toString());
                        if (GtoBVal > 255) {
                            GtoBVal = 255;
                            GtoB.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[7] = GtoBVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        GtoB.setOnEditorActionListener(editingActionListener6);
        TextView.OnEditorActionListener editingActionListener7 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(BtoR.getText().toString())) {
                        BtoRVal = 0;
                        BtoR.setText(Integer.toString(0));
                    }
                    else {
                        BtoRVal = Integer.parseInt(BtoR.getText().toString());
                        if (BtoRVal > 255) {
                            BtoRVal = 255;
                            BtoR.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[10] = BtoRVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        BtoR.setOnEditorActionListener(editingActionListener7);
        TextView.OnEditorActionListener editingActionListener8 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(BtoG.getText().toString())) {
                        BtoGVal = 0;
                        BtoG.setText(Integer.toString(0));
                    }
                    else {
                        BtoGVal = Integer.parseInt(BtoG.getText().toString());
                        if (BtoGVal > 255) {
                            BtoGVal = 255;
                            BtoG.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[11] = BtoGVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        BtoG.setOnEditorActionListener(editingActionListener8);
        TextView.OnEditorActionListener editingActionListener9 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(BtoB.getText().toString())) {
                        BtoBVal = 0;
                        BtoB.setText(Integer.toString(0));
                    }
                    else {
                        BtoBVal = Integer.parseInt(BtoB.getText().toString());
                        if (BtoBVal > 255) {
                            BtoBVal = 255;
                            BtoB.setText(Integer.toString(255));
                        }
                    }
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    customFilter = Arrays.copyOf(currentFilter, currentFilter.length);
                    customFilter[12] = BtoBVal;
                    updateFilter();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        BtoB.setOnEditorActionListener(editingActionListener9);
        TextView.OnEditorActionListener editingActionListener10 = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(alpha_number.getText().toString())) {
                        alphaVal = 255;
                        alpha_number.setText(Integer.toString(255));
                    }
                    else {
                        int temp = Integer.parseInt(alpha_number.getText().toString());
                        if (temp > 255) {
                            alphaVal = 255;
                            alpha_number.setText(Integer.toString(255));
                        }
                        else {
                            alphaVal = temp;
                        }
                    }
                    alpha_slider.setProgress(alphaVal);
                    mSharedMemory.setAlpha(alpha_slider.getProgress());
                    mSharedMemory.setRed(RtoRVal);
                    mSharedMemory.setGreen(GtoGVal);
                    mSharedMemory.setBlue(BtoBVal);
                    currentFilter[18] = alphaVal;
                    updateMatrix();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        alpha_number.setOnEditorActionListener(editingActionListener10);
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

    public void saveFilter(View view) {
        Intent intent = new Intent(Filters.this, Presets.class);
        startActivity(intent);
        String str = "Saved Matrix\n\n";
        for (int j = 0; j < 3; j++) {
            for(int i= (j * 3);i< (j*3) + 3;i++){
                str = str + "   " + String.valueOf((int)currentFilter[i]);
            }
            str = str + "\n";
        }
        str = str + "A:  " + alphaVal;
//        editor.putString("FLOAT_ARR",str);
//        editor.commit();
        PresetModel presetModel = new PresetModel(str, getDate());
        Presets.presetList.add(presetModel);
        PrefConfig.writeListInPref(getApplicationContext(), Presets.presetList);
        Collections.reverse(Presets.presetList);
        Presets.adapter.setTaskModelList(Presets.presetList);
    }


    private String getDate() {
//        Calendar cal = Calendar.getInstance();
//        Date date = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        return String.valueOf(dateFormat.format(date));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return String.valueOf(sdf3.format(timestamp));
    }
    public void applyFilter(View view) {
        boolean invertedFilt = true;
        for (int i = 0; i < currentFilter.length; i++) {
            if (currentFilter[i] != INVERTED[i]) {
                invertedFilt = false;
                break;
            }
        }
        if (invertedFilt) {
            System.out.println(1);
            imgBefore.setColorFilter(new ColorMatrixColorFilter(currentFilter));
            if (prevFilterExists) {
                previousFilters.add(prevFilter);
            }
            prevFilter = Arrays.copyOf(currentFilter, currentFilter.length);
            prevFilterExists = true;
            if (previousFilters.size() > 10) {
                previousFilters.remove(0);
            }
        } else {
            System.out.println(0);
            float[] filter = new float[currentFilter.length];
            for (int i = 0; i < currentFilter.length; i++) {
                filter[i] = currentFilter[i] / 255;
            }
            imgBefore.setColorFilter(new ColorMatrixColorFilter(filter));
            if (prevFilterExists) {
                previousFilters.add(prevFilter);
            }
            prevFilter = Arrays.copyOf(currentFilter, currentFilter.length);
            prevFilterExists = true;
            if (previousFilters.size() > 10) {
                previousFilters.remove(0);
            }
        }
    }
    public void revertFilter(View view) {
        if (previousFilters.isEmpty()) {
            prevFilterExists = false;
            float[] filter = new float[defaultFilter.length];
            for (int i = 0; i < defaultFilter.length; i++) {
                filter[i] = defaultFilter[i] / 255;
            }
            imgBefore.setColorFilter(new ColorMatrixColorFilter(filter));
            return;
        }
        float[] revertedFilter = previousFilters.lastElement();
        boolean invertedFilt = true;
        for (int i = 0; i < revertedFilter.length; i++) {
            if (revertedFilter[i] != INVERTED[i]) {
                invertedFilt = false;
            }
        }
        if (invertedFilt) {
            imgBefore.setColorFilter(new ColorMatrixColorFilter(INVERTED));
            previousFilters.remove(previousFilters.size() - 1);
        } else {
            float[] filter = new float[revertedFilter.length];
            for (int i = 0; i < revertedFilter.length; i++) {
                filter[i] = revertedFilter[i] / 255;
            }
            imgBefore.setColorFilter(new ColorMatrixColorFilter(filter));
            previousFilters.remove(previousFilters.size() - 1);
        }
    }

    public void onClick(View view) {
        if (def.isChecked()) {
            currentFilter = Arrays.copyOf(defaultFilter, defaultFilter.length);
        } else if (protan.isChecked()) {
            currentFilter = Arrays.copyOf(PROTANOPIA, PROTANOPIA.length);
        } else if (deuteran.isChecked()) {
            currentFilter = Arrays.copyOf(DEUTERANOPIA, DEUTERANOPIA.length);
        } else if (tritan.isChecked()) {
            currentFilter = Arrays.copyOf(TRITANOPIA, TRITANOPIA.length);
        } else if (gray.isChecked()) {
            currentFilter = Arrays.copyOf(GRAYSCALE, GRAYSCALE.length);
        } else if (invert.isChecked()) {
            currentFilter = Arrays.copyOf(INVERTED, INVERTED.length);
            currentFilter[18] = (float) alpha_slider.getProgress() / 255;
            imgAfter.setColorFilter(new ColorMatrixColorFilter(currentFilter));
            updateMatrix();
            return;
        }  else if (custom.isChecked()) {
            currentFilter = Arrays.copyOf(customFilter, customFilter.length);
        } else {
            currentFilter = Arrays.copyOf(defaultFilter, defaultFilter.length);
        }
        currentFilter[18] = (float) alpha_slider.getProgress();
        float[] filter = new float[currentFilter.length];

        for (int i = 0; i < currentFilter.length; i++) {
            filter[i] = currentFilter[i] / 255;
        }
        imgAfter.setColorFilter(new ColorMatrixColorFilter(filter));
        updateMatrix();

//        mSharedMemory.setAlpha(alpha_slider.getProgress());
//        mSharedMemory.setRed(RtoRVal);
//        mSharedMemory.setGreen(GtoGVal);
//        mSharedMemory.setBlue(BtoBVal);
    }
//    public void onClickBottom(View view) {
//        def.setChecked(false);
//        gray.setChecked(false);
//        invert.setChecked(false);
//        custom.setChecked(false);
//
//        if (protan.isChecked()) {
//            currentFilter = Arrays.copyOf(PROTANOPIA, PROTANOPIA.length);
//        } else if (deuteran.isChecked()) {
//            currentFilter = Arrays.copyOf(DEUTERANOPIA, DEUTERANOPIA.length);
//        } else if (tritan.isChecked()) {
//            currentFilter = Arrays.copyOf(TRITANOPIA, TRITANOPIA.length);
//        } else {
//            currentFilter = Arrays.copyOf(defaultFilter, defaultFilter.length);
//        }
//
//        currentFilter[18] = (float) alpha_slider.getProgress();
//        float[] filter = new float[currentFilter.length];
//
//        for (int i = 0; i < currentFilter.length; i++) {
//            filter[i] = currentFilter[i] / 255;
//        }
//        imgAfter.setColorFilter(new ColorMatrixColorFilter(filter));
//        updateMatrix();
//    }
    public void updateMatrix() {
        EditText RtoR = findViewById(R.id.RtoR);
        EditText RtoG = findViewById(R.id.RtoG);
        EditText RtoB = findViewById(R.id.RtoB);
        EditText GtoR = findViewById(R.id.GtoR);
        EditText GtoG = findViewById(R.id.GtoG);
        EditText GtoB = findViewById(R.id.GtoB);
        EditText BtoR = findViewById(R.id.BtoR);
        EditText BtoG = findViewById(R.id.BtoG);
        EditText BtoB = findViewById(R.id.BtoB);
        EditText alpha_number = findViewById(R.id.alpha_number);
        RtoR.setText(Integer.toString((int) currentFilter[0]));
        RtoG.setText(Integer.toString((int) currentFilter[1]));
        RtoB.setText(Integer.toString((int) currentFilter[2]));
        GtoR.setText(Integer.toString((int) currentFilter[5]));
        GtoG.setText(Integer.toString((int) currentFilter[6]));
        GtoB.setText(Integer.toString((int) currentFilter[7]));
        BtoR.setText(Integer.toString((int) currentFilter[10]));
        BtoG.setText(Integer.toString((int) currentFilter[11]));
        BtoB.setText(Integer.toString((int) currentFilter[12]));
        alpha_number.setText(Integer.toString((int) currentFilter[18]));
    }
    public void updateFilter() {
        def.setChecked(false);
        protan.setChecked(false);
        deuteran.setChecked(false);
        tritan.setChecked(false);
        gray.setChecked(false);
        invert.setChecked(false);
        custom.setChecked(true);
        mSharedMemory.setAlpha(alpha_slider.getProgress());
//        mSharedMemory.setRed(red_slider.getProgress());
//        mSharedMemory.setGreen(green_slider.getProgress());
//        mSharedMemory.setBlue(blue_slider.getProgress());

        if (ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE) {
            Intent i = new Intent(Filters.this, ScreenFilterService.class);
            startService(i);
        }
        mToggleButton.setChecked(ScreenFilterService.STATE == ScreenFilterService.STATE_ACTIVE);
        float[] filter = new float[customFilter.length];
        for (int i = 0; i < customFilter.length; i++) {
            filter[i] = customFilter[i] / 255;
        }
        imgAfter.setColorFilter(new ColorMatrixColorFilter(filter));
        currentFilter = Arrays.copyOf(customFilter, customFilter.length);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == GALLERY_REQ_CODE) {

                imgAfter.setImageURI(data.getData());
                imgAfter.setColorFilter(new ColorMatrixColorFilter(currentFilter));
                imgBefore.setImageURI(data.getData());
                imgBefore.setColorFilter(new ColorMatrixColorFilter(defaultFilter));
            }
        }

    }
}