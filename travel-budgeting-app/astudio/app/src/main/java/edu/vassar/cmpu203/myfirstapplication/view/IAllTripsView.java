package edu.vassar.cmpu203.myfirstapplication.view;

import java.util.List;

import edu.vassar.cmpu203.myfirstapplication.model.Trip;

public interface IAllTripsView {
    void displayAllTrips(List<Trip> allTrips);

    interface Listener{
        void onTripSelected(int i, Trip selectedTrip);

        void onNewTrip();
    }

}
