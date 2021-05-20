package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;

public class



BuildingProfiActivity extends AppCompatActivity {

    TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_profi);

        txt_name =findViewById(R.id.txt_name);

       String User_Name =  Preference.get(BuildingProfiActivity.this, Preference.KEY_username);

        txt_name.setText("Welcome to TWE,"+User_Name+"!");
    }

    public void backFromBuildingProfile(View view) {
        onBackPressed();
        finish();
    }

    public void createProfi(View view) {
        startActivity(new Intent(BuildingProfiActivity.this,FillDetailsActivity.class));
    }
}