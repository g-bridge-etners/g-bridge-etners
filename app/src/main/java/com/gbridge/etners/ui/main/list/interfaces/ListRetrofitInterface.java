package com.gbridge.etners.ui.main.list.interfaces;


import com.gbridge.etners.ui.main.list.models.ListResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ListRetrofitInterface {

    @GET("/commute/history")
    Call<ListResponse> getList();
}
