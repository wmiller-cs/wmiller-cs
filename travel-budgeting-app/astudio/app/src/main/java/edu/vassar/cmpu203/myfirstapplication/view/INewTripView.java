package edu.vassar.cmpu203.myfirstapplication.view;

import edu.vassar.cmpu203.myfirstapplication.model.Trip;

public interface INewTripView {
    interface Listener{
        void onNewTripInfoChanged(final Trip currentTrip);
    }
    Trip updateNewTripInfo(Trip newTrip);
}
