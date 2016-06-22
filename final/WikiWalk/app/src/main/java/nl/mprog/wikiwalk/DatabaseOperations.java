package nl.mprog.wikiwalk;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
/**
 * WikiWalk - DatabaseOperations.Java
 * Student: Paul Berinde-Tampanariu
 * This Class contains the methods used to read and write to the database.
 **/
public class DatabaseOperations {
    private SQLiteOpenHelper databaseHandler;
    private SQLiteDatabase database;
    private static DatabaseOperations instance;

    // Constructor
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
     * This method gets an ArrayList containing various information about monuments used for
     * marker setting.
     */
    public List<String> getMonuments(String isUnlocked) {
        List<String> monumentList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT abc_lat, abc_lon, abc_objectnaam, abc_categorie FROM POIS WHERE visited=" + "'" + isUnlocked + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            monumentList.add(cursor.getString(0));
            monumentList.add(cursor.getString(1));
            monumentList.add(cursor.getString(2));
            monumentList.add(cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();
        return monumentList;
    }

    /**
     * This method gets an ArrayList containing thumbnail urls for the Collection View.
     */
    public List<String> getCollection() {
        List<String> collection = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT wiki_thumb_url FROM POIS where visited ='YES'" , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            collection.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return collection;
    }
    /**
     * This method gets an ArrayList containing rowIDs used in the intent to the CollectionItemView.
     */
    public List<String> getRowID() {
        List<String> rowID = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT row_ID FROM POIS where visited ='YES'" , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            rowID.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return rowID;
    }

    /**
     * This method gets an ArrayList containing various information for a single Monument.
     */
    public ArrayList<String> getCollectionItem(int rowId) {
    ArrayList<String> collectionItem = new ArrayList<>();
    Cursor cursor = database.rawQuery("SELECT wiki_image_url, abc_objectnaam, abc_adres, wiki_article_url, abc_locatie, abc_categorie FROM POIS where row_ID" + "=" + "'" + rowId + "'" , null);
    cursor.moveToFirst();
    collectionItem.add(cursor.getString(0));
    collectionItem.add(cursor.getString(1));
    collectionItem.add(cursor.getString(2));
    collectionItem.add(cursor.getString(3));
    collectionItem.add(cursor.getString(4));
    collectionItem.add(cursor.getString(5));
    cursor.close();
    return collectionItem;
    }

    /**
     * This method changes the parameter in the field visited from NO to YES for the Monument that
     * has matching latitude, longitude and name.
     */
    public void setMonumentVisited(String latitude, String longitude, String name){
        String strSQL = ( "UPDATE POIS SET visited = 'YES' where abc_objectnaam =" + "'" + name + "'" + " AND abc_lat LIKE " + "'" + latitude + "'" + "AND abc_lon LIKE" + "'" + longitude + "'");
        database.execSQL(strSQL);
    }
}