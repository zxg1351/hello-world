package com.example.administrator.zxg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.zxg.MainActivity;
import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;

/**
 * Created by Administrator on 2016/10/5.
 */
public class LoginActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * 登录按钮
     * @param view
     */
    public void login(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void forgetPassword(View view){
        Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }
}
