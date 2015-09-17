package com.example.sergbek.lessonnine.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.sergbek.lessonnine.model.ContactsPhone;

import java.util.ArrayList;
import java.util.List;

public class DataBaseContacts extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "contacts";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_CONTACT_NAME = "contact_name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_IMAGE = "image";

    private static final String DATABASE_CREATE_SCRIPT = "create table IF NOT EXISTS "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + COLUMN_CONTACT_NAME
            + " text not null, " + COLUMN_PHONE + " integer, " + COLUMN_IMAGE
            + " text);";

    public DataBaseContacts(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseContacts(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DataBaseContacts(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(ContactsPhone contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());
        values.put(COLUMN_IMAGE, contact.getImage());

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public ContactsPhone getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { COLUMN_ID,
                        COLUMN_CONTACT_NAME, COLUMN_PHONE, COLUMN_IMAGE }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ContactsPhone contact = new ContactsPhone(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return contact;
    }

    public List<ContactsPhone> getAllContacts() {
        List<ContactsPhone> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ContactsPhone contact = new ContactsPhone();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contact.setImage(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    public int updateContact(ContactsPhone contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());
        values.put(COLUMN_IMAGE, contact.getImage());

        return db.update(DATABASE_TABLE, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }
}
