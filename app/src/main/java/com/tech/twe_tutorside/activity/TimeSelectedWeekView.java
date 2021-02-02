package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.AvailableSlot;
import com.tech.twe_tutorside.model.SaloonSpecialistModel;
import com.tech.twe_tutorside.model.TimeSlotModel;
import com.tech.twe_tutorside.model.getAddress;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeSelectedWeekView extends AppCompatActivity {

    //Recycleview week View
    private RecyclerView recycler_available_slot_moday;
    private RecyclerView recycler_available_slot_tuesday;
    private RecyclerView recycler_available_slot_wednesDay;
    private RecyclerView recycler_available_slot_thursday;
    private RecyclerView recycler_available_slot_friday;
    private RecyclerView recycler_available_slot_saturday;
    private RecyclerView recycler_available_slot_sunday;

    //Array List week View
    private ArrayList<String> modelListTime_monday = new ArrayList<>();
    private ArrayList<String> modelListTime_tuesday = new ArrayList<>();
    private ArrayList<String> modelListTime_wednesDay= new ArrayList<>();
    private ArrayList<String> modelListTime_thursday = new ArrayList<>();
    private ArrayList<String> modelListTime_friday = new ArrayList<>();
    private ArrayList<String> modelListTime_saturday = new ArrayList<>();
    private ArrayList<String> modelListTime_sunday = new ArrayList<>();

    //Array List week View
    private ArrayList<SaloonSpecialistModel> modelListTime_monday_new = new ArrayList<>();
    private ArrayList<SaloonSpecialistModel> modelListTime_tuesday_new = new ArrayList<>();
    private ArrayList<SaloonSpecialistModel> modelListTime_wednesDay_new= new ArrayList<>();
    private ArrayList<SaloonSpecialistModel> modelListTime_thursday_new = new ArrayList<>();
    private ArrayList<SaloonSpecialistModel> modelListTime_friday_new = new ArrayList<>();
    private ArrayList<SaloonSpecialistModel> modelListTime_saturday_new = new ArrayList<>();
    private ArrayList<SaloonSpecialistModel> modelListTime_sunday_new = new ArrayList<>();

    // Adapter  week View
    private AvailableSlot mAdapter_monday;
    private AvailableSlot mAdapter_tuesday;
    private AvailableSlot mAdapter_wednesDay;
    private AvailableSlot mAdapter_thursday;
    private AvailableSlot mAdapter_friday;
    private AvailableSlot mAdapter_saturday;
    private AvailableSlot mAdapter_sunday;


    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private LinearLayout LL_next;


    //Array List week View selected
    private ArrayList<String> monday = new ArrayList<>();
    private ArrayList<String> tuesday = new ArrayList<>();
    private ArrayList<String> wednesday = new ArrayList<>();
    private ArrayList<String> thursday = new ArrayList<>();
    private ArrayList<String> friday = new ArrayList<>();
    private ArrayList<String> saturday = new ArrayList<>();
    private ArrayList<String> sunday = new ArrayList<>();

    String teach_distance="";
    String TimeZone="";
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
        setContentView(R.layout.activity_time_selected_week_view);

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
            TimeZone=intent.getStringExtra("TimeZone").toString();
        }


        recycler_available_slot_moday=findViewById(R.id.recycler_available_slot_moday);
        recycler_available_slot_tuesday=findViewById(R.id.recycler_available_slot_tuesday);
        recycler_available_slot_wednesDay=findViewById(R.id.recycler_available_slot_wednesDay);
        recycler_available_slot_thursday=findViewById(R.id.recycler_available_slot_thursday);
        recycler_available_slot_friday=findViewById(R.id.recycler_available_slot_friday);
        recycler_available_slot_saturday=findViewById(R.id.recycler_available_slot_saturday);
        recycler_available_slot_sunday=findViewById(R.id.recycler_available_slot_sunday);
        LL_next=findViewById(R.id.LL_next);

        progressBar=findViewById(R.id.progressBar);
        sessionManager = new SessionManager(TimeSelectedWeekView.this);

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getTimeSlotApi();

        }else {
            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        LL_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<modelListTime_monday_new.size();i++)
                {
                    if(modelListTime_monday_new.get(i).isSelected())
                    {
                        monday.add(modelListTime_monday_new.get(i).getName());
                    }
                }

                for(int i=0;i<modelListTime_tuesday_new.size();i++)
                {
                    if(modelListTime_tuesday_new.get(i).isSelected())
                    {
                        thursday.add(modelListTime_tuesday_new.get(i).getName());
                    }
                }

                for(int i=0;i<modelListTime_wednesDay_new.size();i++)
                {
                    if(modelListTime_wednesDay_new.get(i).isSelected())
                    {
                        wednesday.add(modelListTime_wednesDay_new.get(i).getName());
                    }
                }

                for(int i=0;i<modelListTime_thursday_new.size();i++)
                {
                    if(modelListTime_thursday_new.get(i).isSelected())
                    {
                        thursday.add(modelListTime_thursday_new.get(i).getName());
                    }
                }

                for(int i=0;i<modelListTime_friday_new.size();i++)
                {
                    if(modelListTime_friday_new.get(i).isSelected())
                    {
                        friday.add(modelListTime_friday_new.get(i).getName());
                    }
                }

                for(int i=0;i<modelListTime_saturday_new.size();i++)
                {
                    if(modelListTime_saturday_new.get(i).isSelected())
                    {
                        saturday.add(modelListTime_saturday_new.get(i).getName());
                    }
                }

                for(int i=0;i<modelListTime_sunday_new.size();i++)
                {
                    if(modelListTime_sunday_new.get(i).isSelected())
                    {
                        sunday.add(modelListTime_sunday_new.get(i).getName());
                    }
                }

                Log.d("mondayTime :",""+monday);
                Log.d("tuesday :",""+tuesday);
                Log.d("wednesday :",""+wednesday);
                Log.d("thursday :",""+thursday);
                Log.d("friday :",""+friday);
                Log.d("saturday :",""+saturday);
                Log.d("sunday :",""+sunday);

                Intent intent=new Intent(TimeSelectedWeekView.this,CategorySelectActivity.class);
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
                intent.putExtra("TimeZone",TimeZone);
                intent.putExtra("mondayTime",""+monday);
                intent.putExtra("tuesday",""+tuesday);
                intent.putExtra("wednesday",""+wednesday);
                intent.putExtra("thursday",""+thursday);
                intent.putExtra("friday",""+friday);
                intent.putExtra("saturday",""+saturday);
                intent.putExtra("sunday",""+sunday);
                startActivity(intent);

               //startActivity(new Intent(TimeSelectedWeekView.this,CategorySelectActivity.class));

            }
        });


    }

    /*private void setAdapter(ArrayList<String> modelListTime ,AvailableSlot mAdapter,RecyclerView recycler_available_slot) {

        mAdapter = new AvailableSlot(TimeSelectedWeekView.this,modelListTime);
        recycler_available_slot.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TimeSelectedWeekView.this);
        recycler_available_slot.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_available_slot.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new AvailableSlot.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String model) {

              //  Toast.makeText(TimeSelectedWeekView.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    */
    private void setAdapter(ArrayList<SaloonSpecialistModel> modelListTime ,AvailableSlot mAdapter,RecyclerView recycler_available_slot) {

        mAdapter = new AvailableSlot(TimeSelectedWeekView.this,modelListTime);
        recycler_available_slot.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TimeSelectedWeekView.this);
        recycler_available_slot.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_available_slot.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new AvailableSlot.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, SaloonSpecialistModel model) {

              //  Toast.makeText(TimeSelectedWeekView.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTimeSlotApi() {

        String UserId  = Preference.get(TimeSelectedWeekView.this,Preference.KEY_USER_ID);

        Call<TimeSlotModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_time_slot(UserId);

        call.enqueue(new Callback<TimeSlotModel>() {
            @Override
            public void onResponse(Call<TimeSlotModel> call, Response<TimeSlotModel> response) {

                try {

                    TimeSlotModel myTimeSlot=response.body();

                    String status =myTimeSlot.getStatus();
                    String message =myTimeSlot.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                        if(myTimeSlot.getResult() != null)
                        {
                            // Monday Time Set
                            modelListTime_monday = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_monday.size();i++)
                            {
                                modelListTime_monday_new.add(new SaloonSpecialistModel(modelListTime_monday.get(i)));
                            }
                            setAdapter(modelListTime_monday_new,mAdapter_monday,recycler_available_slot_moday);

                            // tuesDay Time Set
                            modelListTime_tuesday = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_tuesday.size();i++)
                            {
                                modelListTime_tuesday_new.add(new SaloonSpecialistModel(modelListTime_tuesday.get(i)));
                            }
                            setAdapter(modelListTime_tuesday_new,mAdapter_tuesday,recycler_available_slot_tuesday);

                            // wednesday Time Set
                            modelListTime_wednesDay = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_wednesDay.size();i++)
                            {
                                modelListTime_wednesDay_new.add(new SaloonSpecialistModel(modelListTime_wednesDay.get(i)));
                            }
                            setAdapter(modelListTime_wednesDay_new,mAdapter_wednesDay,recycler_available_slot_wednesDay);

                            // thursday Time Set
                            modelListTime_thursday = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_thursday.size();i++)
                            {
                                modelListTime_thursday_new.add(new SaloonSpecialistModel(modelListTime_thursday.get(i)));
                            }
                            setAdapter(modelListTime_thursday_new,mAdapter_thursday,recycler_available_slot_thursday);

                            // Friday Time Set
                            modelListTime_friday = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_friday.size();i++)
                            {
                                modelListTime_friday_new.add(new SaloonSpecialistModel(modelListTime_friday.get(i)));
                            }
                            setAdapter(modelListTime_friday_new,mAdapter_friday,recycler_available_slot_friday);

                            // satueday Time Set
                            modelListTime_saturday = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_saturday.size();i++)
                            {
                                modelListTime_saturday_new.add(new SaloonSpecialistModel(modelListTime_saturday.get(i)));
                            }
                            setAdapter(modelListTime_saturday_new,mAdapter_saturday,recycler_available_slot_saturday);


                            // sunday Time Set
                            modelListTime_sunday = (ArrayList<String>) myTimeSlot.getResult();
                            for(int i= 0;i<modelListTime_sunday.size();i++)
                            {
                                modelListTime_sunday_new.add(new SaloonSpecialistModel(modelListTime_sunday.get(i)));
                            }
                            setAdapter(modelListTime_sunday_new,mAdapter_sunday,recycler_available_slot_sunday);
                        }


                    } else {

                        Toast.makeText(TimeSelectedWeekView.this, message, Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<TimeSlotModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TimeSelectedWeekView.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}