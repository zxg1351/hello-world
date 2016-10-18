package com.example.administrator.zxg.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.zxg.R;
import com.example.administrator.zxg.adapter.HomeAdapter;
import com.example.administrator.zxg.common.uitl.CommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
public class GridLayoutActivity extends CommonActivity {

    private HomeAdapter mHomeAdapter;
    private List<String> mData;
    private RecyclerView rv_grid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);
        rv_grid = (RecyclerView) findViewById(R.id.rv_grid);
        initData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rv_grid.setLayoutManager(gridLayoutManager);
        rv_grid.setHasFixedSize(true);
        mHomeAdapter = new HomeAdapter(this,mData);
        rv_grid.setAdapter(mHomeAdapter);

    }

    private void initData(){
        mData = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mData.add("" + (char) i);
        }

    }
}
