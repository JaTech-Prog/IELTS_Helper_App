package com.example.ieltshelperapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataAccessMyVocabulary {
    private SQLiteDatabase db;
    private final Context context;
    private final DatabaseHelper dbHelper;
    public static String TABLE_NAME = "MyVocabulary";
    public static String ID_COL = "_id";
    public static String WORD_ENG_COL = "WORD_ENG";
    public static String WORD_TR_COL = "WORD_TR";
    public static String WORD_DESC_COL = "WORD_DESC";

    public DataAccessMyVocabulary(Context c) {
        context = c;
        dbHelper = new DatabaseHelper(context);
    }

    public void closeDb() {db.close();}

    public void connectToDb() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public Boolean AddWord(Vocabulary word) {
        try{
            ContentValues newValue = new ContentValues();
            newValue.put(WORD_ENG_COL, word.getWordENG());
            newValue.put(WORD_TR_COL, word.getWordTR());
            newValue.put(WORD_DESC_COL, word.getWordDESC());

            return db.insert(TABLE_NAME, null, newValue) > -1;
        } catch (SQLException ex) {
            return false;
        }
    }

    public Cursor findWord(Vocabulary word) {
        String[] column = {
                ID_COL, WORD_ENG_COL, WORD_TR_COL, WORD_DESC_COL
        };

        String selection = WORD_ENG_COL + " like '%" + word.getWordENG() + "%'";
        Cursor cursor = db.query(TABLE_NAME, column, selection, null, null, null, null);

        return  cursor;
    }


    public Cursor getAllVocabulary() {
        return db.rawQuery("SELECT * FROM MyVocabulary", new String[]{});
    }

    public int deleteWord(Vocabulary word) {
        String[] column = {
                ID_COL, WORD_ENG_COL, WORD_TR_COL, WORD_DESC_COL
        };

        String selection = WORD_ENG_COL + " =?" + " AND " + WORD_TR_COL + " =?" + " AND " + WORD_DESC_COL + " =?";
        String[] args = new String[]{word.getWordENG(), word.getWordTR(), word.getWordDESC()};
        try {
            int count = db.delete(TABLE_NAME, selection, args);
            return count;
        } catch (Exception ex) {
            return 0;
        }
    }


    public Integer UpdateVocabulary(Vocabulary oldWord, Vocabulary newWord) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD_ENG_COL, newWord.getWordENG());
        contentValues.put(WORD_TR_COL, newWord.getWordTR());
        contentValues.put(WORD_DESC_COL, newWord.getWordDESC());

        String selection = WORD_ENG_COL + " =?" + " AND " + WORD_TR_COL + " =?" + " AND " + WORD_DESC_COL + " =?";
        String[] args = new String[]{oldWord.getWordENG(), oldWord.getWordTR(), oldWord.getWordDESC()};

        try {
            return db.update(TABLE_NAME, contentValues, selection, args);
        } catch (Exception ex) {
            return 0;
        }
    }
}
