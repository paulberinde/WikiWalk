# Process book: WikiWalk

## Day 1
Day 1 was spent doing initial research on APIs for Google Maps and Wikipedia, as well as formalizing the application concept. The concept, along
with further documentation and some concept screens have been uploaded as the project proposal, after discussion with the TAs.
One interesting discovery made during the day was the application JustinMind Prototyper, which can be used for easy prototyping.

Three logo versions have been created, with this one serving as current choice:
![alt text](https://github.com/paulberinde/project/blob/master/doc/logotest3.png)

## Day 2
Day 2 was spent doing the following:
+ Setting up Google Maps API and building a simple implementation to test it
+ Research MediaWiki API
+ Start on the design document

## Day 3
Day 3 was spent doing the following:
+ Prototype work: Main screen design and Google Maps API research
+ Technical design document creation (Modules/ Classes and interactions, used API-s, data sources, database setup)
 
## Day 4
Day 4 was spent doing the following:
+ Prototype work: Main screen design refining, logo creation and GoogleMAps API behaviour coding


## Day 5 
Day 5 was spent further refining the prototype and researching the following elements:
+ Removing data from the default GoogleMap 
+ OpenMaps API as alternative

## Day 6
+ OpenMaps API has been abandoned despite some advantages (cacheable map, etc) due to added complexity
+ Rijksmonumenten API investigation for list of Rijksmonumenten

## Day 7 
+ Apache SOLR syntax queries test for Rijksmonumenten API
+ Download of DB in Access format for comparison of results.

## Day 8 
+ Successful download of data via API
+ Local database approach favoured due to large size of data (17MB full data, 10 MB Trimmed Data (no woonhuizen)
+ CSV output of API query generated errors, method to convert JSON to CSV researched and applied
+ Used SQLITE Browser to create db based on CSV

## Day 9 
+ Research adding existing DB to app
+ Built DB Handler and Operations Classes
+ Errors encounted when trying to populate list based on DB data (null pointer exception)
+ Alpha version currently in non-working state













