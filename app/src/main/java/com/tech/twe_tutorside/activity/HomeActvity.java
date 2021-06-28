package com.tech.twe_tutorside.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.fragments.HomeFragment;
import com.tech.twe_tutorside.fragments.MessageFragment;
import com.tech.twe_tutorside.fragments.MyProfileFragment;
import com.tech.twe_tutorside.fragments.NotificationFragment;
import com.tech.twe_tutorside.listner.FragmentListener;


public class HomeActvity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentListener {

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView navigationView;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home_actvity);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment(this));
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack("home").commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:
                setColorOnBar(R.color.colorBackground);
                loadFragment(new HomeFragment(this));
                break;

            case R.id.notify:
                setColorOnBar(R.color.colorWhite);
                loadFragment(new NotificationFragment(this));
              //  startActivity(new Intent(HomeActvity.this,YourPackLessonActivity.class));
                break;

            case R.id.chat:
                setColorOnBar(R.color.colorBackground);
                loadFragment(new MessageFragment(this));
               // startActivity(new Intent(HomeActvity.this,ChatActivity.class));
                break;

            case R.id.account:
                setColorOnBar(R.color.colorBackground);
                loadFragment(new MyProfileFragment(this));
                break;
        }

        return true;

    }
    @Override
    public void onBackPressed() {
       if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            //ExitApp();
//           finishAffinity();
           // System.exit(0);
           if (doubleBackToExitPressedOnce) {
               super.onBackPressed();
               finishAffinity();
               return;
           }

           this.doubleBackToExitPressedOnce = true;
           Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

           new Handler().postDelayed(new Runnable() {

               @Override
               public void run() {
                   doubleBackToExitPressedOnce=false;
               }
           }, 2000);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            fragmentManager = getSupportFragmentManager();
            for (int i = 1; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
        }
       /* if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);*/
    }


    @Override
    public void click(Fragment fragment) {
        loadFragment(fragment);
    }


    void setColorOnBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(color, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

}