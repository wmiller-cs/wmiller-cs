package main;
import java.util.ArrayList;
import java.util.List;

public class User {
    int id; // Switch to Java UUID
    String name;
    String location;
    List<User> friends;

    Trip currentTrip;
    List<Trip> allTrips;

    public User() {
        this.id = 0;
        this.name = "Matthew Vassar";
        this.location = "Poughkeepsie";
        this.friends = new ArrayList<>();
        this.currentTrip = new Trip();
        this.allTrips = new ArrayList<>();
    }

    public User(int id, String name, String location, List<User> friends) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.friends = friends;
    }

    public String toString() {
        return name;
    }

    public void editUser(String name, String location, List<User> friends, Trip currentTrip, List<Trip> allTrips) {
        this.name = name;
        this.location = location;
        this.friends = friends;
        this.currentTrip = currentTrip;
        this.allTrips = allTrips;
    }

    public void editUser(String string, boolean isName) {
        if(isName){
            this.name = string;
        }
        else{
            this.location = string;
        }
    }
    public void editUser(List input, boolean isFriends) {
        if (isFriends){
            this.friends = input;
        }
        else{
            this.allTrips = input;
        }
    }
    public void editUser(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }
    public void editUser(List<Trip> allTrips) {
        this.allTrips = allTrips;
    }

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public String getLocation(){return this.location;}

    public List<User> getFriends(){return this.friends;}

    public Trip getCurrentTrip(){return this.currentTrip;}

    public List<Trip> getAllTrips(){return this.allTrips;}

    public User searchFriends(Object searchTerm, int category) {
        for (User friend : this.friends) {
            if (category==1 && friend.getId()==(int)searchTerm) {
                return friend;
            }
            else if (category==2 && friend.getName().equals(searchTerm)) {
                return friend;
            }
            else if (category==3 && friend.getLocation().equals(searchTerm)) {
                return friend;
            }
            else if (category==4 && friend.getFriends() == searchTerm) {
                return friend;
            }
        }

        return null;
    }


}
