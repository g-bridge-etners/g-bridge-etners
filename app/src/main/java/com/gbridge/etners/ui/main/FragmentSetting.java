package com.gbridge.etners.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.admin.AdminActivity;

public class FragmentSetting extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Activity activity;
    private Context context;

    private Button admin, logout;
    private RadioGroup methodGroup;
    private RadioButton methodWifi, methodGps;

    public FragmentSetting(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        admin = view.findViewById(R.id.setting_admin);
        admin.setOnClickListener(this);
        logout = view.findViewById(R.id.setting_logout);
        logout.setOnClickListener(this);
        methodGroup = view.findViewById(R.id.setting_methodGroup);
        methodGroup.setOnCheckedChangeListener(this);
        methodWifi = view.findViewById(R.id.setting_methodWifi);
        methodGps = view.findViewById(R.id.setting_methodGps);

        SharedPreferences sf = context.getSharedPreferences("method", Context.MODE_PRIVATE);
        String currentMethod = sf.getString("method", "gps");
        if(currentMethod == "gps") {
            methodGps.setChecked(true);
            methodWifi.setChecked(false);
        }
        else {
            methodGps.setChecked(false);
            methodWifi.setChecked(true);
        }

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sf = context.getSharedPreferences("method", Context.MODE_PRIVATE);
        String currentMethod = sf.getString("method", "gps");
        if(currentMethod == "gps") {
            methodGps.setChecked(true);
            methodWifi.setChecked(false);
        }
        else {
            methodGps.setChecked(false);
            methodWifi.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.setting_admin:
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_logout:
                Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                activity.finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SharedPreferences sf = context.getSharedPreferences("method", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();

        switch(checkedId) {
            case R.id.setting_methodWifi:
                editor.putString("method", "wifi");
                editor.commit();
                break;
            case R.id.setting_methodGps:
                editor.putString("method", "gps");
                editor.commit();
                break;
        }
    }
}
