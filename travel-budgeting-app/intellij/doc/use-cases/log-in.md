# Log In

## 1. Primary actor and goals
_User_: wants to log in to the app, quickly, easily, and effectively.

## 2. Other stakeholders and their goals

## 3. Preconditions
User has entered the app.

## 4. Postconditions
* Changes to their personal information are saved

## 5. Workflow

```plantuml
@startuml

skin rose

title Log in (casual level)

|#application|User|
|#implementation|System|

|System|
start

:Display log in button and create account button;
|User|
if (Click on) then (log in)
    |System|
    :Display identification box;
    while (Information) is (incomplete/invalid)
    
    |User|
    :Enter identifying information into box;
    |System|
    endwhile (complete/valid)
          
else (create account)
    |System|
    :Display empty boxes requesting basic account information;
    |User|
    :Enter basic account information;
    |System|
    :Save information;
    :Create account for user using inputted information;
  
endif
|System|
:Log user in;
:View current trip;
stop

@enduml
```