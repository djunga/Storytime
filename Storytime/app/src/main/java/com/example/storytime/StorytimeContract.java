package com.example.storytime;

import android.provider.BaseColumns;

/* This class was based on code from the textbook. */
public class StorytimeContract {

    private StorytimeContract() {
    }

    public static final class StorytimeEntry implements BaseColumns {
        public static final String TABLE_NAME = "storytime_users";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_FIRST_NAME = "firstname";
        public static final String COLUMN_LAST_NAME = "lastname";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
    }
}
