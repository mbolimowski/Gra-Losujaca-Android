package com.example.gralosujcajava.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gralosujcajava.Rank;
import com.example.gralosujcajava.db.tables.Ranks;

public class DBHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 2;
    private final static String DB_NAME = "GRALOSUJACA.db";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "
                        + Ranks.TABLE_NAME
                        + " ( "
                        + Ranks.Columns.RANK_LOGIN
                        + " text primary key, "
                        + Ranks.Columns.RANK_PASSWORD
                        +" text, "
                        + Ranks.Columns.RANK_SCORE
                        + " integer ) "
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                "DROP TABLE IF EXISTS "
                        + Ranks.TABLE_NAME
        );
        onCreate(db);
    }
}
