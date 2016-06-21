package com.funvilla.akshay.gamecheater.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by akshaysood on 2/24/16.
 */
public class DataTable {
    String TableName;

    public DataTable(String TableName) {
        this.TableName = TableName;
    }

    public void insert(ContentValues contentValues, SQLiteDatabase database) {
        database.insert(TableName, null, contentValues);
    }

    public Cursor retrieve(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TableName, null);
        return cursor;
    }
}
