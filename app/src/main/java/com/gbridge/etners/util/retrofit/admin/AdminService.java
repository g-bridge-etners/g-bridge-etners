package com.gbridge.etners.util.retrofit.admin;

import com.gbridge.etners.data.EmployeeAttendanceItem;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminService {

    @GET("/admin/report/daily/{date}")
    Call<JsonObject> getDailyReport(@Header("x-access-token") String token, @Path("date") String date);

    @GET("/admin/attendances")
    Call<JsonObject> getAttendances(@Header("x-access-token") String token);

    @PUT("/admin/attendance")
    Call<JsonObject> putAttendance(@Header("x-access-token") String token, @Body EmployeeAttendanceItem body);

    @POST("/admin/location")
    Call<JsonObject> postLocation(@Header("x-access-token") String token, @Body RequestPostLocation body);
}
