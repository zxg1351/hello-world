package com.example.administrator.zxg.ui;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.provider.DocumentsContract.getDocumentId;
import static android.provider.DocumentsContract.isDocumentUri;

/**
 * Created by Administrator on 2016/10/4.
 */
public class SelectPicActivity extends CommonActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO =2;
    public static final int CHOOSE_PHOTO =3;
    private Button takephoto;
    private ImageView imageview;
    private Button choose_from_album;
    private Uri imageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);
        takephoto =(Button) findViewById(R.id.btn_take_photo);
        imageview =(ImageView) findViewById(R.id.iv_imageview);
        choose_from_album = (Button) findViewById(R.id.choose_from_album);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //創建File對象，用戶存儲拍照后的圖片
                File outputImage = new File(Environment.getExternalStorageDirectory(),"output_image.jpg");

                try {
                    if (outputImage.exists()){//如果文件存在，擇刪除該圖片文件
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);

            }
        });

        choose_from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);//打開相冊
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode  == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO);//啟動裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK){
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageview.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判斷手機系統版本號
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4以上系統使用這個方法處理圖片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系統使用這個方法處理圖片
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {

        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {


        if (imagePath !=null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageview.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通過URI 和selection 來獲取真實的圖片路徑
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (null != cursor){

            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();

        }
        return path;
    }

    private void handleImageOnKitKat(Intent data) {


        String imagePath = null;
        Uri uri= data.getData();
        if (isDocumentUri(this,uri)){
            //如果是document類型的uri 則通過document id處理
            String docId = getDocumentId(uri);
            if ("com.android.provides.media.documents".equals(uri.getAuthority())){
                String id  = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else  if("com.android.provides.downloads.documents".equals(uri.getAuthority())){

            Uri contentUri = ContentUris.withAppendedId
                    (Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
               imagePath = getImagePath(contentUri,null);


            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果不是document類型的uri則使用普通方式處理
            imagePath = getImagePath(uri,null);
        }

        displayImage(imagePath);
    }



}
