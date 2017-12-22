package com.stardust.app.base.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.*;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.text.TextUtils;
import android.util.Log;


/**
 * Created by bestw on 2017/12/4.
 */

public class ContactUtil {
    private Context context;

    public ContactUtil(Context context) {
        this.context = context;
    }

    /**
     * 添加联系人到通讯录
     * */
    public void addContact(String name, String phoneNumber, String email, String company) {
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();

        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(CommonDataKinds.Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        context.getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的Email地址
        values.put(Email.DATA, "" + email);
        // 电子邮件的类型
        values.put(Email.TYPE, Email.TYPE_WORK);
        // 向联系人Email URI添加Email数据
        context.getContentResolver().insert(Data.CONTENT_URI, values);

        //添加公司 组织
        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
        values.put(Organization.DATA, "" + company);
        context.getContentResolver().insert(Data.CONTENT_URI, values);
//        Toast.makeText(this, "联系人数据添加成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 向联系人追加信息
     * */
    public void updateContact(String name, String phoneNumber, String email, String company) {

        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.PhoneLookup.DISPLAY_NAME +" =?",new String[]{name}, null);
        String contactId = "";
        String rawContactId = "";
        //向下移动光标
        while (cursor.moveToNext()) {
            //根据id获取手机号码，手机号码表和用户资料表根据id关联
            contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//            Debug.show("contactId---------------:" + contactId);
            // 获取RawContacts表的游标
            Cursor rawContactCur = cr.query(RawContacts.CONTENT_URI, null,
                    RawContacts.CONTACT_ID + " =?", new String[] { contactId }, null);
            // 该查询结果一般只返回一条记录，所以我们直接让游标指向第一条记录
            if (rawContactCur.moveToFirst()) {
                // 读取第一条记录的RawContacts._ID列的值
                rawContactId = rawContactCur.getString(rawContactCur
                        .getColumnIndex(RawContacts._ID));
//                Debug.show("rawContactId---------------:" + rawContactId);
            }
            // 关闭游标
            rawContactCur.close();
            break;
        }
        cursor.close();
        if (TextUtils.isEmpty(rawContactId)) {
            return;
        }
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        cr.insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(CommonDataKinds.Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        cr.insert(Data.CONTENT_URI, values);

        if (!TextUtils.isEmpty(email)) {
            values.clear();

            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            // 联系人的Email地址
            values.put(Email.DATA, "" + email);
            // 电子邮件的类型
            values.put(Email.TYPE, Email.TYPE_WORK);
            // 向联系人Email URI添加Email数据
            cr.insert(Data.CONTENT_URI, values);
        }

        //添加公司 组织
        values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
        values.put(Organization.DATA, "" + company);
//        values.put(Organization.TYPE, "" + Organization.TYPE_WORK);
        cr.insert(Data.CONTENT_URI, values);

    }

    /**
     * 更新联系人信息
     * 方法貌似有问题 待修改
     * */
    public void updateContact2(String name, String phoneNumber, String email) {

        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.PhoneLookup.DISPLAY_NAME +" =?",new String[]{name}, null);
        String contactId = "";
        String rawContactId = "";
        //向下移动光标
        while (cursor.moveToNext()) {
            //根据id获取手机号码，手机号码表和用户资料表根据id关联
            contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Debug.show("contactId---------------:" + contactId);
            // 获取RawContacts表的游标
            Cursor rawContactCur = cr.query(RawContacts.CONTENT_URI, null,
                    RawContacts.CONTACT_ID + " =?", new String[] { contactId }, null);
            // 该查询结果一般只返回一条记录，所以我们直接让游标指向第一条记录
            if (rawContactCur.moveToFirst()) {
                // 读取第一条记录的RawContacts._ID列的值
                rawContactId = rawContactCur.getString(rawContactCur
                        .getColumnIndex(RawContacts._ID));
                Debug.show("rawContactId---------------:" + rawContactId);
            }
            // 关闭游标
            rawContactCur.close();
            break;
        }
        cursor.close();
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
//        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

//        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(CommonDataKinds.Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
//        context.getContentResolver().insert(Data.CONTENT_URI, values);
//        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的Email地址
        values.put(Email.DATA, "" + email);
        // 电子邮件的类型
        values.put(Email.TYPE, Email.TYPE_WORK);
        // 向联系人Email URI添加Email数据
//        context.getContentResolver().insert(Data.CONTENT_URI, values);

        if (TextUtils.isEmpty(rawContactId)) {
            return;
        }
        String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParams = new String[]{rawContactId, Phone.CONTENT_ITEM_TYPE, };
        context.getContentResolver().update(Data.CONTENT_URI, values, where, whereParams);
    }

    /**
     * 判断通讯录中是否存在
     */
    public boolean isExist(String name) {
        boolean result = false;
        //得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        // Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //根据传入的姓名查询通讯录，这个姓名就是上一个activity传过来的参数
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.PhoneLookup.DISPLAY_NAME +" =?",new String[]{name}, null);
//        Debug.show("———-cursor——-" + cursor);
        //向下移动光标
        while (cursor.moveToNext()) {
            result = true;
            break;
//            //取得联系人名字
//            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
//            String contact = cursor.getString(nameFieldColumnIndex);
//            //根据id获取手机号码，手机号码表和用户资料表根据id关联
//            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + “ =”+ContactId, null, null);
//            //获取用户的第一个手机号，因为用户有可能不只有一个手机号
//            while (phone.moveToNext()) {
//                result = true;
//            }
        }
        cursor.close();
        return result;
    }
}
