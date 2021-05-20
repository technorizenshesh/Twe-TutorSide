package com.tech.twe_tutorside.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.databinding.ActivityVerifyOtpBinding;
import com.tech.twe_tutorside.utils.RetrofitClients;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity {

    private static final String TAG = "VerifyOtpActivity";
    Context mContext = VerifyOtpActivity.this;
    ActivityVerifyOtpBinding binding;
    String mobile = "";
    HashMap<String, String> paramHash;
    HashMap<String, File> fileHashMap;

    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    private Boolean isInternetPresent = false;

    String message ="";
    String REGISTER_ID="";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp);

        try {
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this,
                    new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String newToken) {
                            Log.e(TAG, "newtokenLogin: " + newToken);
                            //PrefManager.setString(Constant.REGISTER_ID, newToken);
                            REGISTER_ID = newToken;
                          //  PrefManager.save(VerifyOtpActivity.this, PrefManager.REGISTER_ID, newToken);


                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, "Error=>" + e, Toast.LENGTH_LONG).show();
            Log.e(TAG, "Exception=>" + e);

            e.printStackTrace();
        }


       // FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        paramHash = new HashMap<>();
        fileHashMap = new HashMap<>();

        paramHash = (HashMap<String, String>) getIntent().getSerializableExtra("resgisterHashmap");
        // fileHashMap = (HashMap<String, File>) getIntent().getSerializableExtra("resgisterfileHashmap");
        mobile = "+91" + getIntent().getStringExtra("mobile");

        init();

       sendVerificationCode();

    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    binding.et2.setText("");
                    binding.et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });

        binding.et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    binding.et3.setText("");
                    binding.et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });

        binding.et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    binding.et4.setText("");
                    binding.et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });

        binding.et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    binding.et5.setText("");
                    binding.et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });

        binding.et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    binding.et6.setText("");
                    binding.et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        binding.rlVerify.setOnClickListener(v -> {

            if (TextUtils.isEmpty(binding.et1.getText().toString().trim())) {
                Toast.makeText(mContext, "please_enter_com_otp", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.et2.getText().toString().trim())) {
                Toast.makeText(mContext, "please_enter_com_otp", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.et3.getText().toString().trim())) {
                Toast.makeText(mContext,"please_enter_com_otp", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.et4.getText().toString().trim())) {
                Toast.makeText(mContext,"please_enter_com_otp", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.et5.getText().toString().trim())) {
                Toast.makeText(mContext, "please_enter_com_otp", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.et6.getText().toString().trim())) {
                Toast.makeText(mContext,"please_enter_com_otp", Toast.LENGTH_SHORT).show();
            } else {
                String finalOtp =
                                binding.et1.getText().toString().trim() +
                                binding.et2.getText().toString().trim() +
                                binding.et3.getText().toString().trim() +
                                binding.et4.getText().toString().trim() +
                                binding.et5.getText().toString().trim() +
                                binding.et6.getText().toString().trim();

                String UserName = paramHash.get("Username");
                String email = paramHash.get("email");
                String password = paramHash.get("password");
                String phone = paramHash.get("mobile");
                String latitude = paramHash.get("latitude");
                String longitude = paramHash.get("longitude");

              //  signUpMethod(UserName,email,password,phone,latitude,longitude);

             PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, finalOtp);

               signInWithPhoneAuthCredential(credential);

            }

        });

        binding.tvResend.setOnClickListener(v -> {
            sendVerificationCode();
        });

    }

    private void sendVerificationCode() {

        binding.tvVerifyText.setText("We have sent you an SMS on" + mobile + " with 6 digit verfication code");

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvResend.setText("" + millisUntilFinished / 1000);
                binding.tvResend.setEnabled(false);
            }
            @Override
            public void onFinish() {
                binding.tvResend.setText("resend");
                binding.tvResend.setEnabled(true);
            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile.replace(" ", ""), 60,  TimeUnit.SECONDS,  this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                // Phone number to verify
                // Timeout duration
                // Unit of timeout
                             // Activity (for callback binding)
                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        VerifyOtpActivity.this.id = id;
                        Toast.makeText(mContext, "Please enter 6 digit verification code", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        //   ProjectUtil.pauseProgressDialog()

                        Toast.makeText(mContext, ""+phoneAuthCredential.getSmsCode(), Toast.LENGTH_SHORT).show();

                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // ProjectUtil.pauseProgressDialog();
                        Toast.makeText(mContext, "Failed"+e, Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // ProjectUtil.pauseProgressDialog();
                            // Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();

                            Toast.makeText(mContext, "Successs", Toast.LENGTH_SHORT).show();

                            String UserName = paramHash.get("Username");
                            String email = paramHash.get("email");
                            String password = paramHash.get("password");
                            String phone = paramHash.get("mobile");
                            String latitude = paramHash.get("latitude");
                            String longitude = paramHash.get("longitude");

                            if(UserName != null
                                    && email != null
                                    && password != null
                                    && phone != null
                                    && latitude != null && longitude != null)
                            {

                              signUpMethod(UserName,email,password,phone,latitude,longitude);
                            }

                        } else {
                            // ProjectUtil.pauseProgressDialog();
                            Toast.makeText(mContext, "Otp Not match.", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }

                        }
                    }
                });

    }


    private void signUpMethod(String Username,String email,String password,String phone,String latitude,
                              String longitude) {
        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .signUp(Username,email,password,phone,"Tutor",latitude,longitude,REGISTER_ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {

                    binding.rlVerify.setEnabled(true);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    String UserId = resultOne.getString("id");

                    String username = resultOne.getString("username");

                    if (status.equalsIgnoreCase("1")) {

                        Preference.save(VerifyOtpActivity.this, Preference.KEY_USER_ID,UserId);

                        Toast.makeText(VerifyOtpActivity.this, UserId, Toast.LENGTH_SHORT).show();

                        Preference.save(VerifyOtpActivity.this, Preference.KEY_username,username);

                        startActivity(new Intent(VerifyOtpActivity.this, BuildingProfiActivity.class));
                        finish();

                    } else {
                        Toast.makeText(VerifyOtpActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VerifyOtpActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(VerifyOtpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.rlVerify.setEnabled(true);
                Toast.makeText(VerifyOtpActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

}