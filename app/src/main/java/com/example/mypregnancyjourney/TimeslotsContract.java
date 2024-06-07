package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public class TimeslotsContract {

    private TimeslotsContract(){}

    public static class TimeslotEntry implements BaseColumns{
        public static final String TABLE_NAME = "timeslots";
        public static final String COLUMN_NAME_TIMESLOT_ID = "t_id";
        public static final String COLUMN_NAME_CLINIC_ID = "c_id";
        public static final String COLUMN_NAME_SERVICE_ID = "s_id";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_ISBOOKED = "isBooked";
        public static final String COLUMN_NAME_DATE = "date";

    }
}
