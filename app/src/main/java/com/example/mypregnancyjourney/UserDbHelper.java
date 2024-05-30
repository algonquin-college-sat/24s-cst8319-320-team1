package com.example.mypregnancyjourney;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "User.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY COLLATE NOCASE," +
                    UserContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Add methods for adding users, querying user information, etc.

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME +
                        " WHERE " + UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?",
                new String[] { email.toLowerCase() });

        if (cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD);
            if (passwordIndex != -1) {
                String storedPassword = cursor.getString(passwordIndex);
                cursor.close();
                return storedPassword.equals(password); // Replace with hashed password check in a real app
            } else {
                cursor.close();
                return false;
            }
        } else {
            cursor.close();
            return false;
        }
    }
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                new String[] { UserContract.UserEntry.COLUMN_NAME_EMAIL },
                "LOWER(" + UserContract.UserEntry.COLUMN_NAME_EMAIL + ")=?",
                new String[] { email.toLowerCase() },
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount > 0;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                new String[] { UserContract.UserEntry.COLUMN_NAME_USERNAME },
                UserContract.UserEntry.COLUMN_NAME_USERNAME + "=?",
                new String[] { username },
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount > 0;
    }

    public void addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email.toLowerCase()); // Convert email to lowercase
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password); // Note: Store a hashed password for security

        db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
    }

    public void updateUserPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, newPassword); // Consider hashing the password

        db.update(UserContract.UserEntry.TABLE_NAME, values,
                "LOWER(" + UserContract.UserEntry.COLUMN_NAME_EMAIL + ")=?",
                new String[] { email.toLowerCase() });
    }

    public String getUsername(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                new String[] { UserContract.UserEntry.COLUMN_NAME_USERNAME },
                UserContract.UserEntry.COLUMN_NAME_EMAIL + "=?",
                new String[] { email },
                null, null, null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            cursor.close();
            return username;
        } else {
            cursor.close();
            return null; // or a default username
        }
    }

}
