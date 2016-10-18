package com.example.administrator.zxg.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zxg.R;
import com.example.administrator.zxg.common.uitl.CommonActivity;
import com.example.administrator.zxg.entity.ContactEntity;

import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * 联系人列表
 * Created by Administrator on 2016/8/27.
 */
public class ContactListActivity extends CommonActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        getPhoneContacts();//获取电话联系人

        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.me);
        return super.onCreateOptionsMenu(menu);
    }

    //联系人名称
    private ArrayList<ContactEntity> mContacts = new ArrayList<ContactEntity>();

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    //联系人显示名称
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    private void getPhoneContacts() {
        ContentResolver resolver = getContentResolver();
        Cursor phoneCursor = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空或者为空字段跳过当前循环
//                if(Textu)
                //得到联系人名称
                // 得到联系人名称
                String contactName = phoneCursor
                        .getString(PHONES_DISPLAY_NAME_INDEX);

                // 得到联系人ID
                Long contactid = phoneCursor
                        .getLong(PHONES_CONTACT_ID_INDEX);

                // 得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                // 得到联系人头像Bitamp
                Bitmap contactPhoto = null;
                // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI,
                            contactid);
                    InputStream input = ContactsContract.Contacts
                            .openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(
                            getResources(), R.mipmap.ic_launcher);
                }
                ContactEntity mContact = new ContactEntity(contactName,
                        phoneNumber, contactPhoto);
                mContacts.add(mContact);
            }
            phoneCursor.close();
        }
    }

    private void initList() {
        ListView lv = (ListView) findViewById(R.id.lv_contact);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContactListActivity.this,LayoutActivity.class);
                startActivity(intent);
            }
        });
        lv.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mContacts != null && mContacts.size() > 0) {
                return mContacts.size();
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int i) {

            if (mContacts!=null && mContacts.size()>0){
                return mContacts.get(i);
            }else {

                return null;
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
          ViewHolder holder = null;
            if (view == null){
                holder = new ViewHolder();
                view = LayoutInflater.from(ContactListActivity.this).inflate(R.layout.item_contact,null);
                holder.name = (TextView) view.findViewById(R.id.tv_name);
                holder.number = (TextView)view
                        .findViewById(R.id.tv_number);
                holder.photo = (ImageView) view
                        .findViewById(R.id.iv_photo);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            ContactEntity contact = mContacts.get(i);
            holder.name.setText(contact.getName()+"");
            holder.number.setText(contact.getNumber()+"'");
            holder.photo.setImageBitmap(contact.getPhoto());
            return view;
        }
        class ViewHolder{
            TextView name;
            TextView number;
            ImageView photo;
        }
    }


}
