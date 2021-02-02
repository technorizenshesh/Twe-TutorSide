package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.twe_tutorside.R;

public class YourLessonActivity extends AppCompatActivity   {

    Context mContext;

    private LinearLayout LL_student_home;
    private LinearLayout LL_tutor_home;
    private LinearLayout LL_online;

    private TextView txt_student_home;
    private TextView txt_tutor_home;
    private TextView txt_online;

    String where_to_teach="Student_Home";

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
        setContentView(R.layout.activity_your_lesson);

      Intent intent=getIntent();

        if(intent !=null)
        {
             about=intent.getStringExtra("about").toString();
            location=intent.getStringExtra("location").toString();
            gender=intent.getStringExtra("gender").toString();
            certification=intent.getStringExtra("certification").toString();
             dob=intent.getStringExtra("dob").toString();
             education=intent.getStringExtra("education").toString();
             language=intent.getStringExtra("language").toString();
             Affilations=intent.getStringExtra("Affilations").toString();
             Awards=intent.getStringExtra("Awards").toString();
        }

        LL_student_home=findViewById(R.id.LL_student_home);
        LL_tutor_home=findViewById(R.id.LL_tutor_home);
        LL_online=findViewById(R.id.LL_online);

        txt_student_home=findViewById(R.id.txt_student_home);
        txt_tutor_home=findViewById(R.id.txt_tutor_home);
        txt_online=findViewById(R.id.txt_online);

        LL_student_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                where_to_teach ="Student_Home";

                LL_student_home.setBackgroundResource(R.drawable.edit_bgbutton);
                LL_tutor_home.setBackgroundResource(R.drawable.edit_bgbutton_white);
                LL_online.setBackgroundResource(R.drawable.edit_bgbutton_white);

                txt_student_home.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.colorWhite));
                txt_tutor_home.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.color_txtBlue));
                txt_online.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.color_txtBlue));


            }
        });

        LL_tutor_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                where_to_teach ="Tutor_Home";

                LL_student_home.setBackgroundResource(R.drawable.edit_bgbutton_white);
                LL_tutor_home.setBackgroundResource(R.drawable.edit_bgbutton);
                LL_online.setBackgroundResource(R.drawable.edit_bgbutton_white);

                txt_student_home.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.color_txtBlue));
                txt_tutor_home.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.colorWhite));
                txt_online.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.color_txtBlue));

            }
        });

        LL_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                where_to_teach ="Online";

                LL_student_home.setBackgroundResource(R.drawable.edit_bgbutton_white);
                LL_tutor_home.setBackgroundResource(R.drawable.edit_bgbutton_white);
                LL_online.setBackgroundResource(R.drawable.edit_bgbutton);

                txt_student_home.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.color_txtBlue));
                txt_tutor_home.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.color_txtBlue));
                txt_online.setTextColor(ContextCompat.getColor(YourLessonActivity.this, R.color.colorWhite));


            }
        });

    }


    public void backWantTutor(View view) {
        onBackPressed();
    }

    public void ContinueInit(View view) {
       if(where_to_teach.equalsIgnoreCase(""))
        {
            Toast.makeText(mContext, "Please Select where to teach", Toast.LENGTH_SHORT).show();

        }else
        {
            Intent intent=new Intent(this,TeachingDistanceActivity.class);
            intent.putExtra("about",about);
            intent.putExtra("dob",dob);
            intent.putExtra("education",education);
            intent.putExtra("language",language);
            intent.putExtra("Affilations",Affilations);
            intent.putExtra("Awards",Awards);
            intent.putExtra("where_to_teach",where_to_teach);
            intent.putExtra("location",location);
            intent.putExtra("gender",gender);
            intent.putExtra("certification",certification);
            startActivity(intent);
        }
      //  startActivity(new Intent(YourLessonActivity.this,TeachingDistanceActivity.class));

    }
}