package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public class DocumentContract {
    private DocumentContract(){}

    public static class DocumentEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_document";
        public static final String COLUMN_NAME_DOC_ID = "doc_id";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_C_ID = "c_id";
        public static final String COLUMN_NAME_S_ID = "s_id";
        public static final String COLUMN_NAME_DOCPATH = "docPath";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_UPLOAD_DATE = "upload_date";
    }
}
