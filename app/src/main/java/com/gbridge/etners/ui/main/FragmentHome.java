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
    private int commuteState = 3;   //1:출근, 2:퇴근, 3: 미출근
    private Double lat;
    private Double lon;

    private LinearLayout stateLayout;
    private ImageView wifiState, gpsState;
    private TextView date, name, userState, startTime, endTime;
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

        wifiState = view.findViewById(R.id.home_wifiState);
        wifiState.setOnClickListener(this);
        gpsState = view.findViewById(R.id.home_gpsState);
        gpsState.setOnClickListener(this);
        date = view.findViewById(R.id.home_date);
        name = view.findViewById(R.id.home_name);
        userState = view.findViewById(R.id.home_userState);
        startTime = view.findViewById(R.id.home_startTime);
        endTime = view.findViewById(R.id.home_endTime);
        checkButton = view.findViewById(R.id.home_checkButton);
        checkButton.setOnClickListener(this);

        setDateView();
        checkCommuteState();
        checkWifiState(view);
        checkGpsState(view);


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
                commute(v);
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
        String ap = WifiUtil.getAp(context);

        if(ap != null) {
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
                    lat = latitude;
                    lon = longitude;
                    gpsState.setImageResource(R.drawable.ic_gps);
                    gpsState.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                } else {
                    isGpsConnected = false;
                    gpsState.setImageResource(R.drawable.ic_gps_off);
                    gpsState.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray)));
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
                            if(clockIn == null) {
                                startTime.setText("기록 없음");
                            }
                            else {
                                startTime.setText(clockIn);
                            }
                            if(clockOut == null) {
                                endTime.setText("기록 없음");
                            }
                            else {
                                endTime.setText(clockOut);
                            }

                            switch(code) {
                                case "csr0001":
                                    userState.setText("출근");
                                    checkButton.setText("퇴근하기");
                                    commuteState = 1;
                                    break;
                                case "csr0002":
                                    userState.setText("퇴근");
                                    checkButton.setText("출근하기");
                                    commuteState = 2;
                                    break;
                                case "csr0003":
                                    userState.setText("미출근");
                                    checkButton.setText("출근하기");
                                    commuteState = 3;
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
                        Log.d("FragmentHome", "출퇴근 상태 체크 API 연결 실패");
                        t.printStackTrace();
                    }
                });
    }

    private void commute(View view) {
        String type = "";
        switch(commuteState) {
            case 1:
                type = "out";
                break;
            case 2:
            case 3:
                type = "in";
                break;

        }

        if(isGpsConnected) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://34.82.68.95:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofit.create(CommuteAPI.class).commute(token, new CommuteRequest("gps", type, lat, lon, ""))
                    .enqueue(new Callback<CommuteResponse>() {
                        @Override
                        public void onResponse(Call<CommuteResponse> call, Response<CommuteResponse> response) {
                            if(response.code() == 200) {
                                Log.d("FragmentHome", "성공");
                                switch(commuteState) {
                                    case 1:
                                        Log.d("FragmentHome", "퇴근 완료");
                                        break;
                                    case 2:
                                    case 3:
                                        Log.d("FragmentHome", "출근 완료");
                                        break;

                                }
                                checkCommuteState();
                            }
                            else if(response.code() == 400) {
                                Log.d("FragmentHome", "전달인자 오류");
                            }
                            else if(response.code() == 401) {
                                Log.d("FragmentHome", "토큰 검증 실패");
                            }
                            else if(response.code() == 406) {
                                Log.d("FragmentHome", "위치가 회사가 아님");
                            }
                            else if(response.code() == 415) {
                                Log.d("FragmentHome", "잘못된 요청타입");
                            }
                            else if(response.code() == 418) {
                                Log.d("FragmentHome", "출퇴근 상태 체크 오류");
                            }
                            else if(response.code() == 500) {
                                Log.d("FragmentHome", "서버 오류");
                            }
                        }

                        @Override
                        public void onFailure(Call<CommuteResponse> call, Throwable t) {
                            Log.d("FragmentHome", "출퇴근 API 연결 실패");
                            t.printStackTrace();
                        }
                    });
        }
        else {
            Toast.makeText(context, "GPS가 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            checkGpsState(view);
        }

    }
}
