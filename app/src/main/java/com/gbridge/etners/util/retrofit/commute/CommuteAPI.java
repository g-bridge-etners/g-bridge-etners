package com.gbridge.etners.util.retrofit.commute;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface CommuteAPI {
    @PATCH("commute/check")
    Call<CommuteResponse> commute(
            @Header("x-access-token") String token,
            @Body CommuteRequest body
    );
}
