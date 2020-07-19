package com.gbridge.etners.util.retrofit.commute;

public class CommuteRequest {
    private String method;
    private String type;
    private Double latitude;
    private Double longitude;
    private String ap;

    public CommuteRequest(String method, String type, Double latitude, Double longitude, String ap) {
        this.method = method;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
