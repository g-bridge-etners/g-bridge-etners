package com.gbridge.etners.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.gbridge.etners.BaseActivity;
import com.gbridge.etners.R;
import com.gbridge.etners.ui.login.interfaces.LogInActivityView;
import com.gbridge.etners.ui.main.MainActivity;
import com.gbridge.etners.ui.signup.SignupActivity;

import static com.gbridge.etners.ApplicationClass.X_ACCESS_TOKEN;
import static com.gbridge.etners.ApplicationClass.sSharedPreferences;

public class LogInActivity extends BaseActivity implements LogInActivityView {

    EditText mEtId, mEtPw;
    TextView mTvBtnLogin;
    Button btnSignup;

    String id, pw;
    Boolean bId, bPw;

    private LogInService logInService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInService = new LogInService(this);

        if (sSharedPreferences == null) {
            sSharedPreferences = getApplicationContext().getSharedPreferences("key", Context.MODE_PRIVATE);
        }

        //toolbar 설정
        Toolbar mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();

        mEtId = findViewById(R.id.login_et_id);
        mEtPw = findViewById(R.id.login_et_pw);
        mTvBtnLogin = findViewById(R.id.login_tv_login_btn);
        btnSignup = findViewById(R.id.login_signupButton);
    }

    private boolean checkValid(){
        id = mEtId.getText().toString();
        pw = mEtPw.getText().toString();

        if(id.length() < 9){
            return false;
        }
        if(pw.length() < 6){
            return false;
        }
        return true;
    }

    private void tryPostLogIn(){
        showProgressDialog();
        //logInService.postLogIn("200000000","000000");
        logInService.postLogIn(id,pw);
    }

    @Override
    public void validateSuccess(String text) {
        hideProgressDialog();
    }

    @Override
    public void validateFailure(@Nullable String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }



    @Override
    public void logInSuccess(String message ,String token) {
        hideProgressDialog();
        if(token != null) {
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.putString(X_ACCESS_TOKEN,token);
            editor.commit();
            Intent resultIntent = new Intent(this, MainActivity.class);
            resultIntent.putExtra("id", mEtId.getText().toString());
            resultIntent.putExtra("token",X_ACCESS_TOKEN);
            setResult(1, resultIntent);
            startActivity(resultIntent);
            showCustomToast(mEtId.getText().toString() + "로그인성공");
        }else{
            showCustomToast(message);
        }

    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv_login_btn:
                if(checkValid()) {
                    tryPostLogIn();
                }else{
                    showCustomToast("사원번호와 비밀번호를 확인하세요");
                }
                break;
            case R.id.login_signupButton:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
