package com.gbridge.etners.ui.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.admin.adapter.MyIndividualScheduleManagementRecyclerViewAdapter;
import com.gbridge.etners.ui.admin.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class EmployeeAttendanceManagementFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String TAG = "ISM";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeeAttendanceManagementFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EmployeeAttendanceManagementFragment newInstance() {
        EmployeeAttendanceManagementFragment fragment = new EmployeeAttendanceManagementFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_attendance_management, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyIndividualScheduleManagementRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }
}