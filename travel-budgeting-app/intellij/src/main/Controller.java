package main;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    User user;
    Trip trip;

    public Controller (User u) {
        this.user = u;
        this.trip = u.getCurrentTrip(); // need to make sure that the trip in controller stays updated to the current trip, this only updates the value on instantiation.
    }

    // Controller keeps track of the states of User and Trips (potentially more later)
    // Wherever Main.user is called, a method should be created here

    //User methods
    public void editUser(String name, String location, List<User> friends, Trip currentTrip, List<Trip> allTrips) {
        user.editUser(name, location, friends, currentTrip, allTrips);
    }

    public void editUser(String string, boolean isName){
        user.editUser(string, isName);
    }

    public void editUser(List input, boolean isFriends){
        user.editUser(input, isFriends);
    }

    public void editUser(Trip currentTrip){
        user.editUser(currentTrip);
    }

    public void editUser(List<Trip> allTrips){
        user.editUser(allTrips);
    }

    public int getId(){
        return user.getId();
    }

    public String getName(){
        return user.getName();
    }

    public String getLocation(){
        return user.getLocation();
    }

    public List<User> getFriends(){
        return user.getFriends();
    }

    public Trip getCurrentTrip(){
        return user.getCurrentTrip();
    }

    public List<Trip> getAllTrips(){
        return user.getAllTrips();
    }

    public User searchFriends(Object searchTerm, int category){
        return user.searchFriends(searchTerm, category);
    }

    //Trip methods

    public void editTrip(String location, LocalDate tripStart, LocalDate tripEnd, double budget) {
        trip.editTrip(location, tripStart, tripEnd, budget);
    }

    public void editTrip(String location) {
        trip.editTrip(location);
    }

    public void editTrip(LocalDate tripDate, boolean isStart) {
        trip.editTrip(tripDate, isStart);
    }

    public void editTrip(double budget) {
        trip.editTrip(budget);
    }

    public boolean addParticipant(User friend) {
        return trip.addParticipant(friend);
    }

    public String getTripLocation() {
        return trip.getTripLocation();
    }

    public LocalDate getTripStart() {
        return trip.getTripStart();
    }

    public LocalDate getTripEnd() {
        return trip.getTripEnd();
    }

    public double getBudget() {
        return trip.getBudget();
    }

    public Set<User> getTripParticipants() {
        return trip.getTripParticipants();
    }

}
