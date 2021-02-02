package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.fragments.HomeFragment;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.GetCalculaterDataModel;
import com.tech.twe_tutorside.model.GetCalculaterModel;
import com.tech.twe_tutorside.model.SaloonSpecialistModel;
import com.tech.twe_tutorside.model.TimeSlotModel;
import com.tech.twe_tutorside.utils.RetrofitClients;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeeCalculatorActivity extends AppCompatActivity{

    private EditText edt_perHr;
    private EditText edt_Complte_course_hor;

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
    private String subject="";
    private String location ="";
    private String gender ="";
    private String certification ="";
    String per_hour_payment="";
    String full_course_time="";

    TextView txt1_homeTab_individual;
    TextView txt3_homeTab_grp;


    EditText edt_perHr_indivisual;
    EditText edt_perHr_grp;
    EditText edt_Complte_course_hor_indivisual;
    EditText edt_Complte_course_hor_grp;
    LinearLayout LL_indivisual;
    LinearLayout lesson7Id_indivisual;
    LinearLayout LL_Group;

    TextView txt_hr_services_charge;
    TextView txt_hr_dicount_student;
    TextView txt_hr_teachse_earning;
    TextView txt_hr_student_prices;

    TextView txt_hr_services_charge_grp;
    TextView txt_hr_dicount_student_grp;
    TextView txt_hr_teachse_earning_grp;
    TextView txt_hr_student_prices_grp;


    TextView txt_weekly_services_charge;
    TextView txt_weekly_dicount_student;
    TextView txt_weekly_teacharse_earingn;
    TextView txt_weekly_student_price;


    TextView txt_weekly_services_charge_grp;
    TextView txt_weekly_dicount_student_grp;
    TextView txt_weekly_teacharse_earingn_grp;
    TextView txt_weekly_student_price_grp;

    TextView txt_monthly_services_charge;
    TextView txt_monthly_discount_student;
    TextView txt_monthly_teacharce_earning;
    TextView txt_monthly_student_price;

    TextView txt_monthly_services_charge_grp;
    TextView txt_monthly_discount_studen_grp;
    TextView txt_monthly_teacharce_earning_grp;
    TextView txt_monthly_student_price_grp;


    TextView txt_full_services_charge;
    TextView txt_full_discount_student;
    TextView txt_full_teacharce_earning;
    TextView txt_full_student_price;

    TextView txt_full_services_charge_grp;
    TextView txt_full_discount_student_grp;
    TextView txt_full_teacharce_earning_grp;
    TextView txt_full_student_price_grp;

    ProgressBar progressBar;

     String SignIndivisual_Check ="0";

     LinearLayout lesson7Id_grp;
     LinearLayout lesson7Id_final;

    String isIndivisual ="";
    String isGroup ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_calculator);

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
            subject=intent.getStringExtra("subject").toString();
        }


         findView();

        txt1_homeTab_individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIndivisual_Check ="0";

                LL_indivisual.setVisibility(View.VISIBLE);
                LL_Group.setVisibility(View.GONE);

                txt1_homeTab_individual.setBackgroundResource(R.drawable.color_pink);
                txt3_homeTab_grp.setBackgroundResource(R.drawable.color_gray);

                txt1_homeTab_individual.setTextColor(ContextCompat.getColor(FeeCalculatorActivity.this, R.color.colorWhite));
                txt3_homeTab_grp.setTextColor(ContextCompat.getColor(FeeCalculatorActivity.this, R.color.colorBlack));

            }
        });
        txt3_homeTab_grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIndivisual_Check ="1";

                LL_indivisual.setVisibility(View.GONE);
                LL_Group.setVisibility(View.VISIBLE);

                txt1_homeTab_individual.setBackgroundResource(R.drawable.color_gray);
                txt3_homeTab_grp.setBackgroundResource(R.drawable.color_pink);

                txt1_homeTab_individual.setTextColor(ContextCompat.getColor(FeeCalculatorActivity.this, R.color.colorBlack));
                txt3_homeTab_grp.setTextColor(ContextCompat.getColor(FeeCalculatorActivity.this, R.color.colorWhite));

            }
        });

        lesson7Id_indivisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Price_indivisual =edt_perHr_indivisual.getText().toString();
                String total_duration_time =edt_Complte_course_hor_indivisual.getText().toString();

                if(Price_indivisual.equalsIgnoreCase(""))
                {
                    Toast.makeText(FeeCalculatorActivity.this, "Please Enter sigle Student perhour price ", Toast.LENGTH_SHORT).show();

                }else  if(total_duration_time.equalsIgnoreCase(""))
                {

                    Toast.makeText(FeeCalculatorActivity.this, "Please Enter sigle Student Duration Time ", Toast.LENGTH_SHORT).show();

                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    get_calculation(Price_indivisual,total_duration_time);
                }
            }
        });

        lesson7Id_grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Price_grp =edt_perHr_grp.getText().toString();
                String total_duration_time =edt_Complte_course_hor_grp.getText().toString();

                if(Price_grp.equalsIgnoreCase(""))
                {
                    Toast.makeText(FeeCalculatorActivity.this, "Please Enter sigle Student perhour price ", Toast.LENGTH_SHORT).show();

                }else  if(total_duration_time.equalsIgnoreCase(""))
                {

                    Toast.makeText(FeeCalculatorActivity.this, "Please Enter sigle Student Duration Time ", Toast.LENGTH_SHORT).show();

                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    get_calculation(Price_grp,total_duration_time);
                }
            }
        });

        lesson7Id_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isIndivisual.equalsIgnoreCase(""))
                {

                    Toast.makeText(FeeCalculatorActivity.this, "Please Add Single student Price ", Toast.LENGTH_SHORT).show();

                }else if(isGroup.equalsIgnoreCase(""))
                {

                    Toast.makeText(FeeCalculatorActivity.this, "Please Add Group student Price ", Toast.LENGTH_SHORT).show();

                }else {

                    Intent intent=new Intent(FeeCalculatorActivity.this, PoliceVerifyActivity.class);
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
                    intent.putExtra("subject","math");
                    intent.putExtra("per_hour_payment",per_hour_payment);
                    intent.putExtra("full_course_time",full_course_time);
                    startActivity(intent);

                }

            }
        });


    }

    private void findView() {


        progressBar = findViewById(R.id.progressBar);
        txt1_homeTab_individual = findViewById(R.id.txt1_homeTab);
        txt3_homeTab_grp = findViewById(R.id.txt3_homeTab);
        LL_indivisual = findViewById(R.id.LL_indivisual);
        LL_Group = findViewById(R.id.LL_Group);
        lesson7Id_indivisual = findViewById(R.id.lesson7Id_indivisual);
        edt_perHr_indivisual = findViewById(R.id.edt_perHr_indivisual);
        edt_perHr_grp = findViewById(R.id.edt_perHr_grp);
        edt_Complte_course_hor_indivisual = findViewById(R.id.edt_Complte_course_hor_indivisual);
        edt_Complte_course_hor_grp = findViewById(R.id.edt_Complte_course_hor_grp);
        lesson7Id_final = findViewById(R.id.lesson7Id_final);


        txt_hr_services_charge = findViewById(R.id.txt_hr_services_charge);
        txt_hr_dicount_student = findViewById(R.id.txt_hr_dicount_student);
        txt_hr_teachse_earning = findViewById(R.id.txt_hr_teachse_earning);
        txt_hr_student_prices = findViewById(R.id.txt_hr_student_prices);

        txt_hr_services_charge_grp = findViewById(R.id.txt_hr_services_charge_grp);
        txt_hr_dicount_student_grp = findViewById(R.id.txt_hr_dicount_student_grp);
        txt_hr_teachse_earning_grp = findViewById(R.id.txt_hr_teachse_earning_grp);
        txt_hr_student_prices_grp = findViewById(R.id.txt_hr_student_prices_grp);


        txt_weekly_services_charge = findViewById(R.id.txt_weekly_services_charge);
        txt_weekly_dicount_student = findViewById(R.id.txt_weekly_dicount_student);
        txt_weekly_teacharse_earingn = findViewById(R.id.txt_weekly_teacharse_earingn);
        txt_weekly_student_price = findViewById(R.id.txt_weekly_student_price);

        txt_weekly_services_charge_grp = findViewById(R.id.txt_weekly_services_charge_grp);
        txt_weekly_dicount_student_grp = findViewById(R.id.txt_weekly_dicount_student_grp);
        txt_weekly_teacharse_earingn_grp = findViewById(R.id.txt_weekly_teacharse_earingn_grp);
        txt_weekly_student_price_grp = findViewById(R.id.txt_weekly_student_price_grp);

        txt_monthly_services_charge = findViewById(R.id.txt_monthly_services_charge);
        txt_monthly_discount_student = findViewById(R.id.txt_monthly_discount_student);
        txt_monthly_teacharce_earning = findViewById(R.id.txt_monthly_teacharce_earning);
        txt_monthly_student_price = findViewById(R.id.txt_monthly_student_price);


        txt_monthly_services_charge_grp = findViewById(R.id.txt_monthly_services_charge_grp);
        txt_monthly_discount_studen_grp = findViewById(R.id.txt_monthly_discount_student_grp);
        txt_monthly_teacharce_earning_grp = findViewById(R.id.txt_monthly_teacharce_earning_grp);
        txt_monthly_student_price_grp = findViewById(R.id.txt_monthly_student_price_grp);

        txt_full_services_charge = findViewById(R.id.txt_full_services_charge);
        txt_full_discount_student = findViewById(R.id.txt_full_discount_student);
        txt_full_teacharce_earning = findViewById(R.id.txt_full_teacharce_earning);
        txt_full_student_price = findViewById(R.id.txt_full_student_price);

        txt_full_services_charge_grp = findViewById(R.id.txt_full_services_charge_grp);
        txt_full_discount_student_grp = findViewById(R.id.txt_full_discount_student_grp);
        txt_full_teacharce_earning_grp = findViewById(R.id.txt_full_teacharce_earning_grp);
        txt_full_student_price_grp = findViewById(R.id.txt_full_student_price_grp);

        lesson7Id_grp = findViewById(R.id.lesson7Id_grp);

    }


    /*public void nxtPolice(View view) {
         per_hour_payment=edt_perHr.getText().toString();
         full_course_time=edt_Complte_course_hor.getText().toString();

        if(per_hour_payment.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter perHor Payment", Toast.LENGTH_SHORT).show();

        }else if(full_course_time.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Course Timing", Toast.LENGTH_SHORT).show();
        }else
        {


        }

       // startActivity(new Intent(this,PoliceVerifyActivity.class));
    }*/

    private void get_calculation(String PerHour,String full_course_time) {

        String UserId  = Preference.get(FeeCalculatorActivity.this,Preference.KEY_USER_ID);

        Call<GetCalculaterModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_calculation("1",PerHour,full_course_time);

        call.enqueue(new Callback<GetCalculaterModel>() {
            @Override
            public void onResponse(Call<GetCalculaterModel> call, Response<GetCalculaterModel> response) {
                progressBar.setVisibility(View.GONE);
                try {

                    GetCalculaterModel myTimeSlot=response.body();

                    String status =myTimeSlot.getStatus();
                    String message =myTimeSlot.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        if(SignIndivisual_Check.equalsIgnoreCase("0"))
                        {
                            isIndivisual ="check";

                            txt_hr_services_charge.setText(""+myTimeSlot.getResult().getHourlyDetails().getServiceCharge());
                            txt_hr_dicount_student.setText(""+myTimeSlot.getResult().getHourlyDetails().getDiscountToStudent());
                            txt_hr_teachse_earning.setText(""+myTimeSlot.getResult().getHourlyDetails().getTeachersEarning());
                            txt_hr_student_prices.setText(""+myTimeSlot.getResult().getHourlyDetails().getStudentPrice());


                            txt_weekly_services_charge.setText(""+myTimeSlot.getResult().getWeeklyDetails().getServiceCharge());
                            txt_weekly_dicount_student.setText(""+myTimeSlot.getResult().getWeeklyDetails().getDiscountToStudent());
                            txt_weekly_teacharse_earingn.setText(""+myTimeSlot.getResult().getWeeklyDetails().getTeachersEarning());
                            txt_weekly_student_price.setText(""+myTimeSlot.getResult().getWeeklyDetails().getStudentPrice());

                            txt_monthly_services_charge.setText(""+myTimeSlot.getResult().getMonthlyDetails().getServiceCharge());
                            txt_monthly_discount_student.setText(""+myTimeSlot.getResult().getMonthlyDetails().getDiscountToStudent());
                            txt_monthly_teacharce_earning.setText(""+myTimeSlot.getResult().getMonthlyDetails().getTeachersEarning());
                            txt_monthly_student_price.setText(""+myTimeSlot.getResult().getMonthlyDetails().getStudentPrice());


                            txt_full_services_charge.setText(""+myTimeSlot.getResult().getFullCourse().getServiceCharge());
                            txt_full_discount_student.setText(""+myTimeSlot.getResult().getFullCourse().getDiscountToStudent());
                            txt_full_teacharce_earning.setText(""+myTimeSlot.getResult().getFullCourse().getTeachersEarning());
                            txt_full_student_price.setText(""+myTimeSlot.getResult().getFullCourse().getStudentPrice());


                        }else
                        {
                            isGroup ="check";

                            txt_hr_services_charge_grp.setText(""+myTimeSlot.getResult().getHourlyDetails().getServiceCharge());
                            txt_hr_dicount_student_grp.setText(""+myTimeSlot.getResult().getHourlyDetails().getDiscountToStudent());
                            txt_hr_teachse_earning_grp.setText(""+myTimeSlot.getResult().getHourlyDetails().getTeachersEarning());
                            txt_hr_student_prices_grp.setText(""+myTimeSlot.getResult().getHourlyDetails().getStudentPrice());


                            txt_weekly_services_charge_grp.setText(""+myTimeSlot.getResult().getWeeklyDetails().getServiceCharge());
                            txt_weekly_dicount_student_grp.setText(""+myTimeSlot.getResult().getWeeklyDetails().getDiscountToStudent());
                            txt_weekly_teacharse_earingn_grp.setText(""+myTimeSlot.getResult().getWeeklyDetails().getTeachersEarning());
                            txt_weekly_student_price_grp.setText(""+myTimeSlot.getResult().getWeeklyDetails().getStudentPrice());

                            txt_monthly_services_charge_grp.setText(""+myTimeSlot.getResult().getMonthlyDetails().getServiceCharge());
                            txt_monthly_discount_studen_grp.setText(""+myTimeSlot.getResult().getMonthlyDetails().getDiscountToStudent());
                            txt_monthly_teacharce_earning_grp.setText(""+myTimeSlot.getResult().getMonthlyDetails().getTeachersEarning());
                            txt_monthly_student_price_grp.setText(""+myTimeSlot.getResult().getMonthlyDetails().getStudentPrice());


                            txt_full_services_charge_grp.setText(""+myTimeSlot.getResult().getFullCourse().getServiceCharge());
                            txt_full_discount_student_grp.setText(""+myTimeSlot.getResult().getFullCourse().getDiscountToStudent());
                            txt_full_teacharce_earning_grp.setText(""+myTimeSlot.getResult().getFullCourse().getTeachersEarning());
                            txt_full_student_price_grp.setText(""+myTimeSlot.getResult().getFullCourse().getStudentPrice());


                        }


                    } else {

                        Toast.makeText(FeeCalculatorActivity.this, message, Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<GetCalculaterModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FeeCalculatorActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}