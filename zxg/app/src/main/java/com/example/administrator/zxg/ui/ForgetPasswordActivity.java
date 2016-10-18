package com.example.administrator.zxg.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;

/**
 * Created by Administrator on 2016/10/5.
 */
public class ForgetPasswordActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void submit(View view){
        finish();
        Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show();
    }
}
