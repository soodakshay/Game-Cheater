package com.funvilla.akshay.gamecheater.Fragments;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.funvilla.akshay.gamecheater.Database.DataTable;
import com.funvilla.akshay.gamecheater.Database.DatabaseHelper;

/**
 * Created by akshaysood on 2/24/16.
 */
public class SQLiteDataLoader extends AsyncTask<Void , Void , Void > {
ContentValues contentValues;
    DatabaseHelper helper;
   DataTable table;

    public SQLiteDataLoader(ContentValues contentValues, DatabaseHelper helper, DataTable table) {
        this.contentValues = contentValues;
        this.helper = helper;
        this.table = table;
    }

    @Override
    protected Void doInBackground(Void... params) {
        table.insert(contentValues  , helper.getWritableDatabase());
        return null;
    }
}
