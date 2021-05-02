package com.tech.twe_tutorside.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.SessionManager;
import com.tech.twe_tutorside.utils.RetrofitClients;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {

    LinearLayout LL_send;
    ProgressBar progressBar;
    EditText edt_phone;
    EditText edt_email;
    EditText edt_msg;

    private SessionManager sessionManager;
    String result;
    String description="";
    RelativeLayout RR_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        LL_send =findViewById(R.id.LL_send);
        progressBar =findViewById(R.id.progressBar);
        edt_phone =findViewById(R.id.edt_phone);
        edt_email =findViewById(R.id.edt_email);
        edt_msg =findViewById(R.id.edt_msg);
        RR_back =findViewById(R.id.RR_back);
        sessionManager = new SessionManager(ContactUsActivity.this);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        LL_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PhoneNumber =edt_phone.getText().toString();
                String Email =edt_email.getText().toString();
                String message =edt_msg.getText().toString();

                if(PhoneNumber.equalsIgnoreCase(""))
                {
                    Toast.makeText(ContactUsActivity.this, "Please Enter PhoneNumber", Toast.LENGTH_SHORT).show();

                }else if(Email.equalsIgnoreCase(""))
                {
                    Toast.makeText(ContactUsActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();

                }else if(message.equalsIgnoreCase(""))
                {
                    Toast.makeText(ContactUsActivity.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (sessionManager.isNetworkAvailable()) {

                        progressBar.setVisibility(View.VISIBLE);
                        ContactApi(PhoneNumber,Email,message);
                    }else {
                        Toast.makeText(ContactUsActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ContactApi(String phoneNumber, String email, String message){

        String UserId=Preference.get(ContactUsActivity.this, Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .contact_info(UserId,email,phoneNumber,message);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(ContactUsActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ContactUsActivity.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ContactUsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}