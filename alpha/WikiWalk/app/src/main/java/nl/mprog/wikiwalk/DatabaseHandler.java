package nl.mprog.wikiwalk;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHandler extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "wikiwalk.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}