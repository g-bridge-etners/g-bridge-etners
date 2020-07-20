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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gbridge.etners.R;
import com.gbridge.etners.data.EmployeeAttendanceItem;
import com.gbridge.etners.ui.admin.adapter.EmployeeAttendanceManagementAdapter;
import com.gbridge.etners.ui.admin.dialog.EmployeeAttendanceManagementDialog;
import com.gbridge.etners.util.retrofit.admin.AdminService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hmomeni.progresscircula.ProgressCircula;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmployeeAttendanceManagementFragment extends BaseFragment {

    private EmployeeAttendanceManagementAdapter myAdapter;
    private static final String TAG = "EAM";
    private Retrofit retrofit;
    private AdminService adminService;
    private String token;
    private ProgressCircula progressCircula;
    private LinearLayout containerEmpty;

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
        token = ((AdminActivity) getActivity()).getToken();
        progressCircula = (ProgressCircula) view.findViewById(R.id.progress_circular_employee_attendance_management);
        containerEmpty = (LinearLayout) view.findViewById(R.id.container_empty_data_employee_attendance_management);

        initActionBar();
        initRecyclerView(view);
        initRetrofit();
        readServerAttendances();

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
        ahActionBar.setTitle(R.string.admin_employee_attendance_management_title);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void initRecyclerView(View view) {
        Context context = view.getContext();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_admin));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_employee_attendance_management);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        myAdapter = new EmployeeAttendanceManagementAdapter() {
            @Override
            protected void onOptionClick(ViewHolder holder, EmployeeAttendanceItem employeeAttendanceItem) {
                PopupMenu popup = new PopupMenu(getContext(), holder.btnOptions);
                popup.inflate(R.menu.menu_item_employee_attendance_management);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_item_employee_attendance_management_edit) {
                            Log.d(TAG, "onMenuItemClick: RUN");
                               EmployeeAttendanceManagementDialog dialog = new EmployeeAttendanceManagementDialog(getActivity(), employeeAttendanceItem) {
                                @Override
                                public void onPositiveClicked(EmployeeAttendanceItem item) {
                                    myAdapter.changeItems(new ArrayList<>());
                                    sendAttendanceServer(item);
                                }

                                @Override
                                public void onNegativeClicked() {

                                }
                            };
                            dialog.show();
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            dialog.getWindow().setLayout(metrics.widthPixels * 9 / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_round_white_view);

                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        };
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((AdminActivity) getActivity()).removeAndPop(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void readServerAttendances() {
        showInnerProgress();
        adminService.getAttendances(token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("attendance");
                    List<EmployeeAttendanceItem> list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<EmployeeAttendanceItem>>() {
                    }.getType());
                    myAdapter.changeItems(list);

                } else if(response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                    containerEmpty.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "로딩실패", Toast.LENGTH_SHORT).show();
                }
                hideInnerProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideInnerProgress();
                Toast.makeText(getContext(), "로딩실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendAttendanceServer(EmployeeAttendanceItem item){
        adminService.putAttendance(token, item).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, Integer.toString(response.code()));
                if(response.isSuccessful()){
                    readServerAttendances();
                } else {
                    Toast.makeText(getContext(), "전송실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                Toast.makeText(getContext(), "전송실패", Toast.LENGTH_SHORT).show();
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