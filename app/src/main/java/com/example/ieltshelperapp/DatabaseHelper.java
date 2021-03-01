package com.example.ieltshelperapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static String DATABASE_NAME = "IELTS_HELPER_DB";
    public static int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MyVocabulary (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD_ENG TEXT NOT NULL," +
                "WORD_TR TEXT NOT NULL," +
                "WORD_DESC TEXT NOT NULL" +");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MyVocabulary");
    }
}

