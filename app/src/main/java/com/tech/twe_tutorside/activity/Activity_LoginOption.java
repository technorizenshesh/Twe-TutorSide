package com.tech.twe_tutorside.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tech.twe_tutorside.GPSTracker;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Activity_LoginOption extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    RelativeLayout RR_google;
    RelativeLayout RR_loign_faceBook;

    //Google SignIn
    private RelativeLayout RR_google_login;
    private SignInButton signInButton;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;

    String token="";
    String result="";
    private static final String TAG = "fireBaseToken";
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    //FaceBook
    CallbackManager mCallbackManager;
    LoginButton loginButton;

    GPSTracker gpsTracker;
    String latitude="";
    String longitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity__login_option);

        RR_google= findViewById(R.id.RR_google);
        progressBar= findViewById(R.id.progressBar);
        RR_loign_faceBook= findViewById(R.id.RR_loign_faceBook);
        sessionManager = new SessionManager(Activity_LoginOption.this);


        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            // Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            //  Log.e(TAG, "printHashKey()", e);
        }


        //FirebaseToke
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        Log.e("token",token);
                    }
                });

        RR_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        //Google SignIn
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        RR_loign_faceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.performClick();
            }
        });

        //FaceBook
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Toast.makeText(Activity_LoginOption.this, "btnCancel", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Activity_LoginOption.this, "Btnerrror", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });

        //Gps Lat Long
        gpsTracker=new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
        }else{
            gpsTracker.showSettingsAlert();
        }

    }

    //Google Login
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            handleSignInResult( result );
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //   Toast.makeText(Activity_LoginOption.this, ""+token, Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            String UsernAME=user.getDisplayName();
                            String email=user.getEmail();
                            String SocialId=user.getUid();
                            Uri Url=user.getPhotoUrl();

                            if (sessionManager.isNetworkAvailable()) {

                                progressBar.setVisibility(View.VISIBLE);

                                SocialLoginMethod(UsernAME,email,"123456789",SocialId);

                            }else {

                                Toast.makeText(Activity_LoginOption.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            Toast.makeText(Activity_LoginOption.this, "12"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            String UsernAME=account.getDisplayName();
            String email=account.getEmail();
            String SocialId=account.getId();
            Uri Url=account.getPhotoUrl();

            if (sessionManager.isNetworkAvailable()) {

                progressBar.setVisibility(View.VISIBLE);

                SocialLoginMethod(UsernAME,email,"123456789",SocialId);

            }else {
                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText( this, "Login Unsuccessful", Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void continueEmailInit(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    private void SocialLoginMethod(String UserName,String email,String Mobile,String SocialId) {

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .Social_login(UserName,email,Mobile,"Tutor",latitude,longitude,SocialId,token);

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

                    String check_status = resultOne.getString("check_status");
                    String username = resultOne.getString("username");
                    String image = resultOne.getString("image");
                    String UserId = resultOne.getString("id");

                    if (status.equalsIgnoreCase("1")) {

                        sessionManager.saveUserId(UserId);

                        Preference.save(Activity_LoginOption.this,Preference.KEYType_login,"login");
                        Preference.save(Activity_LoginOption.this, Preference.KEY_check_status,check_status);
                        Preference.save(Activity_LoginOption.this, Preference.KEY_USER_ID,UserId);
                        Preference.save(Activity_LoginOption.this, Preference.KEY_username,username);

                        Toast.makeText(Activity_LoginOption.this, message, Toast.LENGTH_SHORT).show();

                        if(check_status.equalsIgnoreCase("1"))
                        {
                            startActivity(new Intent(Activity_LoginOption.this, HomeActvity.class));

                        }else
                        {
                            startActivity(new Intent(Activity_LoginOption.this, BuildingProfiActivity.class));

                        }

                    } else {

                        Toast.makeText(Activity_LoginOption.this, result, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {

                    Toast.makeText(Activity_LoginOption.this, result, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                } catch (IOException e) {

                    Toast.makeText(Activity_LoginOption.this, result, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Activity_LoginOption.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}