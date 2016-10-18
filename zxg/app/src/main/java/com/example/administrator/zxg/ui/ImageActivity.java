package com.example.administrator.zxg.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.zxg.MainActivity;
import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.BitmapUtil;
import com.example.administrator.zxg.common.uitl.CommonActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ImageActivity extends CommonActivity {

    private static final int TAKE_PICTURE = 0;
    private static final int CHOOSE_PICTURE = 1;
    private static final int CROP = 2;
    private static final int CROP_PICTURE = 3;
    private static final int SCALE = 5;//照片缩小比例
    private ImageView iv_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        iv_image = (ImageView) findViewById(R.id.img);

        this.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //即拍即显示
                showPicturePicker(ImageActivity.this, false);
            }
        });

        this.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //截图后显示
                showPicturePicker(ImageActivity.this, true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    //将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                            + "/image.jpg");
                    Bitmap newBitmap = BitmapUtil.zoomBitmap(bitmap, bitmap.getWidth() / SCALE,
                            bitmap.getHeight() / SCALE);
                    //由于bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();
                    iv_image.setImageBitmap(bitmap);
                    BitmapUtil.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(),
                            String.valueOf(System.currentTimeMillis()));
                    break;
                case CHOOSE_PICTURE:
                    ContentResolver resolver = getContentResolver();
                    //z照片的原始资源地址
                    Uri originalUri = data.getData();
                    //使用ContentProvider通过URI获取原始图片
                    try {
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，
                            //然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = BitmapUtil.zoomBitmap(
                                    photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE
                            );

                            //释放原始图片占用的内存，防止OUT OF MEMORY异常发生
                            photo.recycle();
                            iv_image.setImageBitmap(smallBitmap);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CROP:
                    Uri uri = null;
                    if (null != data) {
                        uri = data.getData();
                        System.out.print("Data");

                    } else {
                        System.out.println("File");
                        String fileName = getSharedPreferences(
                                "temp", Context.MODE_WORLD_WRITEABLE
                        ).getString("tempName", "");
                        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));

                    }
                    cropImage(uri, 500, 500, CROP_PICTURE);
                    break;
                case CROP_PICTURE:
                    Bitmap photo = null;
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        photo = BitmapFactory.decodeFile(photoUri.getPath());

                    }
                    if (photo == null) {
                        Bundle extra = data.getExtras();
                        if (extra != null) {
                            photo = (Bitmap) extra.get("data");
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        }
                    }
                    iv_image.setImageBitmap(photo);
                    break;
                default:
                    break;
            }
        }
    }


    public void showPicturePicker(Context context, boolean isCrop) {
        final boolean crop = isCrop;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("图片来源");
        builder.setNegativeButton("取消", null);
        builder.setItems(new String[]{"拍照", "相册"},
                new DialogInterface.OnClickListener() {
                    //类型码
                    int REQUEST_CODE;

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case TAKE_PICTURE:
                                Uri imageUri = null;
                                String fileName = null;
                                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (crop) {
                                    REQUEST_CODE = CROP;
                                    //删除上一次截图的临时文件
                                    SharedPreferences sharedPreferences = getSharedPreferences("temp", Context.MODE_WORLD_WRITEABLE);
                                    BitmapUtil.deletePhotoAtPathAndName(Environment.getExternalStorageDirectory().getAbsolutePath(), sharedPreferences.getString("tempName", ""));

                                    //保存本次截图临时文件名字
                                    fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("tempName", fileName);
                                    editor.commit();
                                } else {
                                    REQUEST_CODE = TAKE_PICTURE;
                                    fileName = "image.jpg";
                                }
                                imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
                                //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(openCameraIntent, REQUEST_CODE);
                                break;

                            case CHOOSE_PICTURE:
                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                if (crop) {
                                    REQUEST_CODE = CROP;
                                } else {
                                    REQUEST_CODE = CHOOSE_PICTURE;
                                }
                                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(openAlbumIntent, REQUEST_CODE);
                                break;

                            default:
                                break;
                        }
                    }
                });
        builder.create().show();

    }

    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }
}
