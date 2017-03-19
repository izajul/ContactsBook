package com.example.izajul.contactsbook.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by izajul on 1/14/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "contacts_db";
    public static final int DB_VERSION = 1;
    public static final String CONTACT_TABLE_NAME = "contacts_table";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String EMAIL_COLUMN = "email";
    public static final String PHONE_COLUMN = "phone";
    public static final String FAVORITE_COLUMN = "favorite";
    public static final String IMAGE_PATH_COLUMN = "imgpath";

    public static final String TABLE_CREATE_QUERY = "create table "+CONTACT_TABLE_NAME+"("+ID_COLUMN+" integer primary key autoincrement,"+NAME_COLUMN+" text,"+EMAIL_COLUMN+" text,"+PHONE_COLUMN+" text,"+FAVORITE_COLUMN+" integer,"+IMAGE_PATH_COLUMN+" text);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+CONTACT_TABLE_NAME+" if exists");
        onCreate(db);
    }
}
