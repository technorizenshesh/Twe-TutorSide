package com.tech.twe_tutorside.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.fragments.CategoryFragment1;
import com.tech.twe_tutorside.listner.FragmentListener;


public class CategorySelectActivity extends AppCompatActivity implements FragmentListener {

    Context mContext;

    private String teach_distance="";
    private String TimeZone="";
    private String where_to_teach="";
    private String about ="";
    private String dob ="";
    private String education ="";
    private String language ="";
    private String Affilations ="";
    private String Awards ="";

    private String monday="";
    private  String tuesday="";
    private  String wednesday="";
    private  String thursday="";
    private  String friday="";
    private String saturday="";
    private String sunday="";
    private String location ="";
    private String gender ="";
    private String certification ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        mContext=CategorySelectActivity.this;


        Intent intent=getIntent();

        if(intent !=null)
        {
            about=intent.getStringExtra("about").toString();
            dob=intent.getStringExtra("dob").toString();
            education=intent.getStringExtra("education").toString();
            language=intent.getStringExtra("language").toString();
            Affilations=intent.getStringExtra("Affilations").toString();
            Awards=intent.getStringExtra("Awards").toString();
            where_to_teach=intent.getStringExtra("where_to_teach").toString();
            teach_distance=intent.getStringExtra("teach_distance").toString();
            location=intent.getStringExtra("location").toString();
            gender=intent.getStringExtra("gender").toString();
            certification=intent.getStringExtra("certification").toString();
            TimeZone=intent.getStringExtra("TimeZone").toString();
            monday=intent.getStringExtra("mondayTime").toString();
            tuesday=intent.getStringExtra("tuesday").toString();
            wednesday=intent.getStringExtra("wednesday").toString();
            thursday=intent.getStringExtra("thursday").toString();
            friday=intent.getStringExtra("friday").toString();
            saturday=intent.getStringExtra("saturday").toString();
            sunday=intent.getStringExtra("sunday").toString();

        }



        loadFragment(new CategoryFragment1(this));


    }

    private void loadFragment(Fragment fragment) {
        Bundle args = new Bundle();
        args.putString("about", about);
        args.putString("dob", dob);
        args.putString("education", education);
        args.putString("language", language);
        args.putString("Affilations", Affilations);
        args.putString("Awards", Awards);
        args.putString("where_to_teach", where_to_teach);
        args.putString("teach_distance", teach_distance);
        args.putString("location", location);
        args.putString("gender", gender);
        args.putString("certification", certification);
        args.putString("TimeZone", TimeZone);
        args.putString("mondayTime", monday);
        args.putString("tuesday", tuesday);
        args.putString("wednesday", wednesday);
        args.putString("thursday", thursday);
        args.putString("friday", friday);
        args.putString("saturday", saturday);
        args.putString("sunday", sunday );
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutCategory, fragment).commit();
    }

    @Override
    public void click(Fragment fragment) {
        loadFragment(fragment);
    }

}
