package com.example.kraputis.meginam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kraputis on 11/30/16.
 */

public class SqLite_database extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Zaidimas";
    // Contacts table name
    private static final String TABLE_PRISIJUNGIMAS = " Zaidejas";
    // Contacts Table Columns names
    private static String NICK = "NICK";
    //private static String CODE = "CODE";

    public SqLite_database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_PRISIJUNK = "CREATE TABLE " + TABLE_PRISIJUNGIMAS + "("
                + NICK + " TEXT " + ")";
        db.execSQL(TABLE_PRISIJUNK);

        ContentValues values = new ContentValues();
        values.put(NICK, "null");
        // Inserting Row
        db.insert(TABLE_PRISIJUNGIMAS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateZAIDEJAS(String NICKAS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NICK, NICKAS);
        Log.i("SQLITE IRASO NICKA", NICKAS);/////////////
        db.update(TABLE_PRISIJUNGIMAS, values, null, null);
        // db.update(TABLE_PRISIJUNGIMAS, values, "CODE=null", null);
        //db.close();
    }

    public String getZAIDEJAS(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRISIJUNGIMAS, new String[] {
                        NICK }, null,
                null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Log.i("SQLITE GRAZINA NICKA", cursor.getString(0));
       // Log.i("SQLoooo", cursor.getString(1));
        //   Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
        //       cursor.getString(1), cursor.getString(2));
        return cursor.getString(0);
    }
}
