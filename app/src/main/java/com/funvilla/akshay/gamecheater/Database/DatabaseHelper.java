package com.funvilla.akshay.gamecheater.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Akshay on 2/23/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, DBValues.DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBValues.PC_QUERY);
        db.execSQL(DBValues.PS2_QUERY);
        db.execSQL(DBValues.PS3_QUERY);
        db.execSQL(DBValues.PSP_QUERY);
        db.execSQL(DBValues.VITA_QUERY);
        db.execSQL(DBValues.XBOXONE_QUERY);
        db.execSQL(DBValues.XBOX360_QUERY);
        db.execSQL(DBValues.WII_QUERY);
        db.execSQL(DBValues.WIIU_QUERY);
        db.execSQL(DBValues.DS_QUERY);
        db.execSQL(DBValues.DS3_QUERY);
        db.execSQL(DBValues.PS4_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + DBValues.PC_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.PS2_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.PS3_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.PS4_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.PSP_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.VITA_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.WII_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.WIIU_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.XBOX360_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.XBOXONE_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.DS3_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + DBValues.DS_TABLE);
        onCreate(db);
    }
}
