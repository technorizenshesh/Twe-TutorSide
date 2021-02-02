
package com.tech.twe_tutorside.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.tech.twe_tutorside.GPSTracker;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {

    private EditText edt_name;
    private EditText edt_phone;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_confirm_password;
    private LinearLayout LL_signUp;

    private SessionManager sessionManager;
    private String android_id;
    private ProgressBar progressBar;

    String Username="";
    String phone="";
    String email="";
    String password="";
    String Cpassword="";
    CheckBox check;
    GPSTracker gpsTracker;

    String latitude="";
    String longitude="";
    String message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}
        setContentView(R.layout.activity_sign_up);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

          gpsTracker=new GPSTracker(this);

        if(gpsTracker.canGetLocation()){

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        }else{
            gpsTracker.showSettingsAlert();
        }

        setUi();

        LL_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });

    }

    public void SignUPInit(View view) {

       //startActivity(new Intent(SignUpActivity.this, Activity_otp.class));
    }

    public void backLoginInit(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    private void setUi() {
        check=findViewById(R.id.check);
        edt_name=findViewById(R.id.edt_name);
        edt_phone=findViewById(R.id.edt_phone);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        edt_confirm_password=findViewById(R.id.edt_confirm_password);
        progressBar=findViewById(R.id.progressBar);
        LL_signUp=findViewById(R.id.LL_signUp);
        sessionManager = new SessionManager(SignUpActivity.this);
    }


    private void validation() {

         Username = edt_name.getText().toString();
         phone = edt_phone.getText().toString();
         email = edt_email.getText().toString();
         password = edt_password.getText().toString();
         Cpassword = edt_confirm_password.getText().toString();

        if(Username.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter name.", Toast.LENGTH_SHORT).show();

        }else if(phone.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter phone.", Toast.LENGTH_SHORT).show();

        }else if(!isValidEmail(email))
        {
            Toast.makeText(this, "Please Enter email.", Toast.LENGTH_SHORT).show();

        }else if(password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Password.", Toast.LENGTH_SHORT).show();

        }else if(Cpassword.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Confirm password.", Toast.LENGTH_SHORT).show();

        }else if(! Cpassword.equalsIgnoreCase(password))
        {
            Toast.makeText(this, "Don't match password.", Toast.LENGTH_SHORT).show();

        }else if(!check.isChecked())
        {
            Toast.makeText(this, "Please check atleast 18 years old.", Toast.LENGTH_SHORT).show();

        }else
        {
            if (sessionManager.isNetworkAvailable()) {

                LL_signUp.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);

               signUpMethod();

            }else {
                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void signUpMethod() {
        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .signUp(Username,email,password,phone,"Tutor",latitude,longitude,android_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    String UserId = resultOne.getString("id");

                    String username = resultOne.getString("username");

                    if (status.equalsIgnoreCase("1")) {

                        LL_signUp.setEnabled(true);

                        progressBar.setVisibility(View.GONE);

                        Preference.save(SignUpActivity.this,Preference.KEY_USER_ID,UserId);

                        Toast.makeText(SignUpActivity.this, UserId, Toast.LENGTH_SHORT).show();

                        Preference.save(SignUpActivity.this, Preference.KEY_username,username);

                        startActivity(new Intent(SignUpActivity.this, Activity_otp.class));

                    } else {
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        LL_signUp.setEnabled(true);
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                LL_signUp.setEnabled(true);
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}