package com.gbridge.etners.util.retrofit.admin;

public class RequestPostLocation {
    private String method;
    private Double latitude;
    private Double longitude;
    private String ap;

    public RequestPostLocation(String method, Double latitude, Double longitude, String ap) {
        this.method = method;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ap = ap;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }
}
