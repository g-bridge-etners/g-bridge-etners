package com.gbridge.etners.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.admin.AdminActivity;

public class FragmentSetting extends Fragment implements View.OnClickListener {
    private Activity activity;
    private Context context;

    private Button admin, logout;

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

        return  view;
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
}
