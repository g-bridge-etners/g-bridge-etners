package com.gbridge.etners.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gbridge.etners.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentList fragmentList = new FragmentList();
    private FragmentCalendar fragmentCalendar = new FragmentCalendar();

    private FrameLayout frame;
    private BottomNavigationView bnav;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        initViews();
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
            case R.id.bnav_calendar:
                transaction.replace(R.id.main_frame, fragmentCalendar).commitAllowingStateLoss();
                return true;
        }
        return false;
    }

    public String getToken() {return token;}
}
