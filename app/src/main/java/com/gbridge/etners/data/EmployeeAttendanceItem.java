package com.gbridge.etners.data;

public class EmployeeAttendanceItem {

    private String name;
    private String department;
    private String employeeNumber;
    private String clockInTime;
    private String clockOutTime;
    private String startDate;
    private String endDate;


    public EmployeeAttendanceItem(String name, String department, String employeeNumber, String clockInTime, String clockOutTime, String startDate, String endDate) {
        this.name = name;
        this.department = department;
        this.employeeNumber = employeeNumber;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
