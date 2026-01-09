package main;
import java.util.*;
import java.time.*;

public class Trip {
    String location;
    LocalDate tripStart;
    LocalDate tripEnd;
    double budget;
    Set<User> tripParticipants = new HashSet<User>(); // Change to integer hashset of unique IDs

    public Trip() {
        this.location = "Poughkeepsie";
        this.tripStart = LocalDate.now();
        this.tripEnd = LocalDate.now();
        this.budget = 0.0;
    }

    public Trip(String location, LocalDate tripStart, LocalDate tripEnd, double budget) {
        this.location = location;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.budget = budget;
    }

    public String toString() {
        return "Trip Details:\nLocation: " + this.location +
        "\nStart Date: " + this.tripStart.toString() +
        "\nEnd Date: " + this.tripEnd.toString() +
        "\nBudget: " + String.format("%.2f", this.budget) +
        "\nParticipants: " + this.tripParticipants.toString();
    }

    public void editTrip(String location, LocalDate tripStart, LocalDate tripEnd, double budget) {
        this.location = location;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.budget = budget;
    }

    public void editTrip(String location) {
        this.location = location;
    }

    public void editTrip(LocalDate tripDate, boolean isStart) {
        if (isStart) {
            this.tripStart = tripDate;
        } else {
            this.tripEnd = tripDate;
        }
    }

    public void editTrip(double budget) {
        this.budget = budget;
    }

    public boolean addParticipant(User friend) {
        return this.tripParticipants.add(friend);
    }

    public String getTripLocation() {
        return this.location;
    }

    public LocalDate getTripStart() {
        return this.tripStart;
    }

    public LocalDate getTripEnd() {
        return this.tripEnd;
    }

    public double getBudget() {
        return this.budget;
    }

    public Set<User> getTripParticipants() {
        return this.tripParticipants;
    }

}
