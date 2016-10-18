package com.example.administrator.zxg.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.zxg.MainActivity;
import com.example.administrator.zxg.R;
import com.example.administrator.zxg.adapter.HomeAdapter;
import com.example.administrator.zxg.adapter.StaggeredHomeAdapter;
import com.example.administrator.zxg.common.uitl.CommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
public class StaggeredActivity extends CommonActivity {
    private RecyclerView staggered;
    private List<String> mData;
    private StaggeredHomeAdapter mHomeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_staggered);
        initDate();
        staggered = (RecyclerView) findViewById(R.id.rv_staggered);
        staggered.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggered.setLayoutManager(staggeredGridLayoutManager);
        mHomeAdapter = new StaggeredHomeAdapter(StaggeredActivity.this, mData);
        staggered.setAdapter(mHomeAdapter);


    }

    private void initDate() {
        mData = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mData.add("" + (char) i);
        }
    }
}
