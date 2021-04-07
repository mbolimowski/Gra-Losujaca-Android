package com.example.gralosujcajava.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gralosujcajava.Rank;
import com.example.gralosujcajava.db.tables.Ranks;
import java.util.ArrayList;

public class RankDAO {
    private DBHelper dbHelper;

    public RankDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<Rank> getTop10Ranking(){
        ArrayList<Rank> rankingList = new ArrayList<Rank>();

        Cursor cursor = dbHelper.getReadableDatabase()
                .query(
                        Ranks.TABLE_NAME,
                        new String[]{Ranks.Columns.RANK_LOGIN, Ranks.Columns.RANK_PASSWORD, Ranks.Columns.RANK_SCORE},
                        null, null, null, null, null, null
                );

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                rankingList.add(mapCursorToRank(cursor));
            }
        }
        cursor.close();
        return rankingList;
    }

    public Boolean register(final Rank rank){
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(
                "SELECT * FROM "
                        + Ranks.TABLE_NAME
                        + " WHERE "
                        + Ranks.Columns.RANK_LOGIN
                        + " =?",
                new String[]{rank.getLogin()});
        /*
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(
                "SELECT * FROM "
                        + Ranks.TABLE_NAME
                        + " WHERE "
                        + Ranks.Columns.RANK_LOGIN
                        + " = '"
                        + rank.getLogin()
                        +"'",
                null);
         */

        if(cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put(Ranks.Columns.RANK_LOGIN, rank.getLogin());
            contentValues.put(Ranks.Columns.RANK_PASSWORD, rank.getPassword());
            contentValues.put(Ranks.Columns.RANK_SCORE, rank.getScore());

            dbHelper.getWritableDatabase().insert(Ranks.TABLE_NAME, null, contentValues);
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }

    public Rank login(final String login, final String password){
        Rank rank = new Rank();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM "
                        + Ranks.TABLE_NAME
                        + " WHERE "
                        + Ranks.Columns.RANK_LOGIN
                        + " =? AND "
                        + Ranks.Columns.RANK_PASSWORD
                        + " =?",
                new String[]{login, password});
        if(cursor.getCount() == 1){
            cursor.moveToNext();
            rank = mapCursorToRank(cursor);
        }
        else{
            cursor.close();
            return null;
        }
        cursor.close();
        return rank;
    }

    public void updateScore(String login, Integer score){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        /*
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(
                "SELECT "
                        + Ranks.Columns.RANK_SCORE
                        +" FROM "
                        + Ranks.TABLE_NAME
                        + " WHERE "
                        + Ranks.Columns.RANK_LOGIN
                        + " =?",
                new String[]{login});

        if(cursor.getCount() == 1){
            cursor.moveToNext();
            contentValues.put(Ranks.Columns.RANK_SCORE, score);
            db.update(Ranks.TABLE_NAME, contentValues, "login=?", new String[]{login});
            cursor.close();
        }
         */
        contentValues.put(Ranks.Columns.RANK_SCORE, score);
        db.update(Ranks.TABLE_NAME, contentValues, "login=?", new String[]{login});
        db.close();
    }

    public Integer getScore(String login){
        Integer score = 0;
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(
                "SELECT "
                        + Ranks.Columns.RANK_SCORE
                        +" FROM "
                        + Ranks.TABLE_NAME
                        + " WHERE "
                        + Ranks.Columns.RANK_LOGIN
                        + " =?",
                new String[]{login});
        if(cursor.getCount() == 1){
            cursor.moveToNext();
            score = cursor.getInt(cursor.getColumnIndex(Ranks.Columns.RANK_SCORE));
            cursor.close();
        }
        return score;
    }

    private Rank mapCursorToRank(final Cursor cursor){
        int loginColumnID = cursor.getColumnIndex(Ranks.Columns.RANK_LOGIN);
        int passwordColumnID = cursor.getColumnIndex(Ranks.Columns.RANK_PASSWORD);
        int scoreColumnID = cursor.getColumnIndex(Ranks.Columns.RANK_SCORE);

        Rank rank = new Rank();
        rank.setLogin(cursor.getString(loginColumnID));
        rank.setPassword(cursor.getString(passwordColumnID));
        rank.setScore(cursor.getInt(scoreColumnID));

        return rank;

    }
}

