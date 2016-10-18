package com.example.administrator.zxg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;

/**
 * Created by Administratlor on 2016/8/30.
 */
public class LayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
    }

    public void upLoadImage(View view) {

        Intent intent = new Intent(LayoutActivity.this, SelectPicActivity.class);
        startActivity(intent);

    }

    /**
     * 点击查看图片
     *
     * @param view
     */
    public void clickimage(View view) {
        Intent intent = new Intent(LayoutActivity.this, ImageActivity.class);
        startActivity(intent);
    }

    //recycle
    public void recycle(View view) {
        Intent intent = new Intent(LayoutActivity.this, StaggeredGridLayoutActivity.class);
        startActivity(intent);
    }

    //gridviewlayout
    public void gridlayout(View view) {
        Intent intent = new Intent(LayoutActivity.this, GridLayoutActivity.class);
        startActivity(intent);

    }

    public void staggeral(View view) {
        Intent intent = new Intent(LayoutActivity.this, StaggeredActivity.class);
        startActivity(intent);

    }

    public void glideimage(View view) {
        Intent intent = new Intent(LayoutActivity.this, GlideActivity.class);
        startActivity(intent);

    }

    public void morecontent(View view) {
        Intent intent = new Intent(LayoutActivity.this, MoreContentActivity.class);
        startActivity(intent);

    }

}
