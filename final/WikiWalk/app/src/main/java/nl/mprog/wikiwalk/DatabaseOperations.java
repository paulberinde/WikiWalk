package nl.mprog.wikiwalk;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
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

    public List<String> getPOIS() {
        List<String> poiList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT abc_lat, abc_lon, abc_objectnaam, visited, wiki_thumb_url FROM POIS ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            poiList.add(cursor.getString(0));
            poiList.add(cursor.getString(1));
            poiList.add(cursor.getString(2));
            poiList.add(cursor.getString(3));
            poiList.add(cursor.getString(4));
            cursor.moveToNext();
        }
        cursor.close();
        return poiList;
    }


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

/*
    public List<ArrayList<String>> getCollection() {
        ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
        Cursor cursor = database.rawQuery("SELECT row_ID wiki_thumb_url FROM POIS where VISITED='YES'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ArrayList<String> a = new ArrayList<>();
            a.add(cursor.getString(0));
            a.add(cursor.getString(1));
            collection.add(a);
            cursor.moveToNext();
        }
        cursor.close();
        return collection;
    }
  */


    public ArrayList<String> getCollectionItem(int rowID) {
    ArrayList<String> collectionItem = new ArrayList<>();
    Cursor cursor = database.rawQuery("SELECT wiki_image_url, abc_objectnaam, abc_adres, wiki_article_url, abc_locatie, abc_categorie FROM POIS where row_ID" + "=" + "'" + rowID + "'" , null);
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

}