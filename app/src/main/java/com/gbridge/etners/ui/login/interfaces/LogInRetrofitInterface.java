package com.gbridge.etners.ui.login.interfaces;



import com.gbridge.etners.ui.login.models.LogInBody;
import com.gbridge.etners.ui.login.models.LogInResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LogInRetrofitInterface {

    @POST("/auth/login")
    Call<LogInResponse> LogInTest(@Body LogInBody params);

}
