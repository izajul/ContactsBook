package com.example.izajul.contactsbook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.izajul.contactsbook.Model.ContactModel;
import java.util.ArrayList;

public class ContactDatabaseManage {
    DBHelper dbHelper;
    Context context;
    ContactModel mContactModel;

    public ContactDatabaseManage(Context context) {
        this.context = context;
        dbHelper=new DBHelper(context);
    }

//Adding Contact To Database
    public long addContactToBD(ContactModel contactModel){
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(dbHelper.NAME_COLUMN,contactModel.getName());
        contentValues.put(dbHelper.EMAIL_COLUMN,contactModel.getEmail());
        contentValues.put(dbHelper.PHONE_COLUMN,contactModel.getPhone());
        contentValues.put(dbHelper.FAVORITE_COLUMN,0);
        if (!contactModel.getImgsrc().isEmpty()){contentValues.put(dbHelper.IMAGE_PATH_COLUMN,contactModel.getImgsrc());}
        long rowNumber=sqLiteDatabase.insert(dbHelper.CONTACT_TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return rowNumber;
    }

//Get All Contacts From DataBase
    public ArrayList<ContactModel>getContacts(){
        ArrayList<ContactModel>contacts = new ArrayList<>();
        String allSelectQuery="select * from "+dbHelper.CONTACT_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(allSelectQuery,null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(dbHelper.ID_COLUMN));
                String name = cursor.getString(cursor.getColumnIndex(dbHelper.NAME_COLUMN));
                String email = cursor.getString(cursor.getColumnIndex(dbHelper.EMAIL_COLUMN));
                String phone = cursor.getString(cursor.getColumnIndex(dbHelper.PHONE_COLUMN));
                String imgsrc = cursor.getString(cursor.getColumnIndex(dbHelper.IMAGE_PATH_COLUMN));
                ContactModel contact = new ContactModel(name, email, phone, id,imgsrc);
                contacts.add(contact);
            } while (cursor.moveToNext());
        }return  contacts;
    }

// Get Single Contact By Id
    public ContactModel getContactById(int id){
        String query = "select * from "+dbHelper.CONTACT_TABLE_NAME+" where "+dbHelper.ID_COLUMN+" = "+id;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.NAME_COLUMN));
            String phone = cursor.getString(cursor.getColumnIndex(dbHelper.PHONE_COLUMN));
            String email = cursor.getString(cursor.getColumnIndex(dbHelper.EMAIL_COLUMN));
            String src = cursor.getString(cursor.getColumnIndex(dbHelper.IMAGE_PATH_COLUMN));
            mContactModel = new ContactModel(name,email,phone,id,src);
        }return mContactModel;
    }

// Delete Contact From Database By Id
    public boolean deleteContact(int id){
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        long row = sqLiteDatabase.delete(DBHelper.CONTACT_TABLE_NAME," id = "+id,null);
        sqLiteDatabase.close();
        if (row>0) return true ; else return false;
    }

// Update Contact Details
    public long updateContactData(ContactModel contact,int id){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NAME_COLUMN,contact.getName());
        contentValues.put(dbHelper.PHONE_COLUMN,contact.getPhone());
        contentValues.put(dbHelper.EMAIL_COLUMN,contact.getEmail());
        long row = sqLiteDatabase.update(dbHelper.CONTACT_TABLE_NAME,contentValues," id="+id,null);
        sqLiteDatabase.close();
        return row;
    }
}

