package edu.vassar.cmpu203.myfirstapplication.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentEditTripScreenBinding;
import edu.vassar.cmpu203.myfirstapplication.model.DecimalInputFilter;
import edu.vassar.cmpu203.myfirstapplication.model.ButtonItem;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;

public class EditTripView extends Fragment implements IEditTripView {
    private FragmentEditTripScreenBinding binding;

    Listener listener;
    private User user;


    public EditTripView(User user, @NonNull Listener listener) {
        this.user = user;
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentEditTripScreenBinding.inflate(inflater);

        return this.binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Trip currentTrip = this.user.getCurrentTrip();
        this.binding.editLocation.setText(currentTrip.getTripLocation());
        this.binding.editBudget.setText(String.format(Locale.getDefault(), "%.2f", currentTrip.getBudget()));
        this.binding.editBudget.setFilters(new InputFilter[] {new DecimalInputFilter(5,2)});
        this.binding.startDateDisplay.setText(currentTrip.getStartDate().toString());
        this.binding.endDateDisplay.setText(currentTrip.getEndDate().toString());

        EditTripView.this.displayTripParticipants(this.user.getCurrentTrip().getTripParticipants());


        this.binding.startDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTripView.this.listener.onEditStartDate(binding.startDateDisplay);
            }
        });

        this.binding.endDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                EditTripView.this.listener.onEditEndDate(binding.endDateDisplay);
            }
        });

        this.binding.editTripSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip cTrip = updateCurrentTripInfo(currentTrip);

                EditTripView.this.listener.onCurrentTripInfoChanged(cTrip);
            }
        });
    }


    @Override
    public Trip updateCurrentTripInfo(Trip currentTrip) {

        String budget = binding.editBudget.getText().toString();
        if (!budget.isEmpty()) {
            currentTrip.setBudget(Double.parseDouble(budget));
        }

        String location = binding.editLocation.getText().toString();
        if (!location.isEmpty()) {
            currentTrip.setLocation(location);
        }

        try {
            LocalDate startDate = LocalDate.parse(binding.startDateDisplay.getText().toString());
            LocalDate endDate = LocalDate.parse(binding.endDateDisplay.getText().toString());
            if (!endDate.isBefore(startDate)){
                currentTrip.setStartDate(startDate);
                currentTrip.setEndDate(endDate);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Your end date is before your start date");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Invalid date format");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return currentTrip;
    }

    @Override
    public void displayTripParticipants(Set<User> currentTripParticipants) {
        //List<User> participants = new ArrayList<>(currentTripParticipants);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<ButtonItem> participantsDisplay = new ArrayList<>();

        for (User thisParticipant : currentTripParticipants){
            String participantName = thisParticipant.getName();
            if (!user.getName().equals(participantName)) {
                ButtonItem participant = new ButtonItem(participantName, "Remove from trip", "Remove " + participantName + " from trip", true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditTripView.this.listener.onParticipantRemove(thisParticipant);
                    }
                });
                participantsDisplay.add(participant);
            }
            else{
                ButtonItem participant = new ButtonItem(participantName, "", "", false, null);
                participantsDisplay.add(participant);
            }
        }

        binding.recyclerView.setAdapter(new ButtonItemRecyclerViewAdapter(this.getContext(), participantsDisplay));
    }
}
