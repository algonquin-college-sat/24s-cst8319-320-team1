package com.example.mypregnancyjourney;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.content.ContentValues;

import java.time.LocalDate;

public class BookingDbHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "PregnancyApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_SERVICE = ServicesContract.ServicesEntry.TABLE_NAME;
    private static final String TABLE_BOOKING = BookingContract.BookingEntry.TABLE_NAME;
    private static final String TABLE_TIMESLOTS = TimeslotsContract.TimeslotEntry.TABLE_NAME;
    private static final String TABLE_CLINIC = ClinicContract.ClinicEntry.TABLE_NAME;

    // Service Table - column names
    private static final String COL_SERVICE_ID = ServicesContract.ServicesEntry.COLUMN_NAME_SERVICE_ID;
    private static final String COL_SERVICE_NAME = ServicesContract.ServicesEntry.COLUMN_NAME_SERVICE;

    // Booking Table - column names
    private static final String COL_BOOKING_ID = BookingContract.BookingEntry.COLUMN_NAME_BOOKING_ID;
    private static final String COL_BOOKING_EMAIL = BookingContract.BookingEntry.COLUMN_NAME_EMAIL;
    private static final String COL_BOOKING_TIMESLOT_ID =  BookingContract.BookingEntry.COLUMN_NAME_TIMESLOT_ID;
    private static final String COL_BOOKING_STATUS =  BookingContract.BookingEntry.COLUMN_NAME_STATUS;
    private static final String COL_BOOKING_DATE =  BookingContract.BookingEntry.COLUMN_NAME_DATE;

    // Timeslots Table - column names
    private static final String COL_TIMESLOT_ID = TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIMESLOT_ID;
    private static final String COL_TIMESLOT_CLINIC_ID = TimeslotsContract.TimeslotEntry.COLUMN_NAME_CLINIC_ID;
    private static final String COL_TIMESLOT_SERVICE_ID = TimeslotsContract.TimeslotEntry.COLUMN_NAME_SERVICE_ID;
    private static final String COL_TIMESLOT_TIME = TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIME;
    private static final String COL_TIMESLOT_ISBOOKED = TimeslotsContract.TimeslotEntry.COLUMN_NAME_ISBOOKED;
    private static final String COL_TIMESLOT_DATE = TimeslotsContract.TimeslotEntry.COLUMN_NAME_DATE;

    // Clinic Table - column names
    private static final String COL_CLINIC_ID = ClinicContract.ClinicEntry.COLUMN_NAME_CLINIC_ID;
    private static final String COL_CLINIC_NAME = ClinicContract.ClinicEntry.COLUMN_NAME_CLINIC_NAME;
    private static final String COL_CLINIC_ADDR = ClinicContract.ClinicEntry.COLUMN_NAME_ADDRESS;
    private static final String COL_CLINIC_POSTAL_CODE = ClinicContract.ClinicEntry.COLUMN_NAME_POSTAL_CODE;
    private static final String COL_CLINIC_EMAIL = ClinicContract.ClinicEntry.COLUMN_NAME_EMAIL;
    private static final String COL_CLINIC_PHONE = ClinicContract.ClinicEntry.COLUMN_NAME_PHONE;

    // Table Create Statements
    // Service table create statement
    private static final String CREATE_TABLE_SERVICE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SERVICE + "(" +
                    COL_SERVICE_ID + " INTEGER PRIMARY KEY," +
                    COL_SERVICE_NAME + " TEXT)";

    // Booking table create statement
    private static final String CREATE_TABLE_BOOKING =
            "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + "(" +
                    COL_BOOKING_ID + " INTEGER PRIMARY KEY," +
                    COL_BOOKING_EMAIL + " TEXT," +
                    COL_BOOKING_STATUS + "TEXT," +
                    COL_BOOKING_TIMESLOT_ID + " INTEGER, " +
                    COL_BOOKING_DATE + " DATE, " +
                    "FOREIGN KEY(" + COL_BOOKING_EMAIL + ") REFERENCES users (email)," +
                    " FOREIGN KEY(" + COL_BOOKING_TIMESLOT_ID + ") REFERENCES timeslots (t_id),"+
                    "UNIQUE (COL_BOOKING_TIMESLOT_ID))";

    // Timeslots table create statement
    private static final String CREATE_TABLE_TIMESLOTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TIMESLOTS + "(" +
                    COL_TIMESLOT_ID + " INTEGER PRIMARY KEY," +
                    COL_TIMESLOT_CLINIC_ID + " INTEGER," +
                    COL_TIMESLOT_SERVICE_ID + " INTEGER," +
                    COL_TIMESLOT_TIME + " TEXT," +
                    COL_TIMESLOT_ISBOOKED + " BOOLEAN," +
                    COL_TIMESLOT_DATE + " DATE, " +
                    "FOREIGN KEY(" + COL_TIMESLOT_CLINIC_ID + ") REFERENCES clinics (c_id)," +
                    " FOREIGN KEY(" + COL_TIMESLOT_SERVICE_ID + ") REFERENCES Service(s_id))";

    // Clinic table create statement
    private static final String CREATE_TABLE_CLINIC =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CLINIC + "(" +
                    COL_CLINIC_ID + " INTEGER PRIMARY KEY," +
                    COL_CLINIC_NAME + " TEXT," +
                    COL_CLINIC_ADDR + " TEXT," +
                    COL_CLINIC_POSTAL_CODE + " TEXT," +
                    COL_CLINIC_EMAIL + " TEXT," +
                    COL_CLINIC_PHONE + " TEXT)";

    // Service table delete statement
    private static final String DELETE_TABLE_SERVICE =
            "DROP TABLE IF EXISTS " + TABLE_SERVICE;

    // Booking table delete statement
    private static final String DELETE_TABLE_BOOKING =
            "DROP TABLE IF EXISTS " + TABLE_BOOKING;

    // Timeslots table delete statement
    private static final String DELETE_TABLE_TIMESLOTS =
            "DROP TABLE IF EXISTS " + TABLE_TIMESLOTS;

    // Clinic table delete statement
    private static final String DELETE_TABLE_CLINIC =
            "DROP TABLE IF EXISTS " + TABLE_CLINIC;

    public BookingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_SERVICE);
        db.execSQL(CREATE_TABLE_BOOKING);
        db.execSQL(CREATE_TABLE_TIMESLOTS);
        db.execSQL(CREATE_TABLE_CLINIC);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_SERVICE);
        db.execSQL(DELETE_TABLE_BOOKING);
        db.execSQL(DELETE_TABLE_TIMESLOTS);
        db.execSQL(DELETE_TABLE_CLINIC);
        onCreate(db);
    }

    // ------------------------ "Service" table methods ----------------//

    public long insertService(String service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SERVICE_NAME, service);

        // insert row
        long service_id = db.insert(TABLE_SERVICE, null, values);

        return service_id;
    }

    // Getting single service
    public Cursor getService(long service_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE + " WHERE "
                + COL_SERVICE_ID + " = " + service_id;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // Getting all services
    public Cursor getAllServices() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // ------------------------ "Booking" table methods ----------------//

    public long insertBooking(String email, LocalDate date, int timeslot_id, String sta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKING_EMAIL, email);
        values.put(COL_BOOKING_TIMESLOT_ID, timeslot_id);
        values.put(COL_BOOKING_STATUS, sta);
        values.put(COL_BOOKING_DATE, date.toString());

        // insert row
        long booking_id = db.insert(TABLE_BOOKING, null, values);

        return booking_id;
    }

    // Getting single booking
    public Cursor getBooking(long booking_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BOOKING + " WHERE "
                + COL_BOOKING_ID + " = " + booking_id;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // Getting all bookings
    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKING;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // ------------------------ "Timeslots" table methods ----------------//

    public long insertTimeslot(int clinic_id, int service_id, String time, boolean isBooked, LocalDate date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TIMESLOT_CLINIC_ID, clinic_id);
        values.put(COL_TIMESLOT_SERVICE_ID, service_id);
        values.put(COL_TIMESLOT_TIME, time);
        values.put(COL_TIMESLOT_ISBOOKED, isBooked);
        values.put(COL_TIMESLOT_DATE, date.toString());

        // insert row
        long timeslot_id = db.insert(TABLE_TIMESLOTS, null, values);

        return timeslot_id;
    }

    // Getting single timeslot
    public Cursor getTimeslot(long timeslot_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TIMESLOTS + " WHERE "
                + COL_TIMESLOT_ID + " = " + timeslot_id;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // Getting all timeslots
    public Cursor getAllTimeslots() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TIMESLOTS;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // ------------------------ "Clinic" table methods ----------------//

    public long createClinic(String name, String addr, String postalCode, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CLINIC_NAME, name);
        values.put(COL_CLINIC_ADDR, addr);
        values.put(COL_CLINIC_POSTAL_CODE, postalCode);
        values.put(COL_CLINIC_EMAIL, email);
        values.put(COL_CLINIC_PHONE, phone);

        // insert row
        long clinic_id = db.insert(TABLE_CLINIC, null, values);

        return clinic_id;
    }

    // Getting single clinic
    public Cursor getClinic(long clinic_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CLINIC + " WHERE "
                + COL_CLINIC_ID + " = " + clinic_id;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    // Getting all clinics
    public Cursor getAllClinics() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CLINIC;

        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }
}
