package com.gbridge.etners.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gbridge.etners.R;
import com.gbridge.etners.util.retrofit.RegisterAPI;
import com.gbridge.etners.util.retrofit.RegisterRequest;
import com.gbridge.etners.util.retrofit.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    EditText edtId, edtPw, edtPwCheck, edtName;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.signup_buttonText));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edtId = findViewById(R.id.signup_id);
        edtPw = findViewById(R.id.signup_pw);
        edtPwCheck = findViewById(R.id.signup_check);
        edtName = findViewById(R.id.signup_name);

        btnSignup = findViewById(R.id.signup_signupButton);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.signup_signupButton:
                checkAndSignup();
                break;
        }
    }

    private void checkAndSignup() {
        String id = edtId.getText().toString();
        String pw = edtPw.getText().toString();
        String check = edtPwCheck.getText().toString();
        String name = edtName.getText().toString();

        if(id.length() < 9) {
            signupFail("알맞은 사원번호 형식이 아닙니다.");
            return;
        }

        if(!pw.equals(check)) {
            signupFail("비밀번호와 비밀번호 재확인이 서로 다릅니다.");
            return;
        }

        if(pw.length() < 6) {
            signupFail("비밀번호는 6자리 이상이여야 합니다.");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.82.68.95:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(RegisterAPI.class).register(new RegisterRequest(id, pw, name))
        .enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.code() == 201) {
                    Log.d("Test", "회원가입 성공.");
                    signupSuccess();
                }
                else if(response.code() == 400) {
                    Log.d("Test", "잘못된 회원정보입니다.");
                    signupFail(response.body().getMessage());
                }
                else if(response.code() == 409) {
                    Log.d("Test", "이미 존재하는 사원번호입니다.");
                    signupFail(response.body().getMessage());
                }
                else if(response.code() == 415) {
                    Log.d("Test", "잘못된 요청타입입니다.");
                    signupFail(response.body().getMessage());
                }
                else if(response.code() == 500) {
                    Log.d("Test", "서버에 오류가 발생했습니다.");
                    signupFail(response.body().getMessage());
                }
                else {
                    Log.d("Test", "알수 없는 오류입니다.");
                    signupFail(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("Test", "API 연결에 실패했습니다.");
                t.printStackTrace();
                signupFail("서버와의 연결에 실패했습니다.");
            }
        });
    }

    private void signupFail(String message) {
        edtId.setText("");
        edtPw.setText("");
        edtPwCheck.setText("");
        edtName.setText("");
        makeToast(message);
    }

    private void signupSuccess() {
        makeToast("회원가입이 완료되었습니다. 다시 로그인 해 주세요.");
        finish();
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
