package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.graphics.PorterDuff;

import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {
//    private Button red = findViewById(R.id.red);
//    private Button default_ = findViewById(R.id.default_);
//    private ImageView mountains = findViewById(R.id.mountains);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void onClick(View view) { // called from activity_main.xml
//        switch(view.getId()) {
//            case R.id.red:
//                break;
//            case R.id.default_:
//                break;
//            default:
//                break;
//        }
//    }

//    public void turnRed() {
//        ImageView mountains = findViewById(R.id.mountains);
//        mountains.getDrawable().setColorFilter(0xffff7276, PorterDuff.Mode.MULTIPLY);
//    }
//
//    public void reset() {
//        ImageView mountains = findViewById(R.id.mountains);
//        mountains.clearColorFilter();
//    }

    public void selectDefault(View view) {
        SwitchCompat red = findViewById(R.id.red_switch);
        SwitchCompat cust1 = findViewById(R.id.custom_switch1);
        SwitchCompat cust2 = findViewById(R.id.custom_switch2);
        red.setChecked(false);
        cust1.setChecked(false);
        cust2.setChecked(false);
    }

    public void selectRed(View view) {
        SwitchCompat def = findViewById(R.id.default_switch);
        SwitchCompat cust1 = findViewById(R.id.custom_switch1);
        SwitchCompat cust2 = findViewById(R.id.custom_switch2);
        def.setChecked(false);
        cust1.setChecked(false);
        cust2.setChecked(false);
    }
    public void selectCustom1(View view) {
        SwitchCompat red = findViewById(R.id.red_switch);
        SwitchCompat def = findViewById(R.id.default_switch);
        SwitchCompat cust2 = findViewById(R.id.custom_switch2);
        red.setChecked(false);
        def.setChecked(false);
        cust2.setChecked(false);
    }

    public void selectCustom2(View view) {
        SwitchCompat def = findViewById(R.id.default_switch);
        SwitchCompat red = findViewById(R.id.red_switch);
        SwitchCompat cust1 = findViewById(R.id.custom_switch1);
        def.setChecked(false);
        cust1.setChecked(false);
        red.setChecked(false);
    }

    public void applyFilter(View view) {
        SwitchCompat red = findViewById(R.id.red_switch);
        SwitchCompat def = findViewById(R.id.default_switch);
        SwitchCompat cust1 = findViewById(R.id.custom_switch1);
        SwitchCompat cust2 = findViewById(R.id.custom_switch2);
        ImageView mountains = findViewById(R.id.image);
        if (red.isChecked()) {
            mountains.getDrawable().setColorFilter(0xffff7276, PorterDuff.Mode.MULTIPLY);
        } else if (def.isChecked()) {
            mountains.getDrawable().clearColorFilter();
        } else if (cust1.isChecked()) {
            mountains.getDrawable().setColorFilter(0xFF3700B3, PorterDuff.Mode.MULTIPLY);
        } else if (cust2.isChecked()) {
            mountains.getDrawable().setColorFilter(0xFF018786, PorterDuff.Mode.MULTIPLY);
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