package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public class ClinicContract {
    private ClinicContract(){}

    public static class ClinicEntry implements BaseColumns{
        public static final String TABLE_NAME = "clinics";
        public static final String COLUMN_NAME_CLINIC_ID = "c_id";
        public static final String COLUMN_NAME_CLINIC_NAME = "name";
        public static final String COLUMN_NAME_STREET = "street";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_POSTAL_CODE = "postal_code";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE = "phone";
    }
}
