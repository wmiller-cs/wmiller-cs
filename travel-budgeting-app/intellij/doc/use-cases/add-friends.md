# Add Friends

## 1. Primary actor and goals
_User_: wants to connect and become friends with other user of the app. Wants fast, accurate connections, and safety while interacting.

## 2. Other stakeholders and their goals

## 3. Preconditions
User is identified and authenticated.

## 4. Postconditions
* Changes to user's friends list is updated

## 5. Workflow

```plantuml
@startuml

skin rose

title Add friends (casual level)

|#application|User|
|#implementation|System|

|System|
start

:Display search bar and current friends (with 'add friend to trip' next to each friend);
while (stay?) is (yes)
    |User|
    if (Click on) then (add friend to trip)
        |System|
        :Add friend to an existing trip;
    else (search users)
        |System|
        :Search for intended friend;
        if (Intended friend exists) then (yes)
            :Display intended friend;
            |User|
            :Add friend;
            |System|
            :Add friend in database;
        else (no)
            |System|
            :Display that no user was found;
       endif
    endif
    |System|
    :Display search bar and current friends;
endwhile (no)

    |User|
    :Navigate to section of choice;
stop

@enduml
```

# Sequence Diagram

```plantuml

@startuml

actor User
participant UI
participant Controller
participant "currentTrip : Trip"

UI -> User : display search bar and a list of current friends (with 'add friend to trip' next to each friend)
User -> UI : click on 'add friend to trip' next to a friend
UI -> Controller : addParticipant(friend)
Controller -> "currentTrip : Trip" : addParticipant(friend)

@enduml

```