# frisbee_golf_weather
Group project for CS455 Intro to Networks. An android app that gives weather conditions for local disk golf courses.


WHAT THE APP DOES:

This app sends HTTP requests to a locally ran server that performs CRUD operations on the MongoDB backend. The server was implemented by Travis 
Peterson and can be found here: https://github.com/IndentYourCode/diskit-server
Upon startup, the app will display frisbee golf courses in the Vancouver area, including the name of the course and address. Clicking on one of the
displayed courses will navigate the user to a new screen showing more specific "statuses" about that course, including how crowded, empty, rainy, and
windy that course is. The higher the number the more of "it" there is; the lower the less. These values are incremented using crowd-sourcing, and decrement over time. A new GET 
request to update these values will be sent every ~5 seconds. The bottom of the screen also displays clickable text that allows the user to report if the
course is crowded, empty, etc. This is implemented by sending a POST request to the server upon click, where that status will then be incremented by one. 


DESCRIPTION OF CODE ORGANIZATION:

The main composable that calls the other view models to set up the data for each screen, and the main screen functions themselves, is DiskitApp() in DiskitApp.kt, which in turn is 
called from the app's main entry file: MainActivity. TheDiskitApp() function also sets up the navigation controller and a top bar, which displays a string with the current screen name 
and a back arrow to let the user return to the homepage from the course screen. There are two view models used to manage networking tasks and to set mutable states, one view model 
per screen. There are two files handling the UI on each screen: These are the HomeScreen and CourseScreen files. There are also different directories to organize the types of files and
their functions, such as data (contains CourseInfoRepository to act as intermediary for network calls), model, network (which makes the actual network calls), and ui (stores the different 
screens and view models).


TO RUN THE APP:

First, clone the Diskit server from the repo linked above, build the docker image, and then run it (a more in-depth walkthrough is provided in the Diskit
server repo README. Also, the server must be run locally, but building and running the server as described in the README should automatically ensure this). 
After building the docker image for the server, be sure to initialize the data in Mongo by running add-courses/initialize-db.py from the 
project root directory (should be diskit-server if cloned from GitHub). Build the app and make sure your Android phone or emulator of choice is running. 
The app can be ran by executing from the project root directory:

app/src/main/java/com/example/frisbeegolf/MainActivity.kt


WORKLOAD DISTRIBUTION:

Travis Peterson wrote all of the code for the Diskit server, which is in its own repo linked above in the WHAT THE APP DOES section.

Lukas Renschler wrote all of the code for the app's homepage UI and viewmodel, most of the course screen UI and viewmodel, and all of HTTP requests that the app initiates to 
update and retrieve data from the server, as well as the different intermediaries to separate the UI and data layers.

Tim Tipton wrote all of the weather API servicing and UI to display the weather results.


NOTES ON REFERENCES USED:

Much of the UI was written following the guidance of the Android Basics with Compose tutorials provided by Google for Developers. Specifically,
their tutorials educated on ideal app design by using repositories to separate the UI and data layers, and using dependency injection to make the code more flexible. They also 
introduced recommended practices for retrieving data from the internet, and on implementing screen navigation. Links to the tutorials referenced are provided below:

Repositories, dependency injection, and retrieving data from the internet: https://developer.android.com/courses/pathways/android-basics-compose-unit-5-pathway-2#codelab-https://developer.android.com/codelabs/basic-android-kotlin-compose-add-repository
Screen navigation: https://developer.android.com/courses/pathways/android-basics-compose-unit-4-pathway-2#codelab-https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation