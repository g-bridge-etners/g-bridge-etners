package com.gbridge.etners.ui.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gbridge.etners.R;
import com.gbridge.etners.util.GpsReceiver;
import com.gbridge.etners.util.WifiUtil;
import com.gbridge.etners.util.retrofit.admin.AdminService;
import com.gbridge.etners.util.retrofit.admin.RequestPostLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.hmomeni.progresscircula.ProgressCircula;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyLocationManagementFragment extends Fragment implements View.OnClickListener {

    private ImageButton imgBtnRefreshGps;
    private ImageButton imgBtnRefreshWifi;
    private Button btnSubmitGps;
    private Button btnSubmitWifi;
    private TextView tvWifiName;
    private TextView tvWifiIp;
    private SupportMapFragment mapFragment;
    private double currentLatitude;
    private double currentLongitude;
    private String currentAp;
    private Retrofit retrofit;
    private AdminService adminService;
    private String token;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            new GpsReceiver(getContext()) {
                @NonNull
                @Override
                protected void onReceive(Double latitude, Double longitude) {
                    LatLng current = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(current).title("현재 위치"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                    currentLatitude = latitude;
                    currentLongitude = longitude;
                }
            };

        }
    };

    public CompanyLocationManagementFragment() {
    }

    public static CompanyLocationManagementFragment newInstance() {
        CompanyLocationManagementFragment fragment = new CompanyLocationManagementFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_location_management, container, false);

        imgBtnRefreshWifi = (ImageButton) view.findViewById(R.id.img_btn_company_location_refresh_wifi);
        imgBtnRefreshGps = (ImageButton) view.findViewById(R.id.img_btn_company_location_refresh_gps);
        btnSubmitWifi = (Button) view.findViewById(R.id.btn_company_location_submit_wifi);
        btnSubmitGps = (Button) view.findViewById(R.id.btn_company_location_submit_gps);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_company_location_management);
        tvWifiIp = (TextView) view.findViewById(R.id.tv_company_location_wifi_ip);
        tvWifiName = (TextView) view.findViewById(R.id.tv_company_location_wifi_name);

        btnSubmitGps.setOnClickListener(this);
        btnSubmitWifi.setOnClickListener(this);
        imgBtnRefreshGps.setOnClickListener(this);
        imgBtnRefreshWifi.setOnClickListener(this);

        token = ((AdminActivity) getActivity()).getToken();

        initActionBar();
        initRetrofit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshGps();
        refreshWifi();
    }

    private void refreshGps() {
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void refreshWifi() {
        currentAp = WifiUtil.getAp(getActivity());
        String name = WifiUtil.getSsid(getActivity());
        String ip = WifiUtil.getIp(getActivity());

        if (currentAp != null) {
            tvWifiName.setText(name);
            tvWifiIp.setText(ip);
        } else {
            tvWifiName.setText("연결 없음");
            tvWifiIp.setText("연결 없음");
        }
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AdminActivity adminActivity = (AdminActivity) getActivity();
        ActionBar ahActionBar = adminActivity.getSupportActionBar();
        ahActionBar.setTitle(R.string.admin_company_location_management);
        ahActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://34.82.68.95:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        adminService = retrofit.create(AdminService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_company_location_submit_gps:
                adminService.postLocation(token, new RequestPostLocation("gps", currentLatitude, currentLongitude, null)).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("test1", Integer.toString(response.code()));
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "등록성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "등록실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("test1", t.getMessage());
                        Toast.makeText(getContext(), "등록실패", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_company_location_submit_wifi:
                adminService.postLocation(token, new RequestPostLocation("wifi", null, null, currentAp)).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "등록성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "등록실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "등록실패", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.img_btn_company_location_refresh_gps:
                refreshGps();
                break;
            case R.id.img_btn_company_location_refresh_wifi:
                refreshWifi();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((AdminActivity) getActivity()).removeAndPop(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}