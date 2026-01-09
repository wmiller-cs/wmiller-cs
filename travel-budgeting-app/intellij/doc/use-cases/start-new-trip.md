# Start New Trip

## 1. Primary actor and goals
_User_: wants to start a new trip, quickly and easily. Wants easily modifiable information that is safe and well displayed.

## 2. Other stakeholders and their goals

## 3. Preconditions
User is identified and authenticated.

## 4. Postconditions
* Their new trip information is saved

## 5. Workflow

```plantuml
@startuml

skin rose

title Start new trip (casual level)

|#application|User|
|#implementation|System|


|System|
start

:Display boxes for trip information;
|User|
while (stay?) is (yes)
    :Edit information;
    |System|
    :Validate edits;
    :Record edits;
    :Display boxes with updated information;
endwhile(no)
    |System|
    if (Save trip?) then (yes)
        :Create new trip using given information;
    else (no)
    endif
    |System|
    :View current trip;
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

UI -> User : display boxes for trip information
User -> UI : add trip information
User -> UI : click 'save information'
UI -> Controller : editTrip(info)
Controller -> "currentTrip : Trip" : editTrip(info)


@enduml

```