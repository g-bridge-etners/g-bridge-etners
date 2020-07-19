package com.gbridge.etners.ui.login;


import android.util.Log;

import com.gbridge.etners.ui.login.interfaces.LogInActivityView;
import com.gbridge.etners.ui.login.interfaces.LogInRetrofitInterface;
import com.gbridge.etners.ui.login.models.LogInBody;
import com.gbridge.etners.ui.login.models.LogInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gbridge.etners.ApplicationClass.getRetrofit;


class LogInService {
    private final LogInActivityView mLogInActivityView;

    LogInService(final LogInActivityView loginActivityView) {
        this.mLogInActivityView = loginActivityView;
    }


    void postLogIn(String employeeNumber, String password) {
        final LogInRetrofitInterface loginRetrofitInterface = getRetrofit().create(LogInRetrofitInterface.class);
        loginRetrofitInterface.LogInTest(new LogInBody(employeeNumber, password)).enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                final LogInResponse logInResponse = response.body();
//                if (logInResponse == null) {
//                    mLogInActivityView.validateFailure(null);
//                    return;
//                }
                if(response.code() == 200){
                    Log.d("test", "로그인성공");
                    mLogInActivityView.logInSuccess(logInResponse.getMessage(), logInResponse.getToken());
                }else if(response.code() == 400){
                    Log.d("test", "잘못된로그인정보");
                    mLogInActivityView.validateFailure(null);
                }else if(response.code() == 403){
                    Log.d("test","비밀번호 틀림");
                    mLogInActivityView.validateFailure(null);
                }else if(response.code() == 404){
                    Log.d("test","서버에 없는 아이디");
                    mLogInActivityView.validateFailure(null);
                }else if(response.code() == 415){
                    Log.d("test","잘못된 요청타입");
                    mLogInActivityView.validateFailure(null);
                }else{
                    Log.d("test","서버 오류");
                    mLogInActivityView.validateFailure(null);
                }

        }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                mLogInActivityView.validateFailure(null);
            }
        });
    }
}
