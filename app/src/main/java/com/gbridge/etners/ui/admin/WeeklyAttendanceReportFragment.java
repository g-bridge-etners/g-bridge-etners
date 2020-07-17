package com.gbridge.etners.ui.admin;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gbridge.etners.R;


public class WeeklyAttendanceReportFragment extends Fragment {


    private static final String TAG = "WAR";
    

    public WeeklyAttendanceReportFragment() {
        // Required empty public constructor
    }


    public static WeeklyAttendanceReportFragment newInstance() {
        WeeklyAttendanceReportFragment fragment = new WeeklyAttendanceReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_weekly_attendance_report, container, false);

        // Set actionbar
        initActionBar();

        return view;
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AdminActivity adminActivity = (AdminActivity) getActivity();
        ActionBar ahActionBar = adminActivity.getSupportActionBar();
        ahActionBar.setTitle(R.string.admin_weekly_attendance_report_title);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((AdminActivity) getActivity()).removeAndPop(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}