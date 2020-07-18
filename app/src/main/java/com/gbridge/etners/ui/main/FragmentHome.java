package com.gbridge.etners.ui.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentHome extends Fragment implements View.OnClickListener {
    private Context context;
    private boolean isWifiConnected = false;
    private boolean isGpsConnected = false;

    private LinearLayout stateLayout;
    private ImageView wifiState, gpsState;
    private TextView date, name, userState, startTime, endTime, workingTime;
    private Button checkButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

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
}
