package nl.mprog.wikiwalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import android.widget.TextView;

import java.util.ArrayList;
/**
 * WikiWalk - CollectionActivity.Java
 * Student: Paul Berinde-Tampanariu
 * This Class  contains the methods required to create the application's Collection View of
 * unlocked monuments. On creation data is requested from the db and the GridView is initialized
 * and populated with thumbnails of unlocked monuments. An OnItemClickListener paired with an intent
 * ensures moving forward to the CollectionItemActivity.
 **/


public class CollectionActivity extends Activity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Parameters for Fullscreen layout.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection);

        // Instantiate database operations and open db.
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(this);
        databaseOperations.open();

        // Get collection and row id information from db.
        ArrayList<String> collection = (ArrayList<String>) databaseOperations.getCollection();
        final ArrayList<String> rowID = (ArrayList<String>) databaseOperations.getRowID();

        // Set GridView, and Adapter
        GridView gridView = (GridView) findViewById(R.id.collectionView);
        gridView.setAdapter(new ImageAdapter(CollectionActivity.this, collection));

        // Set OnItemClickListener on GridView, and create intent for CollectionItemActivity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToFullscreenIntent = new Intent(context, CollectionItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", Integer.parseInt(rowID.get(position)));
                goToFullscreenIntent.putExtra("android.intent.extra.INTENT", bundle);
                context.startActivity(goToFullscreenIntent);
            }
        });

        // Populate TextView
        int collectionProgress = collection.size();
        String progressMessage = getString(R.string.collection_discovered_monuments) + collectionProgress + getString(R.string.collection_monument_total);
        TextView progressTextView = (TextView) findViewById(R.id.progressTextView);
        progressTextView.setText(progressMessage);

    }
}