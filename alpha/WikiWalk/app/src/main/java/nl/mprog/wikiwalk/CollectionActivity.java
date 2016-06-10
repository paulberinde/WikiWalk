package nl.mprog.wikiwalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        listView = (ListView) findViewById(R.id.listView);
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(this);
        databaseOperations.open();
        ArrayList<String> pois = (ArrayList<String>) databaseOperations.getPOIS();
        databaseOperations.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pois);
        listView.setAdapter(adapter);



    }
}
