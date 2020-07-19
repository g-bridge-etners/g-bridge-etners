package com.gbridge.etners.util.retrofit.commute_check;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CommuteCheckAPI {
    @GET("commute/status")
    Call<CommuteCheckResponse> checkCommute(@Header("x-access-token") String token);
}
