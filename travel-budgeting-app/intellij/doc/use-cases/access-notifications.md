# Access Notifications (Not Implemented)

## 1. Primary actor and goals
_User_: wants to view and accept or deny friend requests, quickly accurately, and safely.

## 2. Other stakeholders and their goals

## 3. Preconditions
User is identified and authenticated.

## 4. Postconditions
* Changes to their friends list are saved

## 5. Workflow

```plantuml
@startuml

skin rose

title Access notifications (casual level)

|#application|User|
|#implementation|System|


|System|
start

:Display incoming friend requests and responses to sent out requests;
|User|
while (stay?) is (yes)
    if (Accept friend request?) then (yes)
        |System|
        :Sets users as friends;
        :Sends confirmation to requester;
    else (no)
        |System|
        :Sends declination to requester;
    endif
    
endwhile(no)
    |System|
    :View current trip;
stop

@enduml
```