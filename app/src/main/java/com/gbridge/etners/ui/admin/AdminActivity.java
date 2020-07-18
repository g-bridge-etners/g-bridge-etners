package com.gbridge.etners.ui.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.main.FragmentCalendar;
import com.gbridge.etners.ui.main.FragmentHome;
import com.gbridge.etners.ui.main.FragmentList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar adminToolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(adminToolbar);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_admin, AdminHomeFragment.newInstance()).commitAllowingStateLoss();
    }




    public void removeAndPop(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().remove(fragment).commit();
        Log.d("test", Integer.toString(fragmentManager.getFragments().size()));
    }



    public void replaceWithBackStack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.enter_right, R.anim.out_left, R.anim.enter_left, R.anim.out_right)
                .replace(R.id.container_admin, fragment)
                .addToBackStack(null)
                .commit();
    }



}
