# View Dashboard

## 1. Primary actor and goals
_User_: wants to view their current trip and add expenses to their trip. Wants fast, accurate information with ability to sort data.

## 2. Other stakeholders and their goals

## 3. Preconditions
User is identified and authenticated.

## 4. Postconditions
* Changes to their trip information are saved

## 5. Workflow

```plantuml
@startuml

skin rose

title View Dashboard (casual level)

|#application|User|
|#implementation|System|


|System|
start
:Display trip information;
|User|
while (stay?) is (yes)
    if (Click on New Expense) then (yes)
        |System|
        :Navigate to Add Expense;
        stop
    else (no)
        |User|
        if (Click on a sort Button) then (yes)
            |User|
            :Display filtered information;
        else (no)
        endif
    endif
endwhile(no)
    |User|
    :Select other section of app;
    |System|
    :Navigate to that section of app;
stop

@enduml
```
# Sequence Diagram

```plantuml

@startuml

actor User
participant UI
participant Controller
participant AddExpenseView

UI -> User : display current trip information
UI -> User : display button to create a new expense
UI -> User : display buttons to sort expenses
User -> UI : click 'New Expense' button
UI -> Controller : onNewExpenseButtonClicked()
Controller -> AddExpenseView : displayFragment(new AddExpenseView)
User -> UI : click on a sorting button
UI -> Controller : onExpensesSorted(RecyclerView, List<ExpenseItem>)
Controller -> UI : refresh recycler view


@enduml

```