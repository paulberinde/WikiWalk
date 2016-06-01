#Technical Design Document

## 1. Introduction
WikiWalk is an application that combines the output of Google Maps with Wikipedia data, for relevant locations on a map. It consists of a small number of screens (activities), with simple navigation. More in-depth information is 
provided to the users via a series of cards (through the use of a CardViews).  

## Component /Module Interactions

![alt-text](https://github.com/paulberinde/project/blob/master/doc/Interactions%20-%20New%20Page.png)

## 3. APIs,Frameworks, Plugins 
The development framework for this application is Android Studio. No other frameworks are required based on the current design of the application.

The two key APIs used in WikiWalk are the GoogleMaps API and the WikiMedia API (for Wikipedia entries). It is not yet known whether further Google APIs (such as Places), or other data sources are needed to ensure coverage of landmarks. 

No plugins are currently required by the application, but it does make extensive use of phone features such as location information.

##4. Data sources

The two main data sources for the application are the GoogleMaps API and WikiMedia API, one providing the map canvas for the application, while the other provides the information content. Whether further data sources are required is to be decided after the initial round of prototyping.

The GoogleMaps API provides a map in a scroll-able view based provided parameters. No changes are required for this data.

Data requested from the WikiMedia API is received in JSON, XML, REST or YAML. Regardless of the choice this data will need to be parsed and formatted to be usable inside the app.


##5. Database Tables, Fields
A SQLite DB will be used for storing visited locations, in order to prevent repeat notifications. These elements will be loaded with a different marker on a map compared to not yet visited locations.


A first table will store the following information (field names, types and overall table layout are subject to change):

1. ID (unique INT)
2. PoI(Point of Interest)Name (Unique VARCHAR)
3. PoI GPS Lattitude (FLOAT)
4. PoI GPS Longitude (FLOAT)
3. PoI Image link (VARCHAR)
4. PoI short description (Taken from Wikipedia) (VARCHAR or BLOB)
5. PoI Visited date and time (DATETIME)

This database will also serve in populating the CardView used for the user's collection of visited monuments (Accessible via the Main Screen or Menu).


A second table will be used for Storing Tour Data (field names, types and overall table layout are subject to change):

1. ID (UNIQUE INT)
2. TOUR NAME (VARCHAR)
3. STEP NUMBER (INT)
4. PoI(Point of Interest)Name (VARCHAR)
5. PoI GPS Lattitude (FLOAT)
6. PoI GPS Longitude (FLOAT)
7. PoI Image link (VARCHAR)
8. PoI short description (Taken from Wikipedia) (VARCHAR or BLOB)
9. Visited (BOOLEAN). This value will be reset at every tour start.

Tour name and Step number will serve jointly as the database key.

