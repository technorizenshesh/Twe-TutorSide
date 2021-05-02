package com.tech.twe_tutorside.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class TermsCondition extends AppCompatActivity {

    RelativeLayout RR_back;
    ProgressBar progressBar;
    TextView txt_terms;

    String result;
    String description="";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        RR_back =findViewById(R.id.RR_back);
        progressBar =findViewById(R.id.progressBar);
        txt_terms =findViewById(R.id.txt_terms);
        sessionManager = new SessionManager(TermsCondition.this);

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            TermsCondition();

        }else {
            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }

    private void TermsCondition(){

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .terms_conditions();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    result = jsonObject.getString("result");
                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    description = resultOne.getString("description");

                    if (status.equalsIgnoreCase("1")) {

                        //txt_privacy_policy.setText(description);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txt_terms.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txt_terms.setText(Html.fromHtml(description));
                        }

                        Toast.makeText(TermsCondition.this, message, Toast.LENGTH_SHORT).show();


                    } else {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(TermsCondition.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TermsCondition.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}