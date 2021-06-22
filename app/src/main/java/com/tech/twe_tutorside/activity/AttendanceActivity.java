package com.tech.twe_tutorside.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.fragments.DailyAttendanceFragment;
import com.tech.twe_tutorside.fragments.MonthlyAttendanceFragment;
import com.tech.twe_tutorside.fragments.WeeklyAttendanceFragment;
import com.tech.twe_tutorside.listner.FragmentListener;


public class AttendanceActivity extends AppCompatActivity implements FragmentListener, View.OnClickListener {


    TextView daily_TabtxtId, weekly_TabtxtId, monthly_TabtxtId;

    FrameLayout frameLayoutAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        frameLayoutAttendance=findViewById(R.id.frameLayout_attendance);


        loadFragment(new DailyAttendanceFragment(this));


        daily_TabtxtId = findViewById(R.id.daily_TabtxtId);
        weekly_TabtxtId = findViewById(R.id.weekly_txtId);
        monthly_TabtxtId = findViewById(R.id.monthly_txtId);

        daily_TabtxtId.setOnClickListener(this);
        weekly_TabtxtId.setOnClickListener(this);
        monthly_TabtxtId.setOnClickListener(this);

    }

    public void backAttendanceInit(View view) {
        onBackPressed();
        finish();
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.daily_TabtxtId:
                daily_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_yellowbg));
                weekly_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_gray));
                monthly_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_gray));
                daily_TabtxtId.setTextColor(Color.parseColor("#05346d"));
                weekly_TabtxtId.setTextColor(Color.parseColor("#000000"));
                monthly_TabtxtId.setTextColor(Color.parseColor("#000000"));
                loadFragment(new DailyAttendanceFragment(this));

                break;


            case R.id.weekly_txtId:

                daily_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_gray));
                weekly_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_yellowbg));
                monthly_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_gray));
                daily_TabtxtId.setTextColor(Color.parseColor("#000000"));
                weekly_TabtxtId.setTextColor(Color.parseColor("#05346d"));
                monthly_TabtxtId.setTextColor(Color.parseColor("#000000"));

                loadFragment(new WeeklyAttendanceFragment(this));

                break;


            case R.id.monthly_txtId:

                daily_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_gray));
                weekly_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_gray));
                monthly_TabtxtId.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_yellowbg));
                daily_TabtxtId.setTextColor(Color.parseColor("#000000"));
                weekly_TabtxtId.setTextColor(Color.parseColor("#000000"));
                monthly_TabtxtId.setTextColor(Color.parseColor("#05346d"));

                loadFragment(new MonthlyAttendanceFragment(this));

                break;
        }

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_attendance, fragment).commit();
    }

    @Override
    public void click(Fragment fragment) {
        loadFragment(fragment);
    }
}