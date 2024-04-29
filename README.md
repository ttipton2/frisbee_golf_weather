# frisbee_golf_weather
Group project for CS455 Intro to Networks. An android app that gives weather conditions for local disk golf courses.


WHAT THE APP DOES:

This app sends HTTP requests to a locally ran server that performs CRUD operations on the MongoDB backend. The server was implemented by Travis 
Peterson and can be found here: https://github.com/IndentYourCode/diskit-server
Upon startup, the app should display frisbee golf courses in the Vancouver area, including the name of the course and address. Clicking on one of the
displayed courses will navigate the user to a new screen showing more specific "statuses" about that course, including how crowded, empty, rainy, and
windy that course is using crowd-sourcing. A new GET request to update these values will be sent every ~5 seconds. The bottom of the screen also displays clickable text that allows the user to report if the
course is crowded, empty, etc. This is implemented by sending a POST request to the server upon click, where that status will then be incremented by
one. 


DESCRIPTION OF CODE ORGANIZATION:




TO RUN THE APP:

First, clone the Diskit server from the repo linked above, build the docker image, and then run it (a more in-depth walkthrough is provided in the Diskit
server repo README. Also, the server must be run locally, but building and running the server as described in the README should automatically ensure this). 
After building the docker image for the server, be sure to initialize the data in Mongo by running add-courses/initialize-db.py from the 
project root directory (should be diskit-server if cloned from GitHub). Build the app and make sure your Android phone or emulator of choice is running. 
The app can be ran by executing from the project root directory:

app/src/main/java/com/example/frisbeegolf/MainActivity.kt


NOTES ON REFERENCES USED:

Much of the UI was written following the guidance of the Android Basics with Compose tutorials provided by Google for Developers. Specifically,
their tutorials educated on ideal app design by using repositories to separate the UI and data layers, and using dependency injection to make more
flexible code. They also introduced recommended practices for retrieving data from the internet, and on implementing screen navigation.
Links to the tutorials referenced are provided below:

Repositories, dependency injection, and retrieving data from the internet: https://developer.android.com/courses/pathways/android-basics-compose-unit-5-pathway-2#codelab-https://developer.android.com/codelabs/basic-android-kotlin-compose-add-repository
For screen navigation: https://developer.android.com/courses/pathways/android-basics-compose-unit-4-pathway-2#codelab-https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation