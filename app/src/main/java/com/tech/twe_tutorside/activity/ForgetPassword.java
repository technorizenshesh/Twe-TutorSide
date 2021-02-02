package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class ForgetPassword extends AppCompatActivity {

    private SessionManager sessionManager;
    private String android_id;
    private String email="";
    private ProgressBar progressBar;

    private EditText edt_email;
    private LinearLayout LL_submit;
    private TextView txt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}
        setContentView(R.layout.activity_forget_password);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        setUi();
    }

    private void setUi() {
        edt_email=findViewById(R.id.edt_email);
        progressBar=findViewById(R.id.progressBar);
        LL_submit=findViewById(R.id.LL_submit);
        txt_login=findViewById(R.id.txt_login);
        sessionManager = new SessionManager(ForgetPassword.this);


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));

            }
        });
        LL_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });
    }
    private void validation() {

        email = edt_email.getText().toString();

        if(!isValidEmail(email))
        {
            Toast.makeText(this, "Please Enter email.", Toast.LENGTH_SHORT).show();

        }else
        {
            if (sessionManager.isNetworkAvailable()) {

                LL_submit.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                forgetMethod();

            }else {
                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



    private void forgetMethod() {

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .forgotPassword(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                 //   JSONObject resultOne = jsonObject.getJSONObject("result");

                  //  String UserId = resultOne.getString("id");

                    if (status.equalsIgnoreCase("1")) {

                        LL_submit.setEnabled(true);

                        progressBar.setVisibility(View.GONE);

                        //sessionManager.saveUserId(UserId);

                        Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ForgetPassword.this, LoginActivity.class));

                    } else {
                        Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        LL_submit.setEnabled(true);
                        Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
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
                LL_submit.setEnabled(true);
                Toast.makeText(ForgetPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}