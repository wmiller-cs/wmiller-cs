package test;

import main.Trip;
import main.User;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test

    void userConstructorTest(){
        User user = new User();
        ArrayList<User> friends = new ArrayList<>();
        assertEquals(0, user.getId());
        assertEquals("Matthew Vassar", user.getName());
        assertEquals("Poughkeepsie", user.getLocation());
        assertEquals(friends.toString(), user.getFriends().toString());

        friends.add(user);
        User user2 = new User(1, "Catharine Vassar", "Toronto", friends);
        assertEquals(1, user2.getId());
        assertEquals("Catharine Vassar", user2.getName());
        assertEquals("Toronto", user2.getLocation());
        assertEquals(friends.toString(), user2.getFriends().toString());
    }

    @Test

    void toStringTest() {
        User user = new User();
        ArrayList<User> friends = new ArrayList<>();
        Trip currentTrip = new Trip();
        ArrayList<Trip> allTrips = new ArrayList<>();
        allTrips.add(currentTrip);

        assertEquals("Matthew Vassar", user.toString());

        user.editUser("test",true);
        assertEquals("test", user.toString());

        user.editUser("Toronto",false);
        assertEquals("test", user.toString());

        user.editUser("test","New York City", friends, currentTrip, allTrips);
        assertEquals("test", user.toString());
    }

    @Test
    void getInfoTest(){
        User user = new User();
        ArrayList<User> friends = new ArrayList<>();
        Trip currentTrip = new Trip();
        ArrayList<Trip> allTrips = new ArrayList<>();

        assertEquals(0, user.getId());
        assertEquals("Matthew Vassar", user.getName());
        assertEquals("Poughkeepsie", user.getLocation());
        assertEquals(friends, user.getFriends());
        assertEquals(currentTrip.toString(), user.getCurrentTrip().toString());
        assertEquals(allTrips, user.getAllTrips());

        user.editUser("Catharine Vassar","New York City", friends, currentTrip, allTrips);

        assertEquals("Catharine Vassar", user.getName());
        assertEquals("New York City", user.getLocation());
        assertEquals(friends, user.getFriends());
        assertEquals(currentTrip, user.getCurrentTrip());
        assertEquals(allTrips, user.getAllTrips());

        user.editUser("John Raymond", true);
        assertEquals("John Raymond", user.getName());

        user.editUser("Toronto", false);
        assertEquals("Toronto", user.getLocation());

        friends.add(new User());
        user.editUser(friends, true);
        assertEquals(friends, user.getFriends());

        currentTrip.editTrip("Springside");
        user.editUser(currentTrip);
        assertEquals(currentTrip.toString(), user.getCurrentTrip().toString());

        allTrips.add(new Trip());
        user.editUser(allTrips, false);
        assertEquals(allTrips.toString(), user.getAllTrips().toString());

    }
}