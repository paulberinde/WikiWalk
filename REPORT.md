#Final Report - WikiWalk 

## 1. Introduction
WikiWalk is an application built around the GoogleMaps API and Google Location Services, while also using a DB containing query data from the Rijksmonumenten API. Data is also loaded from Wikipedia in a WebView, and WikiMedia Commons images used in the Monument Collection. the Picasso plugin (http://square.github.io/picasso/) is used for easy remote image management(download, recycle of views, etc.)


## 2. Technical design
The application consists of two main parts: the Free-Roam mode and the Collection View. Separation of concerns has been followed to the best of the student's ability when designing the application.

###2.1 Free-Roam Mode
Free-roam Mode consists of one activity (Maps Activity) and several related classes: LocationHandler and MarkerHandler are used for managing location data and Marker activities(cleaning the map, setting markers, updating a marker as visited in the database). DatabaseHandler is in charge of creating the Database; and DatabaseOperations contain  methods which implement the various CRUD operations needed to fetch or update data.

On a change in location, the handleNewLocation method in MapsActivity updates the map so that it's centered on the user's position and then requests a list of coordinates for not yet discovered markers; it computes the distance between the user's position and the position of each value in this list, and if the distance is 20 meters or less, then the relevant monument is marked as unlocked, a toast message is provided and the marker is colored blue (for visited) on the next update). An unlocked monument can be checked in the collection.

###2.2 Collection
The Collection consists of two activities - CollectionActivity and CollectionItemActivity.
Collection activity makes use of an imageAdapter in order to implement the Gridview in which the unlocked monuments are visible and can be selected for individual view. Thumbnails of images are loaded in the Gridview to reduce data usage.

On click the relevant element is loaded in the CollectionItemActivity; this element is presented with a higher-resolution image and the following data from the database: Name, Address, Location, Monument Category.

This Activity also contains a button which, when pressed, will open a WebView and load the relevant Wikipedia article if it exists. If there is none, a toast will inform the user of the situation. On pressing the back button while the webView is opened, it is made invisible, so that in case the button was pressed accidentally, the data is kept in memory. On return to the CollectionActivity, the WebView data is purged through the normal Android Activity Lifecycle.


##3. Activities and Classes (Alphabetical in-Depth View)
###3.1 CollectionActivity

Public class CollectionActivity extends Activity

CollectionActivity contains only the OnCreate method, in which the data for the gridview is queried (via Databaseoperations.getCollection()).

The GridView is also initialized and the imageAdapter is set, as well as the onItemClickListener, which contains an intent to go to the CollectionItemActivity. The Bundle passed between the Activities contains the rowID for the monument which needs to be shown.

Finally text is set for the views in the layout.


###3.2 CollectionItemActivity

public class CollectionItemActivity extends Activity 

CollectionItemActivity loads the relevant data into the layout with the aid of DatabaseOperations.getCollectionItem() and the Picasso plugin. once this is done an onClickListener is set on the "Ga Naar Wikipedia" button (R.id.WikipediaButton).
When pressed, the openWebView method is called, which takes one argument, the URL for the requested page.

In case the URL is correct, the page is loaded, in case there is no url, the user is informed via a toast.

The OnBackPressed method is overridden with the following condition: if the WebView is visible when the back button is pressed, then the WebWiew is made invisible. This was implemented so that the Wikipedia page isn't reloaded endlessly for the same item, and to reduce the number of activities.
If the webView is invisible, then the back button works as designed.

###3.3 DatabaseHandler

public class DatabaseHandler extends SQLiteAssetHelper

SqLite asset helper is used here because the application comes with a pre-existing database.

DatabaseHandler is the interface to the database itself.

### 3.4 DatabaseOperations

public class DatabaseOperations 

Apart from the generic methods used to get an instance, open and close a database, the following methods, which are all SQLite Queries, are implemented:
	
++getMonuments(String isUnlocked): Returns abc_lat, abc_lon, abc_objectnaam, abc_categorie for either locked or unlocked monuments. Used for marker related activities.

++getCollection: Returns a list of thumbnail urls for the Collection Gridview
++getRowID: Returns the row ID for the thumbnails.
++getCollectionItem(int rowId) Returns the fullscreen image url, name, address, location and category for the monument in rowID

++setMonumentVisited(String latitude, String longitude, String name): sets the monument that has a matching abc_lat, abc_lon and abc_objectnaam to visited=YES. 

### 3.5 ImageAdapter
public class ImageAdapter extends ArrayAdapter

Standard ImageAdapter based on example from Google Documentation. For each Imageview., it loads the relevant image from a URL and re-sizes it accordingly with the aid of Picasso.

### 3.6 LocationHandler

public class LocationHandler implements  GoogleApiClient.ConnectionCallbacks,        GoogleApiClient.OnConnectionFailedListener, LocationListener

This class is a mostly intact reproduction of the example provided at https://github.com/treehouse/android-location-example-refactored .

It contains Overrides for the default GoogleAPIClient methods. and also has a callback to the method handleNewLocation found in the MapsActivity.

    
### 3.7 MainActivity

public class MainActivity extends Activity

Main screen for the app, contains two clickable buttons and related Intents (goToMap and goToCollection)

### 3.8 MapsActivity

public class MapsActivity extends FragmentActivity implements LocationHandler.LocationCallback 


Maps Activity is also partly based on the example provided at https://github.com/treehouse/android-location-example-refactored .
Apart from the MapsActivity relevant Overrides, the class contains setupMap and handleNewLocations methods. which are responsible for the initial setup and update of the map.

checkForUnlocks is a method which compares the distance between the current location and all locked (red) markers on the map, and in case it is 20 meters or less, it unlocks the relevant entry in the Collection via an SQLite Query.

###3.9 MarkerHandler

public class MarkerHandler

MarkerHandler implements the main functionality for setting, updating and removing markers from the map.

setMonuments is is used in the checkforUnlocks method. It interfaces with DatabaseOperations to get a list of monuments.

setMarkers is the method used to populate the markers on the map. One of its arguments is a boolean, which depending on the value will result in setting the locked or unlocked monument markers.

cleanMap takes the GoogleMap instance as argument, and runs the GoogleMap.clear method.

changeMarkerToUnlocked accesses the DB via DBoperations and changes the value of visited to YES for the monument with the matching name and latitude and longitude.

##4. Challenges

The development process of WikiWalk was one that met hurdles rather often. Firstly, the GoogleMaps API does not allow for an empty map, and even with no other APIs connected, the level of detail is on occasion too much. Alternatives were prospected but eventually rejected due to lack of time and experience.

Another major hurdle was finding an aggregated datasource which fits the initial mission statement for the app, all the while providing rich information with low data usage. Thanks to discussions with peers, the RijksMonumenten API was chosen for monument location. Parsing the data from the API is also problematic due to the lack of experience with Apache SOLR. A solution was found in downloading the data in JSON, converting it to CSV and then processing it into a SQLITE Database, due to the native CSV version being bug-ridden.

Data quality persisted as an issue even after this step; the database has been trimmed, purged of most duplicates, and a large amount of incorrectly categorized items were removed. Due to the high level of buildings/sights declared as monuments in Amsterdam, it was decided to remove all houses (woonhuizen). This and the trimming and correction of duplicates resulted in a decrease from around 7500 monuments to 415. Lastly, because of the scarcity of information available in English, a decision was made to change the language in the app to Dutch.

Lastly several issues were encountered while working with location detection. this has resulted in using a better documented, but deprecated method for map management. 

Due to the series of issues mentioned above, the following features have been removed from the final product: 

++No Lite Mode, since the entire experience depends on usage of user location.
++No Tour Mode
++ No Game Mode


Nonetheless, the final product offers a unique way to walk around the city and discover interesting, lesser known monuments, which was the initial goal of the product.


##5. Design decisions
Initially, the choice was to store different data in the database. Thanks to Rijksmonumenten API, most of the data is available offline in a DB, while images and Wikipedia pages can be loaded on request. This solution is snappy, has a smaller footprint on the phone, and is also rather economical when it comes to data usage.
Other than this, the only significant change was switching the language to Dutch; it did not make sense to have an English app which presents all the data in Dutch.

A tour-mode and/or game mode would be indeed beneficial, and given more time and the current level of experience (as opposed to the level at the start of the project) would probably result in a more feature-rich application.
 























 





 


	

