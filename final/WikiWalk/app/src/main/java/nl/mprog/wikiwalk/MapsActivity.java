package nl.mprog.wikiwalk;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import java.util.ArrayList;

/**
 * WikiWalk - MapsActivity.Java
 * Student: Paul Berinde-Tampanariu
 * This class is partly based from the TreeHouse tutorial and related example provided at
 * https://github.com/treehouse/android-location-example-refactored.
 * Additions include map update on location change, behavior on location change and methods for
 * Monument Unlocks, as well as updating the example code to fit with newer requirements.
 * The class creates a MapFragment, and is then populated with markers via a method form the
 * MarkerHandler. A Method to check for unlocks is also defined here.
 *
 **/


public class MapsActivity extends FragmentActivity implements LocationHandler.LocationCallback {

    private GoogleMap mMap;
    private LocationHandler mLocationHandler;
    MarkerHandler markerHandler = new MarkerHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mLocationHandler = new LocationHandler(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mLocationHandler.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationHandler.disconnect();
    }

    private void setUpMapIfNeeded() {
        // Check if map has been instantiated
        if (mMap == null) {
            // Get map from support fragment
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // If map has been fetched successfully, then it is set up.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    /* Method checks for permissions, cleans map (prevents doubling of markers), enables My Location
    *  dot, then sets unlocked & and locked markers (in that order).
    */
    private void setUpMap() {


    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        // First clean the map to prevent any possible doubling of markers.
        markerHandler.cleanMap(mMap);

        // Enable location controls and zoom levels
        mMap.setMyLocationEnabled(true);
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        // Set markers on map (boolean true for unlocked markers, boolean false for locked markers)
        markerHandler.setMarkers(mMap, this, true);
        markerHandler.setMarkers(mMap, this, false);




    }



    // Method for changing camera on detected movement
    public void handleNewLocation(Location location) {

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        checkForUnlocks(latLng);
    }

    public void checkForUnlocks(LatLng latLng){
        ArrayList monumentList = markerHandler.getMonuments(this,false);
        for (int i=0; i <= monumentList.size()-4; i++){
            double latitude = Double.parseDouble((String)monumentList.get(i));
            i++;
            double longitude = Double.parseDouble((String)monumentList.get(i));
            i++;
            String title = (String)monumentList.get(i);
            i++;
            String category = (String)monumentList.get(i);
            LatLng monumentLatLng= new LatLng (latitude, longitude);
            double distance = SphericalUtil.computeDistanceBetween(latLng, monumentLatLng);
            if (distance <= 20){
                markerHandler.changeMarkerToUnlocked(this, latitude, longitude, title);
                Toast.makeText(MapsActivity.this, "Gefeliciteerd," + title + " is nu ontdekt! Voor meer informatie, check de collectie", Toast.LENGTH_LONG).show();
            }

        }
        markerHandler.setMarkers(mMap, this, true);
        markerHandler.setMarkers(mMap, this, false);

    }

}

