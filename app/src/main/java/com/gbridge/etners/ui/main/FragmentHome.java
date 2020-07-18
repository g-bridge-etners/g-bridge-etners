package com.gbridge.etners.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gbridge.etners.R;
import com.gbridge.etners.util.GpsReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentHome extends Fragment implements View.OnClickListener {
    Context context;

    ImageView networkState;
    TextView date, name, userState, startTime, endTime, workingTime;
    Button checkButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        networkState = view.findViewById(R.id.home_networkState);
        networkState.setOnClickListener(this);
        date = view.findViewById(R.id.home_date);
        name = view.findViewById(R.id.home_name);
        userState = view.findViewById(R.id.home_userState);
        startTime = view.findViewById(R.id.home_startTime);
        endTime = view.findViewById(R.id.home_endTime);
        workingTime = view.findViewById(R.id.home_workingTime);
        checkButton = view.findViewById(R.id.home_checkButton);
        checkButton.setOnClickListener(this);

        setDateView();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.home_networkState:
                Toast.makeText(context, "출퇴근 가능 상태 표시", Toast.LENGTH_SHORT).show();
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

    private void checkNetworkState(View view) {
        new GpsReceiver(view.getContext()) {
            @NonNull
            @Override
            protected void onReceive(Double latitude, Double longitude) {
                if(latitude != null && longitude != null){

                } else {

                }
            }
        }
    }
}
