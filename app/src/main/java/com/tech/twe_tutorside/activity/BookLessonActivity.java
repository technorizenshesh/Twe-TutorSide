package com.tech.twe_tutorside.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.GenderSpinnerAdapter;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookLessonActivity extends AppCompatActivity {

    LinearLayout ll_open_time;
    LinearLayout ll_slotTime;
    LinearLayout ll_closeTime;
    TextView txt_openTime;
    TextView txt_closeTime;

    int mHour;
    int mMinute;

    int mHour_one;
    int mMinute_one;

    Spinner spinnerSlot;
    private String code[] ={"60 min","120 min","180 min","240 min","300 min"};
    private int flags[]= {R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};

    private ProgressBar progressBar;
    private SessionManager sessionManager;

    String FinalOepnTime ="";
    String FinalCloseTime ="";
    String TimeSlot ="";

    String teach_distance="";

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
    TextView txt_time_Zone;
    String TimeZoneNew ="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_lesson);

        Intent intent=getIntent();
        if(intent !=null)
        {
            about=intent.getStringExtra("about").toString();
            dob=intent.getStringExtra("dob").toString();
            education=intent.getStringExtra("education").toString();
            language=intent.getStringExtra("language").toString();
            Affilations=intent.getStringExtra("Affilations").toString();
            Awards=intent.getStringExtra("Awards").toString();
            about=intent.getStringExtra("about").toString();
            dob=intent.getStringExtra("dob").toString();
            where_to_teach=intent.getStringExtra("where_to_teach").toString();
            teach_distance=intent.getStringExtra("teach_distance").toString();
            location=intent.getStringExtra("location").toString();
            gender=intent.getStringExtra("gender").toString();
            certification=intent.getStringExtra("certification").toString();
        }


        ll_open_time=findViewById(R.id.ll_open_time);
        ll_slotTime=findViewById(R.id.ll_slotTime);
        ll_closeTime=findViewById(R.id.ll_closeTime);
        txt_openTime=findViewById(R.id.txt_openTime);
        txt_closeTime=findViewById(R.id.txt_closeTime);
        spinnerSlot=findViewById(R.id.spinnerSlot);
        txt_time_Zone=findViewById(R.id.txt_time_Zone);
        progressBar=findViewById(R.id.progressBar);

        TimeZone tz = TimeZone.getDefault();
        String TimeZone1= tz.getDisplayName(false, TimeZone.SHORT);

        TimeZoneNew = "( "+TimeZone1+" ) "+tz.getDisplayName();

        txt_time_Zone.setText(TimeZoneNew);

        sessionManager = new SessionManager(BookLessonActivity.this);


        GenderSpinnerAdapter customAdapter=new GenderSpinnerAdapter(BookLessonActivity.this,flags,code);
        spinnerSlot.setAdapter(customAdapter);

        ll_open_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookLessonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;
                                txt_openTime.setText(hourOfDay + ":" + minute);

                                FinalOepnTime = hourOfDay+":" + minute;

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        });

        txt_openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookLessonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;
                                txt_openTime.setText(hourOfDay + ":" + minute);

                                FinalOepnTime = hourOfDay+":" + minute;

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        });

        ll_slotTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_closeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour_one = c.get(Calendar.HOUR_OF_DAY);
                mMinute_one = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookLessonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour_one = hourOfDay;
                                mMinute_one = minute;
                                txt_closeTime.setText(hourOfDay + ":" + minute+" PM");

                                FinalCloseTime = hourOfDay+":" + minute;
                            }
                        }, mHour_one, mMinute_one, true);
                timePickerDialog.show();

            }
        });

        txt_closeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour_one = c.get(Calendar.HOUR_OF_DAY);
                mMinute_one = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookLessonActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour_one = hourOfDay;
                                mMinute_one = minute;
                                txt_closeTime.setText(hourOfDay + ":" + minute+" PM");

                                FinalCloseTime = hourOfDay+":" + minute;

                            }
                        }, mHour_one, mMinute_one, true);
                timePickerDialog.show();

            }
        });

        spinnerSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                TimeSlot = code[position];

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

    }


    public void bookLessonInit(View view) {

      if(FinalOepnTime.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Open Time", Toast.LENGTH_SHORT).show();

        }else if(FinalCloseTime.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Close Time", Toast.LENGTH_SHORT).show();

        }else
        {
            if (sessionManager.isNetworkAvailable()) {

                progressBar.setVisibility(View.VISIBLE);

                addTimeApi();

            }else {

                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();

            }
        }
       // startActivity(new Intent(BookLessonActivity.this,CategorySelectActivity.class));
    }

    private void addTimeApi() {

        String UserId  = Preference.get(BookLessonActivity.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .dd_time(UserId,FinalOepnTime,FinalCloseTime,TimeSlot);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                        Intent intent=new Intent(BookLessonActivity.this,TimeSelectedWeekView.class);
                        intent.putExtra("about",about);
                        intent.putExtra("dob",dob);
                        intent.putExtra("education",education);
                        intent.putExtra("language",language);
                        intent.putExtra("Affilations",Affilations);
                        intent.putExtra("Awards",Awards);
                        intent.putExtra("where_to_teach",where_to_teach);
                        intent.putExtra("teach_distance",teach_distance);
                        intent.putExtra("teach_distance",teach_distance);
                        intent.putExtra("location",location);
                        intent.putExtra("gender",gender);
                        intent.putExtra("certification",certification);
                        intent.putExtra("TimeZone",TimeZoneNew);
                        startActivity(intent);

                       // startActivity(new Intent(BookLessonActivity.this,TimeSelectedWeekView.class));

                    } else {

                        Toast.makeText(BookLessonActivity.this, message, Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BookLessonActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}