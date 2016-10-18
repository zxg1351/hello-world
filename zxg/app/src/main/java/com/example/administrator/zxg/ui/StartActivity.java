package com.example.administrator.zxg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.zxg.MainActivity;
import com.example.administrator.zxg.R;
import com.example.administrator.zxg.adapter.MyPagerAdapter;
import com.example.administrator.zxg.common.uitl.CommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class StartActivity extends CommonActivity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private List<View> viewList = new ArrayList<View>();
    private MyPagerAdapter myAdapter;
    private ImageView[] indicationPoint;/*指示点控件*/
    private int[] points = {R.id.point1,R.id.point2,R.id.point3};
    private Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initData();
        initView();
    }

    private void initData() {

        LayoutInflater layoutinflate = LayoutInflater.from(this);
        viewList.add(layoutinflate.inflate(R.layout.start1_layout,null));
        viewList.add(layoutinflate.inflate(R.layout.start2_layout,null));
        viewList.add(layoutinflate.inflate(R.layout.start3_layout,null));

        indicationPoint = new ImageView[viewList.size()];

        //实例化相关控件
        for (int i = 0; i < viewList.size(); i++) {
            indicationPoint[i]= (ImageView) findViewById(points[i]);

        }


    }

    private void initView() {

            myAdapter = new MyPagerAdapter(viewList);
//            mViewPager = (ViewPager) this.findViewById(R.id.mViewPager);
            mViewPager.setAdapter(myAdapter);
//            mViewPager.setPageTransformer(true, new DepthPageTransformer());
            mViewPager.setOffscreenPageLimit(viewList.size());
            mViewPager.setOnPageChangeListener(this);
            btnStart = (Button) viewList.get(viewList.size()-1).findViewById(R.id.start);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < points.length; i++) {
            if (position==i){
                indicationPoint[i].setImageResource(R.drawable.page_indicator_focused);
            }else {
                indicationPoint[i].setImageResource(R.drawable.page_indicator_unfocused);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
