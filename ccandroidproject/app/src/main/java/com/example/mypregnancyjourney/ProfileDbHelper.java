package com.example.mypregnancyjourney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ProfileDatabase.db";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_PROFILE_TABLE =
            "CREATE TABLE " + ProfileContract.ProfileEntry.TABLE_NAME + " (" +
                    ProfileContract.ProfileEntry.COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    ProfileContract.ProfileEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    ProfileContract.ProfileEntry.COLUMN_AGE + " INTEGER NOT NULL," +
                    ProfileContract.ProfileEntry.COLUMN_DUE_DATE + " TEXT NOT NULL," +
                    ProfileContract.ProfileEntry.COLUMN_MEDICAL_HISTORY + " TEXT," +
                    ProfileContract.ProfileEntry.COLUMN_EMERGENCY_CONTACT + " TEXT NOT NULL)";

    private static final String SQL_DELETE_PROFILE_TABLE =
            "DROP TABLE IF EXISTS " + ProfileContract.ProfileEntry.TABLE_NAME;

    public ProfileDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PROFILE_TABLE);
        onCreate(db);
    }
}
