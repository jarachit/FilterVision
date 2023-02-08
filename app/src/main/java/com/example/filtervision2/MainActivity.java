package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button red = findViewById(R.id.red);
    Button default_ = findViewById(R.id.default_);
    ImageView mountains = findViewById(R.id.mountains);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) { // called from activity_main.xml
        switch(view.getId()) {
            case R.id.red:
                break;
            case R.id.default_:
                break;
            default:
                break;
        }
    }

    public void turnRed() {
        mountains.getDrawable().setColorFilter(0xffff7276, PorterDuff.Mode.MULTIPLY);
    }

    public void reset() {
        mountains.clearColorFilter();
    }
}