package nl.mprog.wikiwalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/**
 * WikiWalk - MainActivity.Java
 * Student: Paul Berinde-Tampanariu
 * This Class governs the behaviour of the Main Screen of the app.
 * Contains two methods with intents that link it to the rest of the app.
 **/


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Parameters for Fullscreen layout.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getActionBar();
    }

    /**
     * Method for the intent to go to the MapsActivity
     **/
    public void goToMap(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }
    /**
     * Method for the intent to go to the CollectionActivity
     **/
    public void goToCollection(View view) {
        Intent collectionIntent = new Intent(this, CollectionActivity.class);
        startActivity(collectionIntent);
    }
}
