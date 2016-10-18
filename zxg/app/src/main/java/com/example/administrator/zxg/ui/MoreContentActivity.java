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
 * 更多内容
 * Created by Administrator on 2016/9/18.
 */
public class MoreContentActivity extends CommonActivity {
    private RecyclerView rv_content, rv_moreContent;

    private HomeAdapter mHomeAdapter,m1;
    private List<String> mList;
    private List<String> mList1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_content);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);//我的内容
        rv_moreContent = (RecyclerView) findViewById(R.id.rv_moreContent);//更多的内容

        initData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
//        rv_content.setLayoutManager(gridLayoutManager);
//        mHomeAdapter = new HomeAdapter(this, mList);
//        rv_content.setHasFixedSize(true);
//        rv_content.setAdapter(mHomeAdapter);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 4);
        rv_moreContent.setLayoutManager(gridLayoutManager1);
        m1 = new HomeAdapter(this, mList1);
        rv_moreContent.setHasFixedSize(true);
        rv_moreContent.setAdapter(m1);


    }

    private void initData() {

        mList = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mList.add("" + (char) i);
        }
        mList1 = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            mList1.add(i+1+"");
        }
    }
}
