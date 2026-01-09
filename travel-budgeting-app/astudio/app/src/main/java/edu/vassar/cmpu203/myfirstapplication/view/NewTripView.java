package edu.vassar.cmpu203.myfirstapplication.view;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.Random;

import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentNewTripScreenBinding;

public class NewTripView extends Fragment implements INewTripView{
    User user;

    private FragmentNewTripScreenBinding binding;

    INewTripView.Listener listener;


    public NewTripView(User user, @NonNull INewTripView.Listener listener){
        this.user = user;
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentNewTripScreenBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        this.binding.saveNewTripButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Trip nTrip = updateNewTripInfo(new Trip());

                NewTripView.this.listener.onNewTripInfoChanged(nTrip);
            }
        });
    }

    @Override
    public Trip updateNewTripInfo(Trip newTrip) {
        newTrip.addParticipant(user);
        try{String budget = binding.editBudget.getText().toString();
            if (!budget.isEmpty()) {
                newTrip.setBudget(Double.parseDouble(budget));
            }
        }catch (Exception e){}

        try{String location = binding.editLocation.getText().toString();
            if (!location.isEmpty()){
                newTrip.setLocation(location);
            }
        }catch (Exception e){}

        try {
            String startDate = binding.editStartDate.getText().toString();
            LocalDate startDateL = newTrip.getStartDate();
            LocalDate endDateL = newTrip.getEndDate();
            String endDate = binding.editEndDate.getText().toString();
            if (!startDate.isEmpty()) {
                startDateL = LocalDate.parse(startDate);
            }
            if (!endDate.isEmpty()) {
                endDateL = LocalDate.parse(endDate);
            }
            if (endDateL.isAfter(startDateL)){
                newTrip.setStartDate(startDateL);
                newTrip.setEndDate(endDateL);
            }

        }catch (Exception e){}


        return newTrip;
    }
}
