package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public final class ProfileContract {

    private ProfileContract() {}

    public static class ProfileEntry implements BaseColumns {
        public static final String TABLE_NAME = "profile";
        public static final String COLUMN_USERNAME = "username"; // Added username column
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_MEDICAL_HISTORY = "medical_history";
        public static final String COLUMN_EMERGENCY_CONTACT = "emergency_contact";
    }
}

