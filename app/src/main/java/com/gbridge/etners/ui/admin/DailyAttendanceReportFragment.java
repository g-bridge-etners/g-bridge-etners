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
 */
public class DailyAttendanceReportFragment extends Fragment {


    private static final String TAG = "DAR";


    public DailyAttendanceReportFragment() {
    }


    public static DailyAttendanceReportFragment newInstance() {
        DailyAttendanceReportFragment fragment = new DailyAttendanceReportFragment();
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
        return inflater.inflate(R.layout.fragment_time_attendance_report, container, false);
    }
}