package com.gbridge.etners.ui.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gbridge.etners.BaseActivity;
import com.gbridge.etners.R;

import static com.gbridge.etners.ApplicationClass.X_ACCESS_TOKEN;

public class AdminActivity extends BaseActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar adminToolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(adminToolbar);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_admin, AdminHomeFragment.newInstance()).commitAllowingStateLoss();

        SharedPreferences sharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
        token = sharedPreferences.getString(X_ACCESS_TOKEN, "none");
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

    public String getToken() {
        return token;
    }


}
