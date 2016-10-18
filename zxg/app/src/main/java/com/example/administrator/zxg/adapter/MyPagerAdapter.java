package com.example.administrator.zxg.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViewList;

    public MyPagerAdapter(List<View> viewList) {
        mViewList = viewList;
    }
    /* 更新视图*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
    /* 销毁某个元素时候调用*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {



        return view == object;
    }
}
