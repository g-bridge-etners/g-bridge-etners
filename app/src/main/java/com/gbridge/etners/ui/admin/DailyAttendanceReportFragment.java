package com.gbridge.etners.ui.admin;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gbridge.etners.R;
import com.gbridge.etners.data.DailyAttendanceReportItem;
import com.gbridge.etners.ui.admin.adapter.DailyAttendanceReportAdapter;
import com.gbridge.etners.util.retrofit.admin.AdminService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hmomeni.progresscircula.ProgressCircula;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DailyAttendanceReportFragment extends Fragment {


    private static final String TAG = "DAR";
    private RecyclerView recyclerView;
    private CalendarView calendarView;
    private TextView tvDate;
    private DailyAttendanceReportAdapter myAdapter;
    private Retrofit retrofit;
    private AdminService adminService;
    private String token;
    private ProgressCircula progressCircula;
    private LinearLayout containerEmpty;

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
        progressCircula = (ProgressCircula) view.findViewById(R.id.progress_circular_daily_attendance_report);
        containerEmpty = (LinearLayout) view.findViewById(R.id.container_empty_data_daily_attendance_report);

        showInnerProgress();

        token = ((AdminActivity) getActivity()).getToken();

        // Set actionbar
        initActionBar();
        initRetrofit();
        initRecyclerView();
        initCalendarView();


        return view;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://34.82.68.95:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        adminService = retrofit.create(AdminService.class);
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

        Long currentTime = calendarView.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd", Locale.KOREA);
        String currentDate = dateFormat.format(currentTime);
        myAdapter.setDate(currentDate);
        readServerData(currentDate);
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
                myAdapter.changeItems(new ArrayList<>());
                containerEmpty.setVisibility(View.GONE);
                tvDate.setText(String.format(Locale.KOREA, "%d년 %d월 %d일", year, month + 1, dayOfMonth));
                String changeYear = Integer.toString(year).substring(2);
                String changeMonth;
                if (month < 10) {
                    changeMonth = "0" + (month + 1);
                } else {
                    changeMonth = Integer.toString(month + 1);
                }
                String changeDay;
                if (dayOfMonth < 10) {
                    changeDay = "0" + dayOfMonth;
                } else {
                    changeDay = Integer.toString(dayOfMonth);
                }
                String date = changeYear + "-" + changeMonth + "-" + changeDay;
                showInnerProgress();
                readServerData(date);
                myAdapter.setDate(date);
            }
        });
    }

    private void updateRecyclerView(List<DailyAttendanceReportItem> items) {
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


    private void readServerData(String date) {
        adminService.getDailyReport(token, date).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("dailyReport");
                    List<DailyAttendanceReportItem> list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<DailyAttendanceReportItem>>() {
                    }.getType());
                    myAdapter.changeItems(list);

                } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    containerEmpty.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "로딩실패", Toast.LENGTH_SHORT).show();
                }
                hideInnerProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                hideInnerProgress();
                Toast.makeText(getContext(), "로딩실패", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showInnerProgress(){
        progressCircula.setVisibility(View.VISIBLE);
    }
    private void hideInnerProgress(){
        progressCircula.setVisibility(View.GONE);
    }


}