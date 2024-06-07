package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public class ServicesContract {
    private ServicesContract(){}

    public static class ServicesEntry implements BaseColumns{
        public static final String TABLE_NAME = "services";
        public static final String COLUMN_NAME_SERVICE_ID = "s_id";
        public static final String COLUMN_NAME_SERVICE = "service";

    }
}
