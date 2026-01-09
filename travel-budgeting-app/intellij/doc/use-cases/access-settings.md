# Access Settings (Not Implemented)

## 1. Primary actor and goals
_User_: wants to view and edit their personal information. Wants fast, accurate information, and the ability to quickly and easily change it.

## 2. Other stakeholders and their goals

## 3. Preconditions
User is identified and authenticated.

## 4. Postconditions
* Changes to their personal information are saved

## 5. Workflow

```plantuml
@startuml

skin rose

title Access settings (casual level)

|#application|User|
|#implementation|System|


|System|
start

:Display personal information;
|User|
while (stay?) is (yes)
    :Edit information;
    |System|
    :Validate edits;
    :Record edits;
    :Display personal information;
endwhile(no)
    |System|
    :View current trip;
stop

@enduml
```