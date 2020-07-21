package com.gbridge.etners.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gbridge.etners.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private FragmentHome fragmentHome;
    private FragmentList fragmentList;
    private FragmentSetting fragmentSetting;

    private FrameLayout frame;
    private BottomNavigationView bnav;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        fragmentManager = getSupportFragmentManager();
        fragmentHome = new FragmentHome(this, this, token);
        fragmentList = new FragmentList();
        fragmentSetting = new FragmentSetting(this, this);

        checkPermission();

        initViews();
    }

    private void checkPermission() {
        String deniedPermissions = "";

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            deniedPermissions += Manifest.permission.ACCESS_FINE_LOCATION + " ";
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            deniedPermissions += Manifest.permission.ACCESS_COARSE_LOCATION + " ";
        }

        if(!deniedPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, deniedPermissions.trim().split(" "), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            int length = permissions.length;
            for(int index = 0; index < length; index++) {
                if(grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "권한 허용: " + permissions[index]);
                }
            }
        }
    }

    private void initViews() {
        frame = findViewById(R.id.main_frame);
        bnav = findViewById(R.id.main_bottomNavigation);
        bnav.setOnNavigationItemSelectedListener(this);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragmentHome).commitAllowingStateLoss();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch(item.getItemId()) {
            case R.id.bnav_home:
                transaction.replace(R.id.main_frame, fragmentHome).commitAllowingStateLoss();
                return true;
            case R.id.bnav_list:
                transaction.replace(R.id.main_frame, fragmentList).commitAllowingStateLoss();
                return true;
            case R.id.bnav_setting:
                transaction.replace(R.id.main_frame, fragmentSetting).commitAllowingStateLoss();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public String getToken() {return token;}
}
