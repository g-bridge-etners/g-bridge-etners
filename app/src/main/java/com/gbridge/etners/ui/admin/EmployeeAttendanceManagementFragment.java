package com.gbridge.etners.ui.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gbridge.etners.R;
import com.gbridge.etners.data.EmployeeAttendanceItem;
import com.gbridge.etners.data.EmployeeAttendanceManagementItem;
import com.gbridge.etners.ui.admin.adapter.EmployeeAttendanceManagementAdapter;
import com.gbridge.etners.ui.admin.dialog.EmployeeAttendanceManagementDialog;

import java.util.ArrayList;
import java.util.List;




public class EmployeeAttendanceManagementFragment extends BaseFragment {


    private static final String TAG = "EAM";


    public EmployeeAttendanceManagementFragment() {
    }


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
        Log.d(TAG, "onCreateView: RUN");
        View view = inflater.inflate(R.layout.fragment_employee_attendance_management, container, false);

        initActionBar();
        initRecyclerView(view);

        return view;
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AdminActivity adminActivity = (AdminActivity) getActivity();
        ActionBar ahActionBar = adminActivity.getSupportActionBar();
        ahActionBar.setTitle(R.string.admin_employee_attendance_management_title);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void initRecyclerView(View view) {
        Context context = view.getContext();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_admin));
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new EmployeeAttendanceManagementAdapter(getTestData()) {
            @Override
            protected void onOptionClick(ViewHolder holder) {
                PopupMenu popup = new PopupMenu(getContext(), holder.btnOptions);
                popup.inflate(R.menu.menu_item_employee_attendance_management);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_item_employee_attendance_management_edit) {
                            Log.d(TAG, "onMenuItemClick: RUN");


                            EmployeeAttendanceManagementItem serverItem = null;
                            /*******************************************************************************


                                  TODO: 서버에서 EmployeeAttenDanceManagementItem 불러와야 함

                              if(true){ // 서버에 저장된 데이터 있는 경우
                                serverItem = [서버데이터]
                              }

                             서버에 저장된 데이터 없는 경우 null 유지
                             null인 경우 - dialog 빈페이지, 값이 있는 경우 - dialog 채워줌
                             *******************************************************************************/

                            EmployeeAttendanceManagementDialog dialog = new EmployeeAttendanceManagementDialog(getActivity(), serverItem) {
                                @Override
                                public void onPositiveClicked(EmployeeAttendanceManagementItem item) {
                                    holder.tvStartTime.setText(item.getStartTime());
                                    holder.tvEndTime.setText(item.getEndTime());
                                    holder.tvStartDate.setText(item.getStartDate());
                                    holder.tvEndDate.setText(item.getEndDate());

                                    /*******************************************************************************

                                        TODO: EmployeeAttenDanceManagementItem item 서버에 전송 필요 (REST API 개발중)

                                     *******************************************************************************/
                                }

                                @Override
                                public void onNegativeClicked() {

                                }
                            };
                            dialog.show();
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            dialog.getWindow().setLayout(metrics.widthPixels*9/10, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_round_white_view);

                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
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

     TODO: 서버에서 데이터 불러오는 작업 필요 (REST API 개발중)

     ******************************************************************************/
    private List<EmployeeAttendanceItem> getTestData() {
        ArrayList<EmployeeAttendanceItem> list = new ArrayList<>();

        list.add(new EmployeeAttendanceItem("김태호",
                "개발팀", "201739413", "", "", null, null));
        list.add(new EmployeeAttendanceItem("정영훈",
                "개발팀", "201000000", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("천재웅",
                "개발팀", "201000000", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("최다빈",
                "개발팀", "201000000", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("홍길동",
                "기획팀", "201899123", "10:00", "19:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("김수현",
                "기획팀", "201410213", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("서예지",
                "총무팀", "201912333", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("유재석",
                "총무팀", "201156783", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("이효리",
                "경영팀", "202042353", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("정지훈",
                "경영팀", "201412533", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("이상순",
                "개발팀", "201112363", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("나영석",
                "개발팀", "201043323", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("강호동",
                "개발팀", "202010303", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("이수근",
                "개발팀", "201213435", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("송민호",
                "개발팀", "201516373", "09:00", "17:00","20-08-20", "22-09-30"));
        list.add(new EmployeeAttendanceItem("은지원",
                "개발팀", "201912433", "09:00", "17:00","20-08-20", "22-09-30"));
        return list;
    }
}