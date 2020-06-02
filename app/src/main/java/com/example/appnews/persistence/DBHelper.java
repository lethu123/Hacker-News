package com.example.appnews.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String Name_DB = "HackerNews";
    public static final String Name_Table = "News";

    public static final String by = "bytext";
    public static final String descendants = "descendants";
    public static final String _id = "id";
    public static final String _idNews = "idNews";
    public static final String score = "score";
    public static final String time = "time";
    public static final String title = "title";
    public static final String type = "type";
    public static final String url = "url";
    public static final String category = "category";

    private static final String Create_table = ""
            + "create table " + Name_Table + " ( "
            + _id + " integer primary key autoincrement ,"
            + _idNews + " integer ,"
            + by + "text , "
            + descendants + " text , "
            + score + " integer  , "
            + time + " text  , "
            + title + " text  , "
            + type + " text  , "
            + url + " text ,"
            + category + " integer ); ";

    public DBHelper(Context context) {
        super(context, Name_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS News");
        onCreate(db);
    }
}
