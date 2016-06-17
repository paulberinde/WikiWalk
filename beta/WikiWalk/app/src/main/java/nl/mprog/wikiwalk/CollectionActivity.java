package nl.mprog.wikiwalk;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(this);
        databaseOperations.open();
        List<String> collection = databaseOperations.getCollection();
        List<String> thumbnails = databaseOperations.getThumbs();
        ListView listView = (ListView) findViewById(R.id.collectionView);
        listView.setAdapter(new ImageAdapter(CollectionActivity.this, collection));

        int collectionProgress = collection.size();
        String progressMessage ="Unlocked Points of Interest : " + collectionProgress + "/613";
        TextView progressTextView = (TextView) findViewById(R.id.progressTextView);
        progressTextView.setText (progressMessage);
    }
}
