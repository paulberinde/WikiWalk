package nl.mprog.wikiwalk;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

/**
 * WikiWalk - MarkerHandler.Java
 * Student: Paul Berinde-Tampanariu
 * This clas contains methods for geting monument data (used in unlocking), setting markers,
 * cleaning the map and changing a marker from locked to unlocked by editing the db value. *
 **/

public class MarkerHandler {



    public ArrayList getMonuments(Context context, boolean unlocked) {

        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(context);
        databaseOperations.open();
        ArrayList monumentList = new ArrayList();
        if (unlocked == true) {
            monumentList = (ArrayList) databaseOperations.getMonuments("YES");
        } else if (!unlocked) {
            monumentList = (ArrayList) databaseOperations.getMonuments("NO");
        }
        databaseOperations.close();
        return monumentList;
    }

    public void setMarkers(GoogleMap mMap, Context context, boolean unlocked) {
        MarkerOptions markerOptions = new MarkerOptions();
        ArrayList monumentList = getMonuments(context, unlocked);
        if (unlocked == true) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(229));
        } else if (!unlocked) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        for (int i = 0; i <= monumentList.size() - 3; i++) {
            double latitude = Double.parseDouble((String) monumentList.get(i));
            i++;
            double longitude = Double.parseDouble((String) monumentList.get(i));
            i++;
            String title = (String) monumentList.get(i);
            i++;
            String category = (String) monumentList.get(i);

            markerOptions.position((new LatLng(latitude, longitude)));
            markerOptions.title(title);
            markerOptions.snippet(context.getString(R.string.mapView_marker_category) + category);
            mMap.addMarker(markerOptions);

        }

    }

    public void cleanMap(GoogleMap mMap) {
        mMap.clear();
    }

    public void changeMarkerToUnlocked(Context context, Double latitude, Double longitude, String name) {
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance(context);
        databaseOperations.open();
        String latString = latitude.toString();
        String lonString = longitude.toString();
        databaseOperations.setMonumentVisited(latString, lonString, name);
        databaseOperations.close();
    }

}

