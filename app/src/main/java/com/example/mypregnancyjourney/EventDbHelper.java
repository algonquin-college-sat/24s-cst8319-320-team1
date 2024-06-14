package com.example.mypregnancyjourney;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EventDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CalendarContract.EventEntry.TABLE_NAME + " (" +
                    CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL," +
                    CalendarContract.EventEntry.COLUMN_NAME_DATE + " TEXT NOT NULL," +
                    CalendarContract.EventEntry.COLUMN_NAME_EVENT1 + " TEXT," +
                    CalendarContract.EventEntry.COLUMN_NAME_EVENT2 + " TEXT," +
                    CalendarContract.EventEntry.COLUMN_NAME_EVENT3 + " TEXT," +
                    "PRIMARY KEY (" + CalendarContract.EventEntry.COLUMN_NAME_USERNAME + ", " +
                    CalendarContract.EventEntry.COLUMN_NAME_DATE + "))";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CalendarContract.EventEntry.TABLE_NAME;

    public EventDbHelper(Context context) {
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

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public List<Event> getEventsForUserNextSevenDays(String username) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Adjust the SimpleDateFormat to match the format used in your database
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = dateFormat.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        String endDate = dateFormat.format(calendar.getTime());

        Log.d("DEBUG", "Today: " + todayDate + ", End Date: " + endDate);

        String query = "SELECT * FROM " + CalendarContract.EventEntry.TABLE_NAME +
                " WHERE " + CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " = ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " >= ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " <= ?";

        Log.d("DEBUG", "Query: " + query);
        Log.d("DEBUG", "Parameters: username = " + username + ", todayDate = " + todayDate + ", endDate = " + endDate);

        Cursor cursor = db.rawQuery(query, new String[]{username, todayDate, endDate});

        int dateIndex = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_DATE);
        int event1Index = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_EVENT1);
        int event2Index = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_EVENT2);
        int event3Index = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_EVENT3);

        if (cursor.moveToFirst()) {
            do {
                String date = dateIndex != -1 ? cursor.getString(dateIndex) : "N/A";
                String event1 = event1Index != -1 ? cursor.getString(event1Index) : "N/A";
                String event2 = event2Index != -1 ? cursor.getString(event2Index) : "N/A";
                String event3 = event3Index != -1 ? cursor.getString(event3Index) : "N/A";
                events.add(new Event(date, event1, event2, event3));

                Log.d("DEBUG", "Loaded event on: " + date);
            } while (cursor.moveToNext());
        } else {
            Log.d("DEBUG", "No events found");
        }
        cursor.close();
        db.close();
        return events;
    }

    public List<Event> getEventsForUserLastSevenDays(String username) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Adjust the SimpleDateFormat to match the format used in your database
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String endDate = dateFormat.format(new Date()); // Today's date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7); // Subtract 7 days from today
        String startDate = dateFormat.format(calendar.getTime());

        Log.d("DEBUG", "Start Date: " + startDate + ", End Date: " + endDate);

        // Query to get events between today and the past seven days
        String query = "SELECT * FROM " + CalendarContract.EventEntry.TABLE_NAME +
                " WHERE " + CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " = ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " >= ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " <= ?";

        // Log the query and the parameters
        Log.d("DEBUG", "Query: " + query);
        Log.d("DEBUG", "Parameters: username = " + username + ", startDate = " + startDate + ", endDate = " + endDate);

        Cursor cursor = db.rawQuery(query, new String[]{username, startDate, endDate});

        int dateIndex = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_DATE);
        int event1Index = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_EVENT1);
        int event2Index = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_EVENT2);
        int event3Index = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_EVENT3);

        if (cursor.moveToFirst()) {
            do {
                String date = dateIndex != -1 ? cursor.getString(dateIndex) : "N/A";
                String event1 = event1Index != -1 ? cursor.getString(event1Index) : "N/A";
                String event2 = event2Index != -1 ? cursor.getString(event2Index) : "N/A";
                String event3 = event3Index != -1 ? cursor.getString(event3Index) : "N/A";
                events.add(new Event(date, event1, event2, event3));

                Log.d("DEBUG", "Loaded event on: " + date);
            } while (cursor.moveToNext());
        } else {
            Log.d("DEBUG", "No events found");
        }
        cursor.close();
        db.close();
        return events;
    }

    public List<String> getEventDatesForUser(String username) {
        List<String> dates = new ArrayList<>();

        if (username == null || username.isEmpty()){
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                CalendarContract.EventEntry.TABLE_NAME,
                new String[] { CalendarContract.EventEntry.COLUMN_NAME_DATE },
                CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " = ?",
                new String[] { username },
                null, null, null
        );

        int dateIndex = cursor.getColumnIndex(CalendarContract.EventEntry.COLUMN_NAME_DATE);

        if (dateIndex != -1 && cursor != null) { // Ensure the column index is valid
            while (cursor.moveToNext()) {
                dates.add(cursor.getString(dateIndex));
            }
        } else {
            Log.e("DB_ERROR", "Date column not found in the database.");
        }

        cursor.close();
//        db.close();
        return dates;
    }









}
