package com.tech.twe_tutorside.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tech.twe_tutorside.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Activity_otp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}
        setContentView(R.layout.layout_otp);
    }


    public void OTPInit(View view) {
        startActivity(new Intent(Activity_otp.this, BuildingProfiActivity.class));
        finish();
    }

    public void backCancelBtn(View view) {
        onBackPressed();
        finish();
    }
}
