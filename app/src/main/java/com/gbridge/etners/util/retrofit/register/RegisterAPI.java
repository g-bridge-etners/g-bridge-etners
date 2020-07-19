package com.gbridge.etners.util.retrofit.register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterAPI {
    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest body);
}
