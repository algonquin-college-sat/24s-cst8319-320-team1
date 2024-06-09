package com.example.mypregnancyjourney;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.content.ContentValues;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BookingDbHelper extends SQLiteOpenHelper {

    // Database Name and Version
    public static final String DATABASE_NAME = "Booking.db";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_SERVICE = ServicesContract.ServicesEntry.TABLE_NAME;
    public static final String TABLE_BOOKING = BookingContract.BookingEntry.TABLE_NAME;
    public static final String TABLE_TIMESLOTS = TimeslotsContract.TimeslotEntry.TABLE_NAME;
    public static final String TABLE_CLINIC = ClinicContract.ClinicEntry.TABLE_NAME;

    // Service Table - column names
    public static final String COL_SERVICE_ID = ServicesContract.ServicesEntry.COLUMN_NAME_SERVICE_ID;
    public static final String COL_SERVICE_NAME = ServicesContract.ServicesEntry.COLUMN_NAME_SERVICE;

    // Booking Table - column names
    public static final String COL_BOOKING_ID = BookingContract.BookingEntry.COLUMN_NAME_BOOKING_ID;
    public static final String COL_BOOKING_EMAIL = BookingContract.BookingEntry.COLUMN_NAME_EMAIL;
    public static final String COL_BOOKING_TIMESLOT_ID =  BookingContract.BookingEntry.COLUMN_NAME_TIMESLOT_ID;
    public static final String COL_BOOKING_STATUS =  BookingContract.BookingEntry.COLUMN_NAME_STATUS;
    public static final String COL_BOOKING_DATE =  BookingContract.BookingEntry.COLUMN_NAME_DATE;

    // Timeslots Table - column names
    public static final String COL_TIMESLOT_ID = TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIMESLOT_ID;
    public static final String COL_TIMESLOT_CLINIC_ID = TimeslotsContract.TimeslotEntry.COLUMN_NAME_CLINIC_ID;
    public static final String COL_TIMESLOT_SERVICE_ID = TimeslotsContract.TimeslotEntry.COLUMN_NAME_SERVICE_ID;
    public static final String COL_TIMESLOT_TIME = TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIME;
    public static final String COL_TIMESLOT_ISBOOKED = TimeslotsContract.TimeslotEntry.COLUMN_NAME_ISBOOKED;
    public static final String COL_TIMESLOT_DATE = TimeslotsContract.TimeslotEntry.COLUMN_NAME_DATE;

    // Clinic Table - column names
    public static final String COL_CLINIC_ID = ClinicContract.ClinicEntry.COLUMN_NAME_CLINIC_ID;
    public static final String COL_CLINIC_NAME = ClinicContract.ClinicEntry.COLUMN_NAME_CLINIC_NAME;
    public static final String COL_CLINIC_STREET = ClinicContract.ClinicEntry.COLUMN_NAME_STREET;
    public static final String COL_CLINIC_CITY = ClinicContract.ClinicEntry.COLUMN_NAME_CITY;
    public static final String COL_CLINIC_PROVINCE = ClinicContract.ClinicEntry.COLUMN_NAME_PROVINCE;
    public static final String COL_CLINIC_POSTAL_CODE = ClinicContract.ClinicEntry.COLUMN_NAME_POSTAL_CODE;
    public static final String COL_CLINIC_EMAIL = ClinicContract.ClinicEntry.COLUMN_NAME_EMAIL;
    public static final String COL_CLINIC_PHONE = ClinicContract.ClinicEntry.COLUMN_NAME_PHONE;

    // Table Create Statements
    // Service table create statement
    private static final String CREATE_TABLE_SERVICE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SERVICE + "(" +
                    COL_SERVICE_ID + " INTEGER PRIMARY KEY," +
                    COL_SERVICE_NAME + " TEXT)";

    // Clinic table create statement
    private static final String CREATE_TABLE_CLINIC =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CLINIC + "(" +
                    COL_CLINIC_ID + " INTEGER PRIMARY KEY," +
                    COL_CLINIC_NAME + " TEXT," +
                    COL_CLINIC_STREET + " TEXT," +
                    COL_CLINIC_CITY + " TEXT, " +
                    COL_CLINIC_PROVINCE + " TEXT, " +
                    COL_CLINIC_POSTAL_CODE + " TEXT," +
                    COL_CLINIC_EMAIL + " TEXT," +
                    COL_CLINIC_PHONE + " TEXT)";

    // Timeslots table create statement
    private static final String CREATE_TABLE_TIMESLOTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TIMESLOTS + "(" +
                    COL_TIMESLOT_ID + " INTEGER PRIMARY KEY," +
                    COL_TIMESLOT_CLINIC_ID + " INTEGER," +
                    COL_TIMESLOT_SERVICE_ID + " INTEGER," +
                    COL_TIMESLOT_TIME + " TEXT," +
                    COL_TIMESLOT_ISBOOKED + " BOOLEAN," +
                    COL_TIMESLOT_DATE + " TEXT )";
//                    "FOREIGN KEY(" + COL_TIMESLOT_CLINIC_ID + ") REFERENCES clinics (c_id)," +
//                    " FOREIGN KEY(" + COL_TIMESLOT_SERVICE_ID + ") REFERENCES services (s_id))";

    // Booking table create statement
    private static final String CREATE_TABLE_BOOKING =
            "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + "(" +
                    COL_BOOKING_ID + " INTEGER PRIMARY KEY," +
                    COL_BOOKING_EMAIL + " TEXT," +
                    COL_BOOKING_STATUS + "TEXT," +
                    COL_BOOKING_TIMESLOT_ID + " INTEGER, " +
                    COL_BOOKING_DATE + " TEXT ) ";
//                    "FOREIGN KEY(" + COL_BOOKING_EMAIL + ") REFERENCES users (email)," +
//                    "FOREIGN KEY(" + COL_BOOKING_TIMESLOT_ID + ") REFERENCES timeslots (t_id),"+
//                    "UNIQUE (COL_BOOKING_TIMESLOT_ID))";


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

        // Insert initial data
        insertInitialData(db);

        // Generate timeslots programmatically
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            generateTimeslots(db, 1, 1, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 1, 2, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 1, 3, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 1, 4, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 1, 5, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 1, 6, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 2, 1, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 2, 2, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 2, 3, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 2, 4, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 2, 5, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 2, 6, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 3, 1, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 3, 2, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 3, 3, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 3, 4, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 3, 5, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 3, 6, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 4, 1, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 4, 2, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 4, 3, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 4, 4, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 4, 5, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 4, 6, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 5, 1, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 5, 2, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 5, 3, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 5, 4, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 5, 5, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
            generateTimeslots(db, 5, 6, "2024-06-10", "2024-06-28", "09:00 AM", "05:00 PM", 60);
        }

    }

    private void insertInitialData(SQLiteDatabase db){
        insertService(db,"3D Ultrasound Test");
        insertService(db,"Prenatal Visit");
        insertService(db,"Genetic Screening");
        insertService(db,"Non-Stress Test");
        insertService(db,"Blood tests");
        insertService(db,"Vaccinations");

        insertClinic(db,"RAWSKN","2417 Holly Lane Unit 212E", "Ottawa","Ontario","K1V 7P2","chan0527@gmail.com","(561) 713-6495");
        insertClinic(db,"UC BABY Ottawa","1300 Baseline Road","Ottawa","Ontario","K2C 0A9","chan0527@gmail.com","(855) 622-4774");
        insertClinic(db,"A Date With Baby","20 De Boers Drive Suite 220","Toronto","Ontario","M3J 0H1","chan0527@gmail.com","(435) 875-5998");
        insertClinic(db,"Baby in Sight 3D / 4D Fetal Ultrasound","8312 McCowan Rd., Unit 204B","Markham","Ontario","L3P 8E1","chan0527@gmail.com","(858) 719-5478");
        insertClinic(db,"Institut Maïa","1139 Boulevard de la Cité-des-Jeunes","Vaudreuil-Dorion","Quebec","J7V 0H2","chan0527@gmail.com","(547) 231-1641");

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

    public long insertService(SQLiteDatabase db, String service) {
//        SQLiteDatabase db = this.getWritableDatabase();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateTimeslots(SQLiteDatabase db, int clinic_id, int service_id, String startDate, String endDate, String startTime, String endTime, int intervalMinutes){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        LocalDate start = LocalDate.parse(startDate, dateFormatter);
        LocalDate end = LocalDate.parse(endDate, dateFormatter);

        while (!start.isAfter(end)) {
            LocalTime startT = LocalTime.parse(startTime, timeFormatter);
            LocalTime endT = LocalTime.parse(endTime, timeFormatter);

            while (!startT.isAfter(endT.minusMinutes(intervalMinutes))) {
                LocalTime nextT = startT.plusMinutes(intervalMinutes);
                String timeslot = startT.format(timeFormatter) + " - " + nextT.format(timeFormatter);

                ContentValues values = new ContentValues();
                values.put(COL_TIMESLOT_CLINIC_ID, clinic_id);
                values.put(COL_TIMESLOT_SERVICE_ID, service_id);
                values.put(COL_TIMESLOT_TIME, timeslot);
                values.put(COL_TIMESLOT_ISBOOKED, false);
                values.put(COL_TIMESLOT_DATE, start.format(dateFormatter));

                db.insert(TABLE_TIMESLOTS, null, values);
                startT = nextT;
            }
            start = start.plusDays(1);
        }
    }

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

    public long insertClinic(SQLiteDatabase db,String name, String street, String city, String province, String postalCode, String email, String phone) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CLINIC_NAME, name);
        values.put(COL_CLINIC_STREET, street);
        values.put(COL_CLINIC_CITY, city);
        values.put(COL_CLINIC_PROVINCE, province);
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

    // Fetch unique provinces
    public Cursor getUniqueProvinces() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT " + COL_CLINIC_PROVINCE + " FROM " + TABLE_CLINIC;
        return db.rawQuery(selectQuery, null);
    }

    // Fetch cities based on selected province
    public Cursor getCitiesByProvince(String province) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT " + COL_CLINIC_CITY + " FROM " + TABLE_CLINIC + " WHERE " + COL_CLINIC_PROVINCE + " = ?";
        return db.rawQuery(selectQuery, new String[]{province});
    }

    // Fetch services based on selected province and city
    public Cursor getServicesByProvinceAndCity(String province, String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + TABLE_SERVICE + "." + COL_SERVICE_NAME +
                " FROM " + TABLE_SERVICE + " WHERE " + TABLE_SERVICE + "." + COL_SERVICE_ID + " IN ( SELECT DISTINCT " +
                TABLE_TIMESLOTS + "." + COL_TIMESLOT_SERVICE_ID + " FROM " +TABLE_TIMESLOTS +" INNER JOIN " + TABLE_CLINIC + " ON " +
                TABLE_TIMESLOTS+"."+COL_TIMESLOT_CLINIC_ID +"="+TABLE_CLINIC+"."+COL_CLINIC_ID+" WHERE "+ TABLE_CLINIC+"."+COL_CLINIC_PROVINCE+ " = ? AND "+
                TABLE_CLINIC+"."+COL_CLINIC_CITY+"= ? )";
        return db.rawQuery(selectQuery, new String[]{province, city});

    }

    // Fetch clinics based on selected province, city, and service
    public Cursor getClinicsByProvinceCityAndService(String province, String city, String service) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT " + TABLE_CLINIC +"."+ COL_CLINIC_NAME + " FROM " + TABLE_CLINIC +
                " INNER JOIN " + TABLE_TIMESLOTS +" ON "+TABLE_CLINIC+"."+COL_CLINIC_ID +"="+TABLE_TIMESLOTS+"."+COL_TIMESLOT_CLINIC_ID+
                " INNER JOIN " + TABLE_SERVICE +" ON "+ TABLE_TIMESLOTS+"."+COL_TIMESLOT_SERVICE_ID +" = "+TABLE_SERVICE+"."+COL_SERVICE_ID+
                " WHERE " +TABLE_CLINIC+"."+COL_CLINIC_PROVINCE+" = ? AND " +
                TABLE_CLINIC+"."+COL_CLINIC_CITY+" = ? AND "+
                TABLE_SERVICE+"."+COL_SERVICE_NAME+" = ? ";
        return db.rawQuery(selectQuery, new String[]{province, city, service});
    }
}
