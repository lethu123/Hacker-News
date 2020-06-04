package com.example.appnews.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.appnews.presentation.home.NewModel;

public class NewsDB {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public NewsDB(Context context) {
        dbHelper = new DBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException ex) {
            database = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getAllItem(int type) {
        String query = "SELECT * FROM News WHERE category =" + type + " ORDER BY id DESC";
        Cursor cursor = null;
        cursor = database.rawQuery(query, null);
        return cursor;
    }

    public Boolean checkID(int id, int type) {
        String query = "select * from News where idNews=" + id + " and category=" + type + " limit 1";
        Cursor cursor = null;
        cursor = database.rawQuery(query, null);
        if(cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }
    // 1.history  2.bookmark
    public long addItem(NewModel newModel, int type) {
        Boolean checkExist = checkID(newModel.getId(), type);
        if(checkExist ==false) {
            // check dupplicate false (error)
            ContentValues values = new ContentValues();

            values.put(dbHelper.by, newModel.getBy());
            values.put(dbHelper.descendants, newModel.getDescendants());
            values.put(dbHelper._idNews, newModel.getId());
            values.put(dbHelper.score, newModel.getScore());
            values.put(dbHelper.time, newModel.getTime());
            values.put(dbHelper.title, newModel.getTitle());
            values.put(dbHelper.type, newModel.getType());
            values.put(dbHelper.url, newModel.getUrl());
            values.put(dbHelper.category, type);
           return database.insert(dbHelper.Name_Table, null, values);
        }
        return -1;
    }

    public long deleteItem(int id, int type) {
        return database.delete(dbHelper.Name_Table, dbHelper._idNews + "=" + id + " and " + dbHelper.category + "=" + type, null);
    }
}
