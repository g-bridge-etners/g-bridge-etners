package com.gbridge.etners.ui.login.models;

import com.google.gson.annotations.SerializedName;

public class LogInBody {

    @SerializedName("employeeNumber")
    private String employeeNumber;

    @SerializedName("password")
    private String password;

    public LogInBody(String employeeNumber, String password) {
        this.employeeNumber = employeeNumber;
        this.password = password;
    }

}
