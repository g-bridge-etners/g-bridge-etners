package com.gbridge.etners.ui.admin;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.main.MainActivity;

import java.util.Objects;


public class AdminHomeFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = "AH";


    public AdminHomeFragment() {
    }


    public static AdminHomeFragment newInstance() {
        AdminHomeFragment fragment = new AdminHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: RUN");
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        // Set actionbar
        initActionBar();

        // Set buttons click listener
        view.findViewById(R.id.btn_admin_home_employee_attendance_management).setOnClickListener(this);
        view.findViewById(R.id.btn_admin_home_daily_attendance_report).setOnClickListener(this);
        view.findViewById(R.id.btn_admin_home_weekly_attendance_report).setOnClickListener(this);

        return view;
    }


    private void initActionBar() {
        setHasOptionsMenu(true);
        AdminActivity adminActivity = (AdminActivity) getActivity();
        ActionBar ahActionBar = adminActivity.getSupportActionBar();
        ahActionBar.setTitle(R.string.admin_home_title);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((AdminActivity) getActivity()).finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_admin_home_employee_attendance_management:
                Log.d(TAG, "onClick: EAM");
                ((AdminActivity) getActivity()).replaceWithBackStack(EmployeeAttendanceManagementFragment.newInstance());
                break;
            case R.id.btn_admin_home_daily_attendance_report:
                Log.d(TAG, "onClick: DAR");
                ((AdminActivity) getActivity()).replaceWithBackStack(DailyAttendanceReportFragment.newInstance());
                break;
            case R.id.btn_admin_home_weekly_attendance_report:
                Log.d(TAG, "onClick: WAR");
                ((AdminActivity) getActivity()).replaceWithBackStack(WeeklyAttendanceReportFragment.newInstance());
                break;
        }
    }
}