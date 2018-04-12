package com.alexia.callbutton.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexia.callbutton.jsonparsers.Questionnaire;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by alexm on 4/12/2018.
 */

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuestionsDB";
    private static final String TABLE_NAME = "Questions";
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String[] COLUMNS = { KEY_ID, KEY_TEXT};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " integer PRIMARY KEY, " + KEY_TEXT + " TEXT)";

        sqLiteDatabase.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addQuestion(Questionnaire question) {
        boolean shouldAdd = true;
        ArrayList<Questionnaire> existingItems = getQuestions();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, question.question);
        for (Questionnaire element: existingItems) {
            if (element.idQuestion == question.idQuestion) {
                shouldAdd = false;
                break;
            }
        }
        if (shouldAdd) {
            values.put(KEY_ID, question.idQuestion);
            db.insertOrThrow(TABLE_NAME,null,  values);
        } else {
            db.updateWithOnConflict(TABLE_NAME,values,KEY_ID + "=" + question.idQuestion,null,SQLiteDatabase.CONFLICT_FAIL);
            db.update(TABLE_NAME,values, KEY_ID + "=" + question.idQuestion,null);
        }
        // insert
//        db.update(TABLE_NAME, values,KEY_ID + "=" + question.idQuestion, null);
//
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public ArrayList<Questionnaire> getQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Questionnaire> items = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor == null) {
            return items;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String question = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
            items.add(new Questionnaire(id, question, 0));
            cursor.moveToNext();
        }
        return items;
    }
}
