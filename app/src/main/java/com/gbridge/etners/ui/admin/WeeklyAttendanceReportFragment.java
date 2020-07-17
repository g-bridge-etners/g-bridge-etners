package com.gbridge.etners.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gbridge.etners.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyAttendanceReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_attendance_report, container, false);
    }
}