package com.widgets.widgey.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class QuotesSqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuotesList";
    private static final String TABLE_QUOTES = "Quotes";
    private static final String KEY_ID = "id";
    private static final String QUOTES = "quotes";
    private static final String AUTHOR = "author";

    Context mContext;


    public QuotesSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + QUOTES + " STRING," + AUTHOR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);

        // Create tables again
        onCreate(db);
    }



    public void batchInsterstion(List<Quotes> mQuotesListSqlite){


        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();

        for (Quotes quotes : mQuotesListSqlite) {

            values.put(AUTHOR, quotes.getAuthor());
            values.put(QUOTES,quotes.getQuote());

            try {
                db.insertOrThrow(TABLE_QUOTES, null, values);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }


    public Quotes getRandomQuote(){

        Quotes quotes = new Quotes();;
        String selectQuery = "SELECT  * FROM " + TABLE_QUOTES + " ORDER BY RANDOM() LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                quotes.setQuote(cursor.getString(1));
                quotes.setAuthor(cursor.getString(2));

            } while (cursor.moveToNext());
        }

        return quotes;
    }



}
