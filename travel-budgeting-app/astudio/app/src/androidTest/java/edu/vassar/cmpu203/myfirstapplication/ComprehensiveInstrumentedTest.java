package edu.vassar.cmpu203.myfirstapplication;

import static edu.vassar.cmpu203.myfirstapplication.MainActivity.db;
import static kotlin.random.RandomKt.Random;

import android.os.SystemClock;
import java.util.Random;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import edu.vassar.cmpu203.myfirstapplication.model.User;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ComprehensiveInstrumentedTest {

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This tests whether the display updates in response to the addition of items.
     */
    @Test
    public void allTripsTest() {

        String username = String.valueOf(new Random().nextInt());
        String password = String.valueOf(new Random().nextInt());

        this.typeInput(R.id.editUsername, username);
        this.typeInput(R.id.editPassword, password);

        Espresso.onView(ViewMatchers.withId(R.id.logInButton)).perform(ViewActions.click());

        SystemClock.sleep(500);
        String defaultDashboardTitle = "Trip at Poughkeepsie";
        ViewInteraction dashboardTitleVi = Espresso.onView(ViewMatchers.withId(R.id.dashboardTitle));
        dashboardTitleVi.check(ViewAssertions.matches(ViewMatchers.withText(defaultDashboardTitle)));


        Espresso.onView(ViewMatchers.withId(R.id.friendsButton)).perform(ViewActions.click());
        String friendsTitle = "Add Friends";
        ViewInteraction friendsTitleVi = Espresso.onView(ViewMatchers.withId(R.id.addFriendsText));
        friendsTitleVi.check(ViewAssertions.matches(ViewMatchers.withText(friendsTitle)));

        Espresso.onView(ViewMatchers.withId(R.id.currentTripButton)).perform(ViewActions.click());
        String editTripTitle = "Edit Trip";
        ViewInteraction editTripTitleVi = Espresso.onView(ViewMatchers.withId(R.id.editTripTitle));
        editTripTitleVi.check(ViewAssertions.matches(ViewMatchers.withText(editTripTitle)));

        Espresso.onView(ViewMatchers.withId(R.id.dashboardButton)).perform(ViewActions.click());
        dashboardTitleVi = Espresso.onView(ViewMatchers.withId(R.id.dashboardTitle));
        dashboardTitleVi.check(ViewAssertions.matches(ViewMatchers.withText(defaultDashboardTitle)));

        Espresso.onView(ViewMatchers.withId(R.id.allTripsButton)).perform(ViewActions.click());
        String allTripsTitle = "All Trips";
        ViewInteraction allTripsTitleVi = Espresso.onView(ViewMatchers.withId(R.id.allTrips));
        allTripsTitleVi.check(ViewAssertions.matches(ViewMatchers.withText(allTripsTitle)));

        Espresso.onView(ViewMatchers.withId(R.id.newTripButton)).perform(ViewActions.click());
        String newTripTitle = "Create New Trip";
        ViewInteraction newTripTitleVi = Espresso.onView(ViewMatchers.withId(R.id.newTrip));
        newTripTitleVi.check(ViewAssertions.matches(ViewMatchers.withText(newTripTitle)));

        SystemClock.sleep(500);

        // Context of the app under test.
        String location = "New York";
        double budget = 7.01;

        LocalDate startDate = LocalDate.parse("2025-01-01");
        LocalDate endDate = LocalDate.parse("2025-02-01");
        //String startDate = "2025-01-01";
        //String endDate = "2025-02-01";

        this.typeInput(R.id.editLocation, location);
        this.typeInput(R.id.editBudget, budget);
        this.typeInput(R.id.editStartDate, startDate);
        this.typeInput(R.id.editEndDate, endDate);
        Espresso.onView(ViewMatchers.withId(R.id.saveNewTripButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.allTripsButton)).perform(ViewActions.click());

        String currentTripName = "New York 2025";
        ViewInteraction allTripVi = Espresso.onView(ViewMatchers.withText(currentTripName));
        allTripVi.check(ViewAssertions.matches(ViewMatchers.withText(currentTripName)));

        User friend1 = new User();
        friend1.setName("Bob");
        friend1.setId(99);

        User friend2 = new User();
        friend2.setName("Susan");
        friend2.setId(100);

        db.collection("users").document("99").set(friend1.toMap());
        db.collection("users").document("100").set(friend2.toMap());

        Espresso.onView(ViewMatchers.withId(R.id.friendsButton)).perform(ViewActions.click());

        String buttonText = "Add to this trip";
        this.typeInput(R.id.editFriendUsername, friend1.getName());
        SystemClock.sleep(200);
        Espresso.onView(ViewMatchers.withId(R.id.searchButton)).perform(ViewActions.click());
        SystemClock.sleep(200);
        Espresso.onView(ViewMatchers.withId(R.id.addUserButton)).perform(ViewActions.click());

        ViewInteraction friend1Vi = Espresso.onView(ViewMatchers.withText(friend1.getName()));
        friend1Vi.check(ViewAssertions.matches(ViewMatchers.withText(friend1.getName())));
        Espresso.onView(ViewMatchers.withText(buttonText)).perform(ViewActions.click());

        this.typeInput(R.id.editFriendUsername, friend2.getName());
        SystemClock.sleep(200);
        Espresso.onView(ViewMatchers.withId(R.id.searchButton)).perform(ViewActions.click());
        SystemClock.sleep(200);
        Espresso.onView(ViewMatchers.withId(R.id.addUserButton)).perform(ViewActions.click());

        ViewInteraction friend2Vi = Espresso.onView(ViewMatchers.withText(friend2.getName()));
        friend2Vi.check(ViewAssertions.matches(ViewMatchers.withText(friend2.getName())));

        Espresso.onView(ViewMatchers.withId(R.id.currentTripButton)).perform(ViewActions.click());

        ViewInteraction participant1Vi = Espresso.onView(ViewMatchers.withText(friend1.getName()));
        participant1Vi.check(ViewAssertions.matches(ViewMatchers.withText(friend1.getName())));

        ViewInteraction participant2Vi = Espresso.onView(ViewMatchers.withText(username));
        participant2Vi.check(ViewAssertions.matches(ViewMatchers.withText(username)));
        Espresso.onView(ViewMatchers.withText("Remove")).perform(ViewActions.click());

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.dashboardButton)).perform(ViewActions.click());
        ViewInteraction budgetVi = Espresso.onView(ViewMatchers.withId(R.id.totalBudget));
        budgetVi.check(ViewAssertions.matches(ViewMatchers.withText(String.valueOf(budget))));

        Espresso.onView(ViewMatchers.withId(R.id.newExpenseButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.addExpenseCancelButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.newExpenseButton)).perform(ViewActions.click());
        ViewInteraction addExpenseTitleVi = Espresso.onView(ViewMatchers.withId(R.id.addExpenseTitle));
        addExpenseTitleVi.check(ViewAssertions.matches(ViewMatchers.withText("Add an Expense")));
        this.typeInput(R.id.addExpenseType, "Food");
        this.typeInput(R.id.addExpenseCost, 6.01);
        this.typeInput(R.id.addExpenseMemo, "Burger for dinner");
        Espresso.onView(ViewMatchers.withId(R.id.addExpenseSaveButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.newExpenseButton)).perform(ViewActions.click());
        this.typeInput(R.id.addExpenseCost, 5.99);
        this.typeInput(R.id.addExpenseMemo, "idk");
        Espresso.onView(ViewMatchers.withId(R.id.addExpenseSaveButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.expenseUserTagLabelButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.expenseTypeLabelButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.expenseCostLabelButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.expenseTimeLabelButton)).perform(ViewActions.click());







    }

    private void typeInput(final int viewId, final Object input) {
        Espresso.onView(ViewMatchers.withId(viewId)).perform(ViewActions.typeText(input.toString()));

        Espresso.closeSoftKeyboard(); // close the keyboard to prevent it from occluding other objects
    }
}
