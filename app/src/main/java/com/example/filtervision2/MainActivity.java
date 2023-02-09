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
        red.setChecked(false);
    }

    public void selectRed(View view) {
        SwitchCompat def = findViewById(R.id.default_switch);
        def.setChecked(false);
    }

    public void applyFilter(View view) {
        SwitchCompat red = findViewById(R.id.red_switch);
        SwitchCompat def = findViewById(R.id.default_switch);
        ImageView mountains = findViewById(R.id.mountains);
        if (red.isChecked()) {
            mountains.getDrawable().setColorFilter(0xffff7276, PorterDuff.Mode.MULTIPLY);
        } else if (def.isChecked()) {
            mountains.getDrawable().clearColorFilter();
        }
    }
}