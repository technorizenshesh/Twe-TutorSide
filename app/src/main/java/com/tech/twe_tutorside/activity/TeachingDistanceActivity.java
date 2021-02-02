package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.twe_tutorside.R;

import vn.nms.dynamic_seekbar.DynamicSeekBarView;

public class TeachingDistanceActivity extends AppCompatActivity  implements SeekBar.OnSeekBarChangeListener, View.OnClickListener{

    DynamicSeekBarView dynamicSeekBarView;

    LinearLayout teaching_verifyId;
    TextView teaching_txtverifyId;

    String teach_distance="50Km";

    String where_to_teach="";
    String about ="";
    String dob ="";
    String education ="";
    String language ="";
    String Affilations ="";
    String Awards ="";
    String location ="";
    String gender ="";
    String certification ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_distance);

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
            location=intent.getStringExtra("location").toString();
            gender=intent.getStringExtra("gender").toString();
            certification=intent.getStringExtra("certification").toString();
        }


        dynamicSeekBarView = (DynamicSeekBarView)findViewById(R.id.dynamicSeekbar);
        dynamicSeekBarView.setSeekBarChangeListener(this);
        dynamicSeekBarView.setMax(200);
        dynamicSeekBarView.setProgress(150);


        teaching_verifyId =findViewById(R.id.teaching_verifyId);
        teaching_verifyId.setOnClickListener(this);
        teaching_txtverifyId = findViewById(R.id.teaching_txtverifyId);
        teaching_txtverifyId.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.teaching_verifyId:
             if(teach_distance.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Please Enter teach distance", Toast.LENGTH_SHORT).show();

                }else
                {
                    Intent intent=new Intent(this,BookLessonActivity.class);
                    intent.putExtra("about",about);
                    intent.putExtra("dob",dob);
                    intent.putExtra("education",education);
                    intent.putExtra("language",language);
                    intent.putExtra("Affilations",Affilations);
                    intent.putExtra("Awards",Awards);
                    intent.putExtra("where_to_teach",where_to_teach);
                    intent.putExtra("teach_distance",teach_distance);
                    intent.putExtra("location",location);
                    intent.putExtra("gender",gender);
                    intent.putExtra("certification",certification);
                    startActivity(intent);

                }
               // startActivity(new Intent(TeachingDistanceActivity.this, BookLessonActivity.class));
                break;

            case R.id.teaching_txtverifyId:

                //    listener.click(new Lesson6Fragment(listener));
                startActivity(new Intent(TeachingDistanceActivity.this, BookLessonActivity.class));
                finish();
                break;


        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        dynamicSeekBarView.setInfoText(""+i + " km",i);
        teach_distance = i+" KM";

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void ivBackDistance(View view) {
        onBackPressed();
        finish();
    }
}