package com.gbridge.etners.ui.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gbridge.etners.R;
import com.gbridge.etners.data.DailyAttendanceReportItem;
import com.gbridge.etners.data.EmployeeAttendanceItem;
import com.gbridge.etners.ui.admin.adapter.DailyAttendanceReportAdapter;
import com.gbridge.etners.ui.admin.adapter.EmployeeAttendanceManagementAdapter;

import java.util.ArrayList;
import java.util.List;


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
        View view = inflater.inflate(R.layout.fragment_daily_attendance_report, container, false);

        // Set actionbar
        initActionBar();
        initRecyclerView(view);

        return view;
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AdminActivity adminActivity = (AdminActivity) getActivity();
        ActionBar ahActionBar = adminActivity.getSupportActionBar();
        ahActionBar.setTitle(R.string.admin_daily_attendance_report_title);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void initRecyclerView(View view){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.divider_admin));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_daily_attendance_report);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new DailyAttendanceReportAdapter(getTestData()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((AdminActivity) getActivity()).removeAndPop(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<DailyAttendanceReportItem> getTestData() {
        ArrayList<DailyAttendanceReportItem> list = new ArrayList<>();

        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료","09:00","17:00","09:00","18:00"));
        return list;
    }

}