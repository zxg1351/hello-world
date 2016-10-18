package com.example.administrator.zxg.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/8/27.
 */
public class ContactEntity  {
    /** 联系人名称 **/
    private String name;
    /** 联系人号码 **/
    private String number;
    /** 联系人头像 **/
    private Bitmap photo;

    @Override
    public String toString() {
        return "ContactEntity{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", photo=" + photo +
                '}';
    }

    public ContactEntity(String name, String number, Bitmap photo) {
        super();
        this.name = name;
        this.number = number;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

}
