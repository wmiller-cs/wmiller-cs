# Phase 2 Test Report

## Unit tests
All unit tests can be found under ``intellij/src/test``

Using the JUnit package, we tested the functionality of following classes:
* Controller
* User
* Trip

## System test
The system test was conducted by play testing the current prototype, entering expected inputs, as well as inputs that do not fit the prompt to see if the prototype would break. From this test, the prototype was able to handle expected inputs and react accordingly, as well as properly respond to unexpected inputs.

Below is the console log of our system test:
```
Hello! Welcome to spendies, an app that helps you manage your traveling budgets.
Please enter your name.
Will
Hey, Will! Would you like to start a new trip (enter a) or manage your current trip (b)?
a
You may manage the trip's overall budget, dates, and location.
Please enter your trip's total budget in USD (like 5.99).
700
Please enter your trip's start date (Year-Month-Day). (Leave blank for today)
2024-12-09
Please enter your trip's end date (Year-Month-Day). (Leave blank for today)
2024-12-15
Please enter your trip's location.
New York City
Your information has been saved.
Enter 'q' to quit or anything else to stay.

Hey, Will! Would you like to start a new trip (enter a) or manage your current trip (b)?
b
Your current trip is shared with 0 friend(s): []
Would you like to add friends to your current trip (y/n)?
y
Which friend would you like to add to your current trip?
[John]
Al
Al doesn't exist in your friends list.
Your current trip is shared with 0 friend(s): []
Would you like to add friends to your current trip (y/n)?
y
Which friend would you like to add to your current trip?
[John]
John
You have added John to your current trip.
Your current trip is shared with 1 friend(s): [John]
Would you like to add friends to your current trip (y/n)?
y
Which friend would you like to add to your current trip?
[John]
John
John already has access to your current trip.
Your current trip is shared with 1 friend(s): [John]
Would you like to add friends to your current trip (y/n)?
n
You may manage the trip's overall budget, dates, and location.
Your trip's current overall budget is $700.00.
Please enter your trip's new overall budget in USD (like 5.99) or enter 's' to keep it the same.
15.67
Your trip's current start date is 2024-12-09.
Please enter your trip's new start date (Year-Month-Day) or enter 's' to keep it the same.
s
Your trip's current end date is 2024-12-15.
Please enter your trip's new end date (Year-Month-Day) or enter 's' to keep it the same.
2024-12-17
Your trip's current location is New York City.
Please enter your trip's location or enter 's' to keep it the same.
Albany
Your information has been saved.
Enter 'q' to quit or anything else to stay.

Hey, Will! Would you like to start a new trip (enter a) or manage your current trip (b)?
b
Your current trip is shared with 1 friend(s): [John]
Would you like to add friends to your current trip (y/n)?
n
You may manage the trip's overall budget, dates, and location.
Your trip's current overall budget is $15.67.
Please enter your trip's new overall budget in USD (like 5.99) or enter 's' to keep it the same.
s
Your trip's current start date is 2024-12-09.
Please enter your trip's new start date (Year-Month-Day) or enter 's' to keep it the same.
2024-12-20
Your trip's current end date is 2024-12-17.
Please enter your trip's new end date (Year-Month-Day) or enter 's' to keep it the same.
2024-12-15
Invalid end date.
Your trip's current start date is 2024-12-09.
Please enter your trip's new start date (Year-Month-Day) or enter 's' to keep it the same.
2024-12-09
Your trip's current end date is 2024-12-17.
Please enter your trip's new end date (Year-Month-Day) or enter 's' to keep it the same.
2024-12-12
Your trip's current location is Albany.
Please enter your trip's location or enter 's' to keep it the same.
s
Your information has been saved.
Enter 'q' to quit or anything else to stay.

Hey, Will! Would you like to start a new trip (enter a) or manage your current trip (b)?
b
Your current trip is shared with 1 friend(s): [John]
Would you like to add friends to your current trip (y/n)?
n
You may manage the trip's overall budget, dates, and location.
Your trip's current overall budget is $15.67.
Please enter your trip's new overall budget in USD (like 5.99) or enter 's' to keep it the same.
hey
You did not enter a valid input.
Hey, Will! Would you like to start a new trip (enter a) or manage your current trip (b)?
a
You may manage the trip's overall budget, dates, and location.
Please enter your trip's total budget in USD (like 5.99).
65
Please enter your trip's start date (Year-Month-Day). (Leave blank for today)

Please enter your trip's end date (Year-Month-Day). (Leave blank for today)

Please enter your trip's location.
Poughkeepsie
Your information has been saved.
Enter 'q' to quit or anything else to stay.

Hey, Will! Would you like to start a new trip (enter a) or manage your current trip (b)?
b
Your current trip is shared with 1 friend(s): [John]
Would you like to add friends to your current trip (y/n)?
n
You may manage the trip's overall budget, dates, and location.
Your trip's current overall budget is $65.00.
Please enter your trip's new overall budget in USD (like 5.99) or enter 's' to keep it the same.
s
Your trip's current start date is 2024-11-01.
Please enter your trip's new start date (Year-Month-Day) or enter 's' to keep it the same.
s
Your trip's current end date is 2024-11-01.
Please enter your trip's new end date (Year-Month-Day) or enter 's' to keep it the same.
s
Your trip's current location is Poughkeepsie.
Please enter your trip's location or enter 's' to keep it the same.
s
Your information has been saved.
Enter 'q' to quit or anything else to stay.
q

Process finished with exit code 0
```