# Minor Programmeren Project - WikiWalk (Amsterdam Edition)
![alt text](https://github.com/paulberinde/project/blob/master/doc/logotest3.png)
## Problem Definition

Today people are more and more connected, and have access to a variety of information sources when visiting places, both old and new. 
This information is in many cases fragmented, or non-interactive. Furthermore, it mostly provides anything of value to tourists, whereas
even locals might sometimes want to gain new insight into their city.

Instead of only catering to newcomers, WikiWalk aims to help its users (re)discover any city, by providing a customizable experience that
caters both to the first timers, as well as locals, feeding interesting facts and information about places you walk by.

## Product Features

WikiWalk aims to integrate the flexibility of Google Maps and the large amount of data available in public repositories such as Wikipedia
in a framework that allows for the users to discover new facts about the city they're currently in literally at their own pace. 
Note: Elements not marked as (MVP) are considered optional.

+ Allows for a tour selection or a free-roam mode (MVP)
+ Tracks checked location history and will not re-notify for already checked PoIs (Points of Interest)(MVP)
+ Allows for filtering of PoIs based on preference
+ Allows for NO GPS mode (lite mode), though with certain features missing (MVP)
+ Game mode: Tracks visited PoIs and user receives points based on visits
+ Allows for offline data storage (maps/PoI information) (to be investigated)

## App Concept Screens
![alt text](https://github.com/paulberinde/project/blob/master/doc/Start%20Screen%202.png)
![alt text](https://github.com/paulberinde/project/blob/master/doc/Free-Roam.jpg)

## Data Sources and Processing
+ Two main data sources will be a)map data (provided via Google Maps) and b) Wikipedia/ other wiki data (provided via MediaWiki/Wikia APIs)
and/or any other relevant datasources (TBD)
+ The GoogleMaps API allows for the use of blank maps, or Maps with custom selected information. Integration should not pose any issues.
+ The MediaWiki API unofficially supports Wikipedia. The output data is provided in common formats (such as JSON, PHP).
+ A SQLite or similar DB is required for storing user history.
+ For the option to store offline data (map or Wiki data) a protocol on how to create this as well as a controller within the app should
be created. The possibility and the technology for this is under investigation.
+ Any further data sources to be decided.


## Application Components and Layout
The application will need to have separate handlers for Google Maps, MediaWiki, as well as SQLite; lastly if possible, a way to manage offline
data should also exist. 

Upon an event registered via Google Maps+GPS (such as being in vicinity of a PoI), the list of PoIs should be generated. This list should then 
be modified based User History found in SQLite. On user choice (click on item) the relevant data is fetched from either the phone 
or Wikipedia/other relevant source.

## External Components/ APIs
The following components will be required:
+ Google Maps API
+ MediaWiki API
+ SQLite
+ APIs for alternate data sources (under investigation)

## Possible Technical Issues and Workarounds
+ It is currently unclear whether the GoogleMaps API will allow for selecive population of the maps based on certain specific requirements.
Alternate map APIs are being investigated as suitable alternatives.

+ Wikipedia might not be exhaustive enough for all locations, so alternate information might have to be found.

+ Unknown whether GoogleMaps allows for offline storage of map data outside the dedicated app.


## Similar Applications Already Available
Amsterdam Travel Guide is one of the apps that fills a similar need to WikiWalk. However, it is a general app mixing landmarks with 
shopping areas, whereas WikiWalk aims to cover mostly landmarks. While Amsterdam Travel Guide Allows to filter out unwanted information,
what is left is very sparse (see below):
![alt text](https://github.com/paulberinde/project/blob/master/doc/Screenshot_2016-05-30-16-11-32.png)


PocketGuide by PocketGuide Inc. is another app offering GPS - enabled tour guides. Due to the nature of the app I am only able to infer based on user reviews on what the issues are, such as the free version being barely functional online in order to push for the purchase of
offline content, in the form of audio guides. The app also forces a certain rigueur on the user, having to partake in an audio tour,
rather than the ad-hoc, no-commitment approach proposed by WikiWalk.

