package com.gbridge.etners.ui.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.CalendarView;
import android.widget.TextView;

import com.gbridge.etners.R;
import com.gbridge.etners.data.DailyAttendanceReportItem;
import com.gbridge.etners.data.EmployeeAttendanceItem;
import com.gbridge.etners.ui.admin.adapter.DailyAttendanceReportAdapter;
import com.gbridge.etners.ui.admin.adapter.EmployeeAttendanceManagementAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DailyAttendanceReportFragment extends Fragment {


    private static final String TAG = "DAR";
    private RecyclerView recyclerView;
    private CalendarView calendarView;
    private TextView tvDate;
    private DailyAttendanceReportAdapter myAdapter;
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


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_daily_attendance_report);
        calendarView = (CalendarView) view.findViewById(R.id.calendar_view_daily_attendance_report);
        tvDate = (TextView) view.findViewById(R.id.tv_daily_attendance_report_date);

        // Set actionbar
        initActionBar();
        initRecyclerView();
        initCalendarView();

        return view;
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AdminActivity adminActivity = (AdminActivity) getActivity();
        ActionBar ahActionBar = adminActivity.getSupportActionBar();
        ahActionBar.setTitle(R.string.admin_daily_attendance_report_title);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void initRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.divider_admin));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        myAdapter = new DailyAttendanceReportAdapter();
        recyclerView.setAdapter(myAdapter);

        /*******************************************************************************

         TODO: 작업1 - 서버에서 데이터 불러오는 작업 필요 By 날짜 (REST API 개발중)
         초기 뷰 생성시 오늘 날짜로 rest api 호출 및
         updateRecyclerView에 LIST 전달

         ******************************************************************************/
        updateRecyclerView(getTestData());
    }

    private void initCalendarView() {
        Long currentTime = calendarView.getDate();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        String currentYear = yearFormat.format(currentTime);
        String currentMonth = monthFormat.format(currentTime);
        String currentDay = dayFormat.format(currentTime);
        tvDate.setText(String.format("%s년 %s월 %s일", currentYear, currentMonth, currentDay));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tvDate.setText(String.format(Locale.KOREA, "%d년 %d월 %d일", year, month + 1, dayOfMonth));

                /*******************************************************************************

                 TODO: 작업1 - 서버에서 데이터 불러오는 작업 필요 By 날짜 (REST API 개발중)
                 날짜 선택시 REST API 호출하고
                 updateRecyclerView에 LIST 넘겨야 함

                 ******************************************************************************/
                updateRecyclerView(getTestData2());
            }
        });
    }

    private void updateRecyclerView(List<DailyAttendanceReportItem> items){
        myAdapter.changeItems(items);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((AdminActivity) getActivity()).removeAndPop(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*******************************************************************************

     TODO: 작업1 - 서버에서 데이터 불러오는 작업 필요 By 날짜 (REST API 개발중)
     날짜로 데이터 불러오는 REST API 필요

     ******************************************************************************/
    private List<DailyAttendanceReportItem> getTestData() {
        ArrayList<DailyAttendanceReportItem> list = new ArrayList<>();

        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "비정상", "09:00", "17:00", "10:00", "16:00"));
        list.add(new DailyAttendanceReportItem("정영훈",
                "개발팀", "201000000", "근무중", "09:00", "17:00", "09:00", null));
        list.add(new DailyAttendanceReportItem("천재웅",
                "개발팀", "201000000", null, "10:00", null, "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("최다빈",
                "개발팀", "201000000", "완료", "11:00", "17:00", "11:00", "17:00"));
        list.add(new DailyAttendanceReportItem("김수현",
                "기획팀", "201739433", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("서예지",
                "경영팀", "201839413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        return list;
    }
    private List<DailyAttendanceReportItem> getTestData2() {
        ArrayList<DailyAttendanceReportItem> list = new ArrayList<>();

        list.add(new DailyAttendanceReportItem("테스트",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "11:00", "18:00"));
        list.add(new DailyAttendanceReportItem("정영훈",
                "개발팀", "201000000", "근무중", "09:00", "17:00", "09:00", null));
        list.add(new DailyAttendanceReportItem("천재웅",
                "개발팀", "201000000", "퇴근완료", "10:00", null, "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("최다빈",
                "개발팀", "201000000", "퇴근완료", "11:00", "17:00", "12:00", "16:00"));
        list.add(new DailyAttendanceReportItem("김수현",
                "기획팀", "201739433", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("서예지",
                "경영팀", "201839413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        list.add(new DailyAttendanceReportItem("김태호",
                "개발팀", "201739413", "퇴근완료", "09:00", "17:00", "09:00", "18:00"));
        return list;
    }

}