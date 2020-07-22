package com.gbridge.etners.ui.main.list.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("history")
    private ArrayList<ListHistory> listHistory;


    public class ListHistory {

        @SerializedName("status")
        private String status;

        @SerializedName("date")
        private String date;

        @SerializedName("clockInTime")
        private String clockInTime;

        @SerializedName("clockOutTime")
        private String clockOutTime;

        public String getStatus() {
            return status;
        }

        public String getDate() {
            return date;
        }

        public String getClockInTime() {
            return clockInTime;
        }

        public String getClockOutTime() {
            return clockOutTime;
        }
    }


    public String getMessage() {
        return message;
    }

    public ArrayList<ListHistory> getListHistory() {
        return listHistory;
    }

}