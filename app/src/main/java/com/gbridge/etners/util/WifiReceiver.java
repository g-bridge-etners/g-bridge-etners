package com.gbridge.etners.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;

import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.util.List;


/*
    필요 퍼미션
    ACCESS_WIFI_STATE, CHANGE_WIFI_STATE

    성공 : JSONArray aps
    실패 : empty JSONArray aps

    example
    new WifiReciever(Activity.Context){
            @NonNull
            @Override
            protected void onReceive(JSONArray aps) {
                작업
            }
        };
 */
public abstract class WifiReceiver {
    private Context context;

    public WifiReceiver(@NonNull final Context context) {
        this.context = context.getApplicationContext();

        final WifiManager wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)) {
                    JSONArray aps = new JSONArray();
                    List<ScanResult> results = wifiManager.getScanResults();
                    for (ScanResult result : results) {
                        aps.put(result.SSID + result.BSSID);
                    }
                    WifiReceiver.this.onReceive(aps);
                    try {
                        context.unregisterReceiver(this);
                    } catch (Exception e) {
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(broadcastReceiver, intentFilter);
        wifiManager.startScan();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    context.unregisterReceiver(broadcastReceiver);
                    WifiReceiver.this.onReceive(new JSONArray());
                } catch (Exception e) {
                }
            }
        }, 4000);
    }


    @NonNull
    protected abstract void onReceive(JSONArray aps);
}
