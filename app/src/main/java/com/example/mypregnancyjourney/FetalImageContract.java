package com.example.mypregnancyjourney;

import android.provider.BaseColumns;

public final class FetalImageContract {
    private FetalImageContract() {} // Private constructor to prevent instantiation

    public static class FetalImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "fetal_images";
        public static final String COLUMN_WEEK = "week";
        public static final String COLUMN_IMAGE_RESOURCE_ID = "image_resource_id";
        public static final String COLUMN_RELATED_ARTICLE_URL = "related_article_url";
    }
}
