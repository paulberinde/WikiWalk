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

public class CollectionActivity extends Activity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection);
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(this);
        databaseOperations.open();
        ArrayList<String> collection = (ArrayList<String>) databaseOperations.getCollection();
        final ArrayList<String> rowID= (ArrayList<String>) databaseOperations.getRowID();

        GridView gridView = (GridView) findViewById(R.id.collectionView);

        gridView.setAdapter(new ImageAdapter(CollectionActivity.this, collection));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToFullscreenIntent = new Intent(context, CollectionItemActivity.class);
                Bundle bundle =new Bundle();
                bundle.putInt("position", Integer.parseInt(rowID.get(position)));
                goToFullscreenIntent.putExtra("android.intent.extra.INTENT", bundle);
                context.startActivity(goToFullscreenIntent);
            }
        });
        int collectionProgress = collection.size();
        String progressMessage = "Ontdekte Monumenten: " + collectionProgress + " van 424";
        TextView progressTextView = (TextView) findViewById(R.id.progressTextView);
        progressTextView.setText(progressMessage);

    }
}