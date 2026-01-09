# CMPU-203 F24 - Team 1G: Spendies

## Iteration 2 Prototype Overview
This prototype has three screens controlled by a bottom navigation bar: Create New Trip, Current Trip, and Friends.

## Create New Trip
This screen allows the user to create a new trip with user input for Location, Budget, Start Date, and End Date.

Note: User input for Start and End Date should follow YYYY-MM-DD format.

## Current Trip
This screen allows the user to view and edit their current trip with user input for Location, Budget, Start Date, and End Date.

Note: User input for Start and End Date should follow YYYY-MM-DD format.

## Friends
This screen allows the user to view a list of their friends, search for friends, and add friends to their current trip.

Note: The only demo friends that exist are Bob (who is already your friend by default) and Susan (who you must search for, add them as a friend, and then are able to add them to your current trip).

## For Testing
If you run the tests and receive the following error:

Execution failed for task ':app:connectedDebugAndroidTest'.
> java.io.IOException: Unable to delete directory '/home/wmiller/Desktop/cs203/project/team-1g/astudio/app/build/outputs/androidTest-results/connected/debug/Pixel_5_API_34_1(AVD) - 14/testlog' after 10 attempts

Delete the "testlog" folder and the error will go away temporarily.

## Miscellaneous Notes For Readers
The following folders and files are either currently unsued or placeholders:
astudio/app/java/*app-name*/data/*
astudio/app/java/*app-name*/ui/*
astudio/app/java/*app-name*/Controller.java
astudio/app/java/*app-name*/MainAdapter.java
astudio/app/res/drawable/background_splash.xml
astudio/app/res/layout/activity_login/*
astudio/app/res/layout/(fragment_home.xml, fragment_notifications.xml, fragment_login_screen.xml, fragment_notifications.xml, item_layout.xml)
astudio/app/res/menu/*
astudio/app/res/navigation/*


SplashScreenActivity.java will be migrated to the built in SplashScreen implementation.

