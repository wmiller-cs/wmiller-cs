package test;

import main.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    User user = new User();
    Controller controller = new Controller(user);

    @Test
    void UserTest(){

        ArrayList<User> friends = new ArrayList<>();
        Trip currentTrip = new Trip();
        ArrayList<Trip> allTrips = new ArrayList<>();

        controller.editUser("Catharine Vassar","New York City", friends, currentTrip, allTrips);

        assertEquals("Catharine Vassar", controller.getName());
        assertEquals("New York City", controller.getLocation());
        assertEquals(friends, controller.getFriends());
        assertEquals(currentTrip, controller.getCurrentTrip());
        assertEquals(allTrips, controller.getAllTrips());

        controller.editUser("Ray", true);
        assertEquals("Ray", controller.getName());

        controller.editUser("Toronto", false);
        assertEquals("Toronto", controller.getLocation());

        ArrayList<User> friends2 = new ArrayList<>();
        friends2.add(new User());
        controller.editUser(friends2, true);
        assertEquals(friends2, controller.getFriends());

        Trip currentTrip2 = new Trip();
        currentTrip2.editTrip(9);
        controller.editUser(currentTrip2);
        assertEquals(currentTrip2, controller.getCurrentTrip());


        List<Trip> allTrips2 = new ArrayList<>();
        allTrips2.add(new Trip());
        controller.editUser(allTrips2, false);
        assertEquals(allTrips2, controller.getAllTrips());
    }

    @Test
    void searchFriendsTest(){
        ArrayList<User> pBFriends = new ArrayList<>();
        User pB = new User(11, "Elizabeth Bradley", "Vassar College", pBFriends);
        ArrayList<User> friends = new ArrayList<>();
        friends.add(pB);

        controller.editUser(friends, true);
        User friendsSearchResult = controller.searchFriends("Elizabeth Bradley", 2);
        assertEquals(pB, friendsSearchResult);
        
        friendsSearchResult = controller.searchFriends("Vassar College", 3);
        assertEquals(pB, friendsSearchResult);

        friendsSearchResult = controller.searchFriends(pBFriends, 4);
        assertEquals(pB, friendsSearchResult);
    
    }

    @Test
    void TripTest(){
        controller.editTrip("NYC", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), 100);

        assertEquals("Trip Details:\nLocation: NYC\nStart Date: 2025-01-01\nEnd Date: 2025-01-01\nBudget: 100.00\nParticipants: []", controller.getCurrentTrip().toString());

        controller.editTrip("Boston");
        controller.editTrip(LocalDate.of(2024, 12, 25), true);
        controller.editTrip(LocalDate.of(2024, 12, 25), false);
        controller.editTrip(200);
        assertEquals("Boston", controller.getTripLocation());
        assertEquals(LocalDate.of(2024, 12, 25), controller.getTripStart());
        assertEquals(LocalDate.of(2024, 12, 25), controller.getTripEnd());
        assertEquals(200, controller.getBudget());

        User myFriend = new User(1, "Catharine Vassar", "Poughkeepsie", new ArrayList<User>());
        User myOtherFriend = new User(2, "John Ellison Vassar", "Poughkeepsie", new ArrayList<User>());

        controller.addParticipant(myFriend);
        controller.addParticipant(myOtherFriend);
        assertTrue(controller.getTripParticipants().contains(myFriend));
        assertTrue(controller.getTripParticipants().contains(myOtherFriend));
    }

}