package com.gbridge.etners.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gbridge.etners.R;
import com.gbridge.etners.util.GpsReceiver;
import com.gbridge.etners.util.WifiUtil;
import com.gbridge.etners.util.retrofit.commute.CommuteAPI;
import com.gbridge.etners.util.retrofit.commute.CommuteRequest;
import com.gbridge.etners.util.retrofit.commute.CommuteResponse;
import com.gbridge.etners.util.retrofit.commute_check.CommuteCheckAPI;
import com.gbridge.etners.util.retrofit.commute_check.CommuteCheckResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHome extends Fragment implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private String token;
    private boolean isWifiConnected = false;
    private boolean isGpsConnected = false;
    private int commuteState = 0;   //0:미출근, 1:출근, 2:퇴근

    private LinearLayout stateLayout;
    private ImageView wifiState, gpsState;
    private TextView date, name, userState, startTime, endTime, workingTime;
    private Button checkButton;

    public FragmentHome(Context context, Activity activity, String token) {
        this.context = context;
        this.activity = activity;
        this.token = token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        stateLayout = view.findViewById(R.id.home_stateLayout);
        wifiState = view.findViewById(R.id.home_wifiState);
        wifiState.setOnClickListener(this);
        gpsState = view.findViewById(R.id.home_gpsState);
        gpsState.setOnClickListener(this);
        date = view.findViewById(R.id.home_date);
        name = view.findViewById(R.id.home_name);
        userState = view.findViewById(R.id.home_userState);
        startTime = view.findViewById(R.id.home_startTime);
        endTime = view.findViewById(R.id.home_endTime);
        workingTime = view.findViewById(R.id.home_workingTime);
        checkButton = view.findViewById(R.id.home_checkButton);
        checkButton.setOnClickListener(this);

        setDateView();
        checkWifiState(view);
        checkGpsState(view);
        checkCommuteState();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.home_wifiState:
                checkWifiState(v);
                break;
            case R.id.home_gpsState:
                checkGpsState(v);
                break;
            case R.id.home_checkButton:
                Toast.makeText(context, "출퇴근 버튼", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setDateView() {
        long currentTime = System.currentTimeMillis();
        Date mDate = new Date(currentTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String stringDate = simpleDateFormat.format(mDate);
        date.setText(stringDate);
    }

    private void checkWifiState(View view) {
        String wifiAp = WifiUtil.getAp(view.getContext());

        if(wifiAp != null) {
            isWifiConnected = true;
            wifiState.setImageResource(R.drawable.ic_wifi);
            wifiState.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }
        else {
            isWifiConnected = false;
            wifiState.setImageResource(R.drawable.ic_wifi_off);
            wifiState.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray)));
        }
    }

    private void checkGpsState(View view) {
        new GpsReceiver(view.getContext()) {
            @NonNull
            @Override
            protected void onReceive(Double latitude, Double longitude) {
                if(latitude != null && longitude != null){
                    isGpsConnected = true;
                    gpsState.setImageResource(R.drawable.ic_gps);
                    gpsState.setBackgroundResource(R.color.colorAccent);
                } else {
                    isGpsConnected = false;
                    gpsState.setImageResource(R.drawable.ic_gps_off);
                    gpsState.setBackgroundResource(R.color.colorGray);
                }
            }
        };
    }

    private void checkCommuteState() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.82.68.95:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(CommuteCheckAPI.class).checkCommute(token)
                .enqueue(new Callback<CommuteCheckResponse>() {
                    @Override
                    public void onResponse(Call<CommuteCheckResponse> call, Response<CommuteCheckResponse> response) {
                        if(response.code() == 200) {
                            CommuteCheckResponse body = response.body();
                            String code = body.getCode();
                            String userName = body.getName();
                            String clockIn = body.getClock_in();
                            String clockOut = body.getClock_out();
                            Log.d("FragmentHome", "출퇴근 상태 반환 성공");

                            name.setText(userName);
                            startTime.setText(clockIn);
                            endTime.setText(clockOut);

                            switch(code) {
                                case "csr0001":
                                    userState.setText("출근");
                                    commuteState = 1;
                                    break;
                                case "csr0002":
                                    userState.setText("퇴근");
                                    commuteState = 2;
                                    break;
                                case "csr0003":
                                    userState.setText("미출근");
                                    commuteState = 0;
                                    break;
                            }
                        }
                        else if(response.code() == 401) {
                            Log.d("FragmentHome", "토큰 검증 실패");
                            activity.finish();
                            Toast.makeText(context, "토큰이 만료되었거나 유효하지 않습니다. 다시 로그인 해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code() == 500) {
                            Log.d("FragmentHome", "서버 오류");
                            activity.finish();
                            Toast.makeText(context, "서버에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommuteCheckResponse> call, Throwable t) {
                        Log.d("FragmentHome", "API 연결 실패");
                        t.printStackTrace();
                    }
                });
    }

    private void commute() {
        String type = "";
        switch(commuteState) {
            case 0:
            case 2:
                type = "in";
                break;
            case 1:
                type = "out";
                break;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.82.68.95:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(CommuteAPI.class).commute(token, new CommuteRequest())
                .enqueue(new Callback<CommuteResponse>() {
                    @Override
                    public void onResponse(Call<CommuteResponse> call, Response<CommuteResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<CommuteResponse> call, Throwable t) {

                    }
                });
    }
}
