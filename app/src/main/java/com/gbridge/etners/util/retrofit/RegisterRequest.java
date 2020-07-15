package com.gbridge.etners.util.retrofit;

public class RegisterRequest {
    private String employeeNumber;
    private String password;
    private String name;

    public RegisterRequest(String employeeNumber, String password, String name) {
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.name = name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}