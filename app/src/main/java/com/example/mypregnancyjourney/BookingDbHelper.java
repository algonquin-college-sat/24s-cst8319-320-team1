package com.example.mypregnancyjourney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingDbHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "PregnancyApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Create Statements
    // Service table create statement
    private static final String CREATE_TABLE_SERVICE =
            "CREATE TABLE IF NOT EXISTS" + ServicesContract.ServicesEntry.TABLE_NAME + "(" +
                    ServicesContract.ServicesEntry.COLUMN_NAME_SERVICE_ID + " INTEGER PRIMARY KEY," +
                    ServicesContract.ServicesEntry.COLUMN_NAME_SERVICE + " TEXT)";

    // Booking table create statement
    private static final String CREATE_TABLE_BOOKING =
            "CREATE TABLE IF NOT EXISTS" + BookingContract.BookingEntry.TABLE_NAME + "(" +
                    BookingContract.BookingEntry.COLUMN_NAME_BOOKING_ID + " INTEGER PRIMARY KEY," +
                    BookingContract.BookingEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    BookingContract.BookingEntry.COLUMN_NAME_STATUS + "TEXT," +
                    BookingContract.BookingEntry.COLUMN_NAME_TIMESLOT_ID + " INTEGER, " +
                    "FOREIGN KEY(" + BookingContract.BookingEntry.COLUMN_NAME_EMAIL + ") REFERENCES users (email)," +
                    " FOREIGN KEY(" + BookingContract.BookingEntry.COLUMN_NAME_TIMESLOT_ID + ") REFERENCES timeslots (t_id))";

    // Timeslots table create statement
    private static final String CREATE_TABLE_TIMESLOTS =
            "CREATE TABLE IF NOT EXISTS" + TimeslotsContract.TimeslotEntry.TABLE_NAME + "(" +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIMESLOT_ID + " INTEGER PRIMARY KEY," +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_CLINIC_ID + " INTEGER," +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_SERVICE_ID + " INTEGER," +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIME + " TEXT," +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_ISBOOKED + " BOOLEAN," +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_DATE + " DATE, " +
                    "FOREIGN KEY(" + TimeslotsContract.TimeslotEntry.COLUMN_NAME_CLINIC_ID + ") REFERENCES clinics (c_id)," +
                    " FOREIGN KEY(" + TimeslotsContract.TimeslotEntry.COLUMN_NAME_SERVICE_ID + ") REFERENCES Service(s_id)," +
                    " UNIQUE(" + TimeslotsContract.TimeslotEntry.COLUMN_NAME_CLINIC_ID + ", " + TimeslotsContract.TimeslotEntry.COLUMN_NAME_SERVICE_ID + ", " +
                    TimeslotsContract.TimeslotEntry.COLUMN_NAME_DATE + ", " + TimeslotsContract.TimeslotEntry.COLUMN_NAME_DATE + "))";

    // Clinic table create statement
    private static final String CREATE_TABLE_CLINIC =
            "CREATE TABLE IF NOT EXISTS" + ClinicContract.ClinicEntry.TABLE_NAME + "(" +
                    ClinicContract.ClinicEntry.COLUMN_NAME_CLINIC_ID + " INTEGER PRIMARY KEY," +
                    ClinicContract.ClinicEntry.COLUMN_NAME_CLINIC_NAME + " TEXT," +
                    ClinicContract.ClinicEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    ClinicContract.ClinicEntry.COLUMN_NAME_POSTAL_CODE + " TEXT," +
                    ClinicContract.ClinicEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    ClinicContract.ClinicEntry.COLUMN_NAME_PHONE + " TEXT)";

    public BookingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
