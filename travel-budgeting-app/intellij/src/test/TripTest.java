package test;

import main.User;
import main.Trip;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class TripTest {

    @Test
    void tripConstructorTests() {
        Trip myTestTrip = new Trip();
        Trip myOtherTestTrip = new Trip("Norfolk", LocalDate.of(1792, 4, 29), LocalDate.of(1868, 6, 23), 408000);

        assertEquals(myTestTrip.getTripLocation(), "Poughkeepsie");
        assertEquals(myTestTrip.getTripStart(), LocalDate.now());
        assertEquals(myTestTrip.getTripEnd(), LocalDate.now());
        assertEquals(myTestTrip.getBudget(), 0);
        assertEquals(myTestTrip.getTripParticipants(), new HashSet<User>());

        assertEquals("Trip Details:\nLocation: Norfolk\nStart Date: 1792-04-29\nEnd Date: 1868-06-23\nBudget: 408000.00\nParticipants: []", myOtherTestTrip.toString());
    }

    @Test
    void editTripTest() {
        Trip myTestTrip = new Trip();

        // Let's go to New York City with a new default user for New Year 2025 with a budget of $100

        myTestTrip.editTrip("NYC", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), 100);

        assertEquals("Trip Details:\nLocation: NYC\nStart Date: 2025-01-01\nEnd Date: 2025-01-01\nBudget: 100.00\nParticipants: []", myTestTrip.toString());
    }

    @Test
    void editTripIndividualTest() {
        Trip myTestTrip = new Trip();

        // Actually, let's make this a trip to Boston for Christmas with a budget of $200 with another friend

        myTestTrip.editTrip("Boston");
        myTestTrip.editTrip(LocalDate.of(2024, 12, 25), true);
        myTestTrip.editTrip(LocalDate.of(2024, 12, 25), false);
        myTestTrip.editTrip(200);

        assertEquals("Trip Details:\nLocation: Boston\nStart Date: 2024-12-25\nEnd Date: 2024-12-25\nBudget: 200.00\nParticipants: []", myTestTrip.toString());
    }

    @Test
    void addParticipantTest() {
        Trip myTestTrip = new Trip();
        User myFriend = new User(1, "Catharine Vassar", "Poughkeepsie", new ArrayList<User>());
        User myOtherFriend = new User(2, "John Ellison Vassar", "Poughkeepsie", new ArrayList<User>());

        // Let's add a couple of people to the trip
        
        myTestTrip.addParticipant(myFriend);
        myTestTrip.addParticipant(myOtherFriend);

        assertEquals("Trip Details:\nLocation: Poughkeepsie\nStart Date: " + LocalDate.now().toString() + "\nEnd Date: " + LocalDate.now().toString() + "\nBudget: 0.00\nParticipants: [John Ellison Vassar, Catharine Vassar]", myTestTrip.toString());
    }
}