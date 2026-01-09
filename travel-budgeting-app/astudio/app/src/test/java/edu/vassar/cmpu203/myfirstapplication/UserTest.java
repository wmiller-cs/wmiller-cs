package edu.vassar.cmpu203.myfirstapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.vassar.cmpu203.myfirstapplication.model.User;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;


import java.util.ArrayList;

public class UserTest {


    /***
     * Tests the User Class Constructor and checks results using get methods and the toString method
     * Tests with an empty friends list and once with a temporary friend
     */
    @Test
    public void userConstructorTest(){
        User user = new User();
        ArrayList<User> friends = new ArrayList<>();
        assertEquals(0, user.getId());
        assertEquals("Matthew Vassar", user.getName());
        assertEquals("Poughkeepsie", user.getLocation());
        assertEquals(friends.toString(), user.getFriends().toString());

        friends.add(user);
        User user2 = new User(1, "Catharine Vassar", "Toronto");
        user2.setFriends(friends);
        assertEquals(1, user2.getId());
        assertEquals("Catharine Vassar", user2.getName());
        assertEquals("Toronto", user2.getLocation());
        assertEquals(friends.toString(), user2.getFriends().toString());
    }

    /***
     * Tests the User Class editUser method
     */
    @Test
    public void editUserTest(){
        User user = new User();
        ArrayList<User> friends = new ArrayList<>();
        Trip currentTrip = new Trip();
        user.getAllTrips().add(currentTrip);
        ArrayList<Trip> allTrips = new ArrayList<>();
        allTrips.add(currentTrip);

        assertEquals(0, user.getId());
        assertEquals("Matthew Vassar", user.getName());
        assertEquals("Poughkeepsie", user.getLocation());
        assertEquals(friends, user.getFriends());
        assertEquals(currentTrip.toString(), (user.getCurrentTrip()).toString());
        assertEquals(allTrips, user.getAllTrips());

        user.editUser("Catharine Vassar","New York City", friends, allTrips);

        assertEquals("Catharine Vassar", user.getName());
        assertEquals("New York City", user.getLocation());
        assertEquals(friends, user.getFriends());
        assertEquals(currentTrip, user.getCurrentTrip());
        assertEquals(allTrips, user.getAllTrips());

        user.setName("John Raymond");
        assertEquals("John Raymond", user.getName());

        user.setLocation("Toronto");
        assertEquals("Toronto", user.getLocation());

        friends.add(new User());
        user.setFriends(friends);
        assertEquals(friends, user.getFriends());

        currentTrip.setLocation("Springside");
        user.setCurrentTrip(currentTrip);
        assertEquals(currentTrip.toString(), user.getCurrentTrip().toString());

        allTrips.add(new Trip());
        user.setAllTrips(allTrips);
        assertEquals(allTrips.toString(), user.getAllTrips().toString());

        user.setPassword("never");
        assertEquals(user.getPassword(), "never");

    }
}