package com.example.mypregnancyjourney;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FetalImageDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fetal_images.db";
    public static final int DATABASE_VERSION = 1;
    private Context mContext;

    public FetalImageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FETAL_IMAGE_TABLE = "CREATE TABLE " + FetalImageContract.FetalImageEntry.TABLE_NAME + " ("
                + FetalImageContract.FetalImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FetalImageContract.FetalImageEntry.COLUMN_WEEK + " INTEGER NOT NULL, "
                + FetalImageContract.FetalImageEntry.COLUMN_IMAGE_RESOURCE_ID + " INTEGER NOT NULL, "
                + FetalImageContract.FetalImageEntry.COLUMN_RELATED_ARTICLE_URL + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_FETAL_IMAGE_TABLE);

        // Pre-populate the database with example data
        prepopulateDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FetalImageContract.FetalImageEntry.TABLE_NAME);
        onCreate(db);
    }

    private void prepopulateDatabase(SQLiteDatabase db) {
        for (int week = 1; week <= 40; week++) {
            String imageResourceName = "week" + week;
            int imageResourceId = getResourceIdByName(imageResourceName);
            String relatedArticleUrl = "https://example.com/article_" + week;

            insertFetalImage(db, week, imageResourceId, relatedArticleUrl);
        }
    }

    private int getResourceIdByName(String resourceName) {
        int resourceId = mContext.getResources().getIdentifier(resourceName, "drawable", mContext.getPackageName());
        return resourceId;
    }

    private void insertFetalImage(SQLiteDatabase db, int week, int imageResourceId, String relatedArticleUrl) {
        ContentValues values = new ContentValues();
        values.put(FetalImageContract.FetalImageEntry.COLUMN_WEEK, week);
        values.put(FetalImageContract.FetalImageEntry.COLUMN_IMAGE_RESOURCE_ID, imageResourceId);
        values.put(FetalImageContract.FetalImageEntry.COLUMN_RELATED_ARTICLE_URL, relatedArticleUrl);

        long newRowId = db.insert(FetalImageContract.FetalImageEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Log.e("FetalImageDatabaseHelper", "Failed to insert row for week " + week);
        } else {
            Log.d("FetalImageDatabaseHelper", "Inserted row ID " + newRowId + " for week " + week);
        }
    }
}
