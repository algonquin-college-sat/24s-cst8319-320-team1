package com.example.mypregnancyjourney;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FetalImageDAO {
    private SQLiteDatabase database;
    private Context context;
    private String[] allColumns = {
            FetalImageContract.FetalImageEntry._ID,
            FetalImageContract.FetalImageEntry.COLUMN_WEEK,
            FetalImageContract.FetalImageEntry.COLUMN_IMAGE_RESOURCE_ID,
            FetalImageContract.FetalImageEntry.COLUMN_RELATED_ARTICLE_URL
    };

    public FetalImageDAO(SQLiteDatabase database, Context context) {
        this.database = database;
        this.context = context;
    }

    public FetalImage getFetalImage(int week) {
        FetalImage fetalImage = null;
        Cursor cursor = null;

        try {
            cursor = database.query(
                    FetalImageContract.FetalImageEntry.TABLE_NAME,
                    allColumns,
                    FetalImageContract.FetalImageEntry.COLUMN_WEEK + "=?",
                    new String[]{String.valueOf(week)},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(FetalImageContract.FetalImageEntry._ID));
                int imageResourceId = cursor.getInt(cursor.getColumnIndex(FetalImageContract.FetalImageEntry.COLUMN_IMAGE_RESOURCE_ID));
                String relatedArticleUrl = cursor.getString(cursor.getColumnIndex(FetalImageContract.FetalImageEntry.COLUMN_RELATED_ARTICLE_URL));
                fetalImage = new FetalImage(id, week, imageResourceId, relatedArticleUrl);
            }
        } catch (Exception e) {
            Log.e("FetalImageDAO", "Error while getting fetal image for week " + week, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fetalImage;
    }
}
