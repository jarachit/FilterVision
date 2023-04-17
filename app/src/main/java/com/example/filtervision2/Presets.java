package com.example.filtervision2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Presets extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static PresetAdapter adapter;
//    private Button saveFilter;
    public static List<PresetModel> presetList;

    //    ArrayList<String> presets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presets);

        recyclerView = findViewById(R.id.recyclerview);
//        saveFilter = findViewById(R.id.save_filter);

        presetList = PrefConfig.readListFromPref(this);

        if (presetList == null)
            presetList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new PresetAdapter(getApplicationContext(), presetList);
        recyclerView.setAdapter(adapter);
    }
}
