package com.gbridge.etners.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gbridge.etners.R;

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
                if(checkInputs()) {
                    makeToast(getString(R.string.signup_toast_success));
                    finish();
                }
                else {
                    edtId.setText("");
                    edtPw.setText("");
                    edtPwCheck.setText("");
                    edtName.setText("");
                }
                break;
        }
    }

    private boolean checkInputs() {
        String id = edtId.getText().toString();
        String pw = edtPw.getText().toString();
        String check = edtPwCheck.getText().toString();
        String name = edtName.getText().toString();

        if(id.length() < 9) {
            makeToast(getString(R.string.signup_toast_idLengthFail));
            return false;
        }

        if(!pw.equals(check)) {
            makeToast(getString(R.string.signup_toast_pwCheckFail));
            return false;
        }

        if(pw.length() < 6) {
            makeToast(getString(R.string.signup_toast_pwLengthFail));
            return false;
        }

        return true;
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
