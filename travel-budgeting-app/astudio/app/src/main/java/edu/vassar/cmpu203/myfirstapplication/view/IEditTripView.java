package edu.vassar.cmpu203.myfirstapplication.view;

import android.widget.TextView;

import java.util.Set;

import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;

public interface IEditTripView {
    Trip updateCurrentTripInfo(Trip currentTrip);
    void displayTripParticipants(Set<User> currentTripParticipants);

    interface Listener{
        void onCurrentTripInfoChanged(final Trip currentTrip);

        void onEditStartDate(TextView currentTripStartDate);
        void onEditEndDate(TextView currentTripEndDate);

        void onParticipantRemove(User participant);
    }
}
