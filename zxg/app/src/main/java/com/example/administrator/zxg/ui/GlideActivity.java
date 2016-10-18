package com.example.administrator.zxg.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;

import java.io.File;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GlideActivity extends CommonActivity {
    private ImageView imageView, image3, image4, image5, image6, image7, image8, image9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        imageView = (ImageView) findViewById(R.id.iv_1);
        image5 = (ImageView) findViewById(R.id.iv_5);
        image3 = (ImageView) findViewById(R.id.iv_3);
        image4 = (ImageView) findViewById(R.id.iv_4);
        image6 = (ImageView) findViewById(R.id.iv_6);
        image7 = (ImageView) findViewById(R.id.iv_7);


//        int source = R.drawable.p;
        Glide.with(this).load(R.drawable.pic1).asBitmap().into(imageView);
        String url = "http://www.qq745.com/uploads/allimg/141106/1-141106153Q5.png";
        Glide.with(this).load(url).into(image5);


        File file = new File(Environment.getExternalStorageDirectory(), "longzhu_icon.png");
        Glide.with(this).load(file).asBitmap().into(image7);
        //设置默认图片和加载失败时显示的图片
        Glide.with(this).load(file).asBitmap().placeholder(R.drawable.pic1).into(image6);

//        淡入显示效果
        Glide.with(this).load(file).placeholder(R.drawable.pic1).
                error(R.mipmap.ic_launcher).crossFade().into(image6);
        Glide.with(this).load(file).placeholder(R.drawable.pic1).
                error(R.mipmap.ic_launcher).crossFade(1000).//淡入顯示時間
                override(80,80).//最後變成80固定的橡樹值
                into(image4);

//设置缩略图有2种方式:
      //  通过thumbnail(float)指定0.0f~1.0f的原始图像大小,例如全像素的大小是500*500,如果设置为thumbnail为0.1f,即目标图片的10%,显示的缩略图大小就是50*50;
        Glide.with(this).
                load(R.drawable.pic1).
                placeholder(R.drawable.pic1).//加载中显示的图片
                error(R.drawable.pic1).//加载失败时显示的图片
                crossFade(1000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
                override(80, 80).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                centerCrop().//中心裁剪,缩放填充至整个ImageView
                skipMemoryCache(true).//跳过内存缓存
                diskCacheStrategy(DiskCacheStrategy.RESULT).//保存最终图片
                thumbnail(0.1f).//10%的原图大小
                into(image3);
    }
}
