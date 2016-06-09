package nl.mprog.wikiwalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getActionBar();
    }

    public void goToMap(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }

    public void goToCollection(View view) {
        Intent collectionIntent = new Intent(this, CollectionActivity.class);
        startActivity(collectionIntent);
    }

    public void goToTourSelect(View view) {
        Intent collectionIntent = new Intent(this, TourSelectActivity.class);
        startActivity(collectionIntent);
    }

    public void goToGameMode(View view) {
        Intent collectionIntent = new Intent(this, MapsActivity.class);
        startActivity(collectionIntent);
    }
}
