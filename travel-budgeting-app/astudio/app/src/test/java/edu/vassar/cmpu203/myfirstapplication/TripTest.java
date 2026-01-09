package edu.vassar.cmpu203.myfirstapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

import java.time.LocalDate;

import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;

public class TripTest {

    /***
     * Tests the Trip Class Constructor and checks results using get methods and the toString method
     */
    @Test
    public void tripConstructorTests() {
        Trip myTestTrip = new Trip();
        Trip myOtherTestTrip = new Trip(2,"Norfolk", LocalDate.of(1792, 4, 29), LocalDate.of(1868, 6, 23), 408000);

        assertEquals(myTestTrip.getTripLocation(), "Poughkeepsie");
        assertEquals(myTestTrip.getStartDate(), LocalDate.now());
        assertEquals(myTestTrip.getEndDate(), LocalDate.now());
        assertEquals(myTestTrip.getBudget(), 0, 0);
        assertEquals(myTestTrip.getTripParticipants(), new HashSet<User>());

        assertEquals("Trip Details:\nID: 2\nLocation: Norfolk\nStart Date: 1792-04-29\nEnd Date: 1868-06-23\nBudget: 408000.00\nParticipants: []", myOtherTestTrip.toString());
    }

    /***
     * Tests the Trip Class editTrip method with all parameters
     */
    @Test
    public void editTripTest() {
        Trip myTestTrip = new Trip();

        // Let's go to New York City with a new default user for New Year 2025 with a budget of $100

        myTestTrip.editTrip("NYC", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), 100);

        assertEquals("Trip Details:\nID: " + myTestTrip.getId() + "\nLocation: NYC\nStart Date: 2025-01-01\nEnd Date: 2025-01-01\nBudget: 100.00\nParticipants: []", myTestTrip.toString());
    }

    /***
     * Tests the Trip Class editTrip method with only a single parameter
     */
    @Test
    public void editTripIndividualTest() {
        Trip myTestTrip = new Trip();

        // Actually, let's make this a trip to Boston for Christmas with a budget of $200 with another friend

        myTestTrip.setLocation("Boston");
        myTestTrip.setStartDate(LocalDate.of(2024, 12, 25));
        myTestTrip.setEndDate(LocalDate.of(2024, 12, 25));
        myTestTrip.setId(200);

        assertEquals("Trip Details:\nID: " + myTestTrip.getId() + "\nLocation: Boston\nStart Date: 2024-12-25\nEnd Date: 2024-12-25\nBudget: 0.00\nParticipants: []", myTestTrip.toString());
    }

    /***
     * Tests the Trip Class addParticipant method
     */
    @Test
    public void addParticipantTest() {
        Trip myTestTrip = new Trip();
        User myFriend = new User(1, "Catharine Vassar", "Poughkeepsie");
        User myOtherFriend = new User(2, "John Ellison Vassar", "Poughkeepsie");

        // Let's add a couple of people to the trip
        
        myTestTrip.addParticipant(myFriend);
        myTestTrip.addParticipant(myOtherFriend);

        assertEquals("Trip Details:\nID: " + myTestTrip.getId() + "\nLocation: Poughkeepsie\nStart Date: " + LocalDate.now().toString() + "\nEnd Date: " + LocalDate.now().toString() + "\nBudget: 0.00\nParticipants: [John Ellison Vassar, Catharine Vassar]", myTestTrip.toString());
    }

    /***
     * Tests the Trip Class clearParticipants method
     */
    @Test
    public void clearParticipantsTest(){
        Trip myTestTrip = new Trip();
        User myFriend = new User(1, "Catharine Vassar", "Poughkeepsie");
        User myOtherFriend = new User(2, "John Ellison Vassar", "Poughkeepsie");

        // Let's add a couple of people to the trip

        myTestTrip.addParticipant(myFriend);
        myTestTrip.addParticipant(myOtherFriend);
        myTestTrip.clearParticipants();
        assertEquals("Trip Details:\nID: " + myTestTrip.getId()+ "\nLocation: Poughkeepsie\nStart Date: " + LocalDate.now().toString() + "\nEnd Date: " + LocalDate.now().toString() + "\nBudget: 0.00\nParticipants: []", myTestTrip.toString());


    }
}