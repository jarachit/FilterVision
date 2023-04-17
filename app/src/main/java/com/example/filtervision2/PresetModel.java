package com.example.filtervision2;

import java.io.Serializable;

public class PresetModel implements Serializable{
    private String matrix;
    private String  preset_number;

    public PresetModel() {
    }

    public PresetModel(String matrixValue, String  presetNumber) {
        this.matrix = matrixValue;
        this.preset_number = presetNumber;
    }

    public String getMatrixValue() {
        return matrix;
    }

    public String getpresetNumber() {
        return preset_number;
    }
}
