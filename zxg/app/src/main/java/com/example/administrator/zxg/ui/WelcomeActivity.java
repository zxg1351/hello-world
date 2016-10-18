package com.example.administrator.zxg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;

/**
 * Created by Administrator on 2016/9/25.
 */
public class WelcomeActivity extends CommonActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onWelcome(View view){

        Intent intent = new Intent(WelcomeActivity.this,StartActivity.class);
        startActivity(intent);
    }
}
