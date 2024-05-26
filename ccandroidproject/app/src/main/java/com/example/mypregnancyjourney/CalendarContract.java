package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public final class CalendarContract {

    private CalendarContract() {}

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_EVENT1 = "event1";
        public static final String COLUMN_NAME_EVENT2 = "event2";
        public static final String COLUMN_NAME_EVENT3 = "event3";
    }
}
