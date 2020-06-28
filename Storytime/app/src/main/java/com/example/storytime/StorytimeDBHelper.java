/*
package com.example.storytime;

public class StorytimeDBHelper {
}
*/

package com.example.storytime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/* This class has code that was taken from the textbook. Its purpose is to set up
 * the database.
 * The textbook says this code can be reused for other database classes. It is rather
 * general code.
 * */
public class StorytimeDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "storytime_users.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_TABLE_STORYTIME_USERS =
            "create table storytime_users (_id integer primary key autoincrement, "
                    + "firstname text not null, lastname text, email text, "
                    + "password text);";

    public StorytimeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STORYTIME_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(StorytimeDBHelper.class.getName(),
                "Upgrading database...");
        db.execSQL("DROP TABLE IF EXISTS storytime_users");
        onCreate(db);
    }
}
