package com.gbridge.etners.data;

public class DailyAttendanceReportItem {
    String name;
    String department;
    String employeeNumber;
    String status;
    String startTime;
    String endTime;
    String clockInTime;
    String clockOutTime;

    public DailyAttendanceReportItem(String name, String department, String employeeNumber, String status, String startTime, String endTime, String clockInTime, String clockOutTime) {
        this.name = name;
        this.department = department;
        this.employeeNumber = employeeNumber;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
}
