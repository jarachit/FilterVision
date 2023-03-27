package com.example.filtervision2;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class SharedMemory {
    private SharedPreferences mSharedPreferences;

    public SharedMemory(Context context) {
        mSharedPreferences = context.getSharedPreferences("SCREEN_FILTER_PREF", Context.MODE_PRIVATE);
    }

    private int getValue(String prop, int def) {
        return mSharedPreferences.getInt(prop, def);
    }

    public int getAlpha() {
        return getValue("alpha", 0x33);
    }

    public int getRed() {
        return getValue("red", 0x00);
    }
    public int getGreen() {
        return getValue("green", 0x00);
    }
    public int getBlue() {
        return getValue("blue", 0x00);
    }

    private void setValue(String value, int v) {
        mSharedPreferences.edit().putInt(value, v).apply();
    }

    public void setAlpha(int val) {
        setValue("alpha", val);
    }
    public void setRed(int val) {
        setValue("red", val);
    }
    public void setGreen(int val) {
        setValue("green", val);
    }
    public void setBlue(int val) {
        setValue("blue", val);
    }

    public int getColor() {
        return Color.rgb(getRed(), getGreen(), getBlue());
//        return Color.argb(getAlpha(), getRed(), getGreen(), getBlue());
    }
}
