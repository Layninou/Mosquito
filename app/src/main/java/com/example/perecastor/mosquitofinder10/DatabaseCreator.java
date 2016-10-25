package com.example.perecastor.mosquitofinder10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Père Castor on 08/06/2016.
 */
public class DatabaseCreator extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "database_mosquitofinder";

    // Table Names
    private static final String T_QUIZZ = "table_quizz";

    /*
    *
    *
    * column names
    *
    * */

    //Quizz
    private static final String KEY_Q_ID = "quizz_id";
    private static final String KEY_Q_A = "quizz_a";
    private static final String KEY_Q_B = "quizz_b";
    private static final String KEY_Q_C = "quizz_c";
    private static final String KEY_Q_D = "quizz_d";
    private static final String KEY_Q_E = "quizz_e";
    private static final String KEY_Q_F = "quizz_f";
    private static final String KEY_Q_G = "quizz_g";
    private static final String KEY_Q_PICS = "quizz_pics";

    // Table create statement
    private static final String CREATE_TABLE_QUIZZ = "CREATE TABLE " + T_QUIZZ + " ("+
            KEY_Q_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_Q_A + " INTEGER," +
            KEY_Q_B + " INTEGER," +
            KEY_Q_C + " INTEGER," +
            KEY_Q_D + " INTEGER," +
            KEY_Q_E + " INTEGER," +
            KEY_Q_F + " INTEGER," +
            KEY_Q_G + " INTEGER," +
            KEY_Q_PICS + " BLOB);";


    //Constructor
    public DatabaseCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseCreator(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_QUIZZ);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // creating table
        db.execSQL(CREATE_TABLE_QUIZZ);

        // create new table
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
