package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.CountrySpinnerAdapter;
import com.tech.twe_tutorside.model.myprofile_model;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  EditProfileActivity extends AppCompatActivity {

    LinearLayout LL_save;
    RelativeLayout RR_back;
    private SessionManager sessionManager;
    ProgressBar progressBar;
    Spinner spinnergender;
    EditText edt_name,edt_age,edt_phone;

    private String code[] ={"Male","Female"};
    private int flags[]= {R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};

    String Gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBar=findViewById(R.id.progressBar);
        edt_name=findViewById(R.id.edt_name);
        edt_age=findViewById(R.id.edt_age);
        spinnergender=findViewById(R.id.spinnergender);
        edt_phone=findViewById(R.id.edt_phone);
        RR_back=findViewById(R.id.RR_back);
        LL_save=findViewById(R.id.LL_save);

        CountrySpinnerAdapter customAdapter=new CountrySpinnerAdapter(EditProfileActivity.this,flags,code);
        spinnergender.setAdapter(customAdapter);

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3){

                Gender = code[pos];

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        sessionManager = new SessionManager(this);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            myProfile();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        LL_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName =edt_name.getText().toString();
                String Age =edt_age.getText().toString();
              //  String Gender =edt_name.getText().toString();
                String Mobile =edt_phone.getText().toString();

                if (sessionManager.isNetworkAvailable()) {

                    progressBar.setVisibility(View.VISIBLE);

                    UpdateProfile(UserName,Age,"Male",Mobile);

                }else {

                    Toast.makeText(EditProfileActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void myProfile() {

        String UserId = Preference.get(this, Preference.KEY_USER_ID);

        Call<myprofile_model> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_profile(UserId);
        call.enqueue(new Callback<myprofile_model>() {
            @Override
            public void onResponse(Call<myprofile_model> call, Response<myprofile_model> response) {

                try {

                    progressBar.setVisibility(View.GONE);

                    myprofile_model finallyPr = response.body();

                    String status=finallyPr.getStatus();
                    String message=finallyPr.getStatus();

                    if (status.equalsIgnoreCase("1")) {

                        String profile_image = finallyPr.getResult().get(0).getProfileImage();
                        String Gender = finallyPr.getResult().get(0).getUserDetails().getGender();

                        if(Gender.equalsIgnoreCase("Male"))
                        {
                            spinnergender.setSelection(0);

                        }else {

                            spinnergender.setSelection(1);
                        }
                        edt_name.setText(finallyPr.getResult().get(0).getUsername());
                        edt_age.setText(finallyPr.getResult().get(0).getUserDetails().getDob());
                       // edt_gender.setText(finallyPr.getResult().get(0).getUserDetails().getGender());
                        edt_phone.setText(finallyPr.getResult().get(0).getMobile());

                    } else {

                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<myprofile_model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateProfile(String userName, String age, String gender, String mobile) {

        String UserId = Preference.get(this, Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .update_profile_data(UserId,userName,mobile,age,Gender);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                     String username = resultOne.getString("username");

                    if (status.equalsIgnoreCase("1")) {

                        Preference.save(EditProfileActivity.this, Preference.KEY_username,username);

                        Toast.makeText(EditProfileActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();

                        finish();

                    } else {

                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}