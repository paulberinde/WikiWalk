package nl.mprog.wikiwalk;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * WikiWalk - DatabaseHandler.Java
 * Student: Paul Berinde-Tampanariu
 * This Class extends the SQLiteAsset helper Class, given that we are working with an already
 * existing database.
 **/

public class DatabaseHandler extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "wikiwalk.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}