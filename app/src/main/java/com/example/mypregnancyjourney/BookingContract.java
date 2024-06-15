package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public class BookingContract {

    private BookingContract(){}

    public static class BookingEntry implements BaseColumns{
        public static final String TABLE_NAME = "booking";
        public static final String COLUMN_NAME_BOOKING_ID = "b_id";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_TIMESLOT_ID = "t_id";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
