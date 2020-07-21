package com.gbridge.etners.util.retrofit.register;

public class RegisterRequest {
    private String employeeNumber;
    private String password;
    private String department;
    private String name;

    public RegisterRequest(String employeeNumber, String password, String department, String name) {
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}