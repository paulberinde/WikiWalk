package nl.mprog.wikiwalk;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private SQLiteOpenHelper databaseHandler;
    private SQLiteDatabase database;
    private static DatabaseOperations instance;


    public DatabaseOperations(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }

   
    public static DatabaseOperations getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseOperations(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = databaseHandler.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getPOIS() {
        List<String> poiList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT abc_lat, abc_lon, abc_objectnaam FROM POIS ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            poiList.add(cursor.getString(0));
            poiList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return poiList;
    }
}