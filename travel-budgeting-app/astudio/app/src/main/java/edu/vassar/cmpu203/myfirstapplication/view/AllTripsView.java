package edu.vassar.cmpu203.myfirstapplication.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.myfirstapplication.model.ButtonItem;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentAllTripsScreenBinding;

public class AllTripsView extends Fragment implements IAllTripsView {
    User user;

    FragmentAllTripsScreenBinding binding;


    IAllTripsView.Listener listener;

    public AllTripsView(User user, Listener listener){
        this.user = user;
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentAllTripsScreenBinding.inflate(inflater);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        this.binding.newTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllTripsView.this.listener.onNewTrip();
            }
        });

        AllTripsView.this.displayAllTrips(this.user.getAllTrips());
    }

    @Override
    public void displayAllTrips(List<Trip> allTrips) {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<ButtonItem> allTripsDisplay = new ArrayList<>();

        for(int i=0; i<allTrips.size(); i++){
            int finalI = i;
            String tripName = allTrips.get(i).getTripLocation() + " "  + allTrips.get(i).getStartDate().getYear();
            ButtonItem trip = new ButtonItem(tripName, "Edit", tripName, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllTripsView.this.listener.onTripSelected(finalI, allTrips.get(finalI));
                }
            });

            allTripsDisplay.add(trip);
        }

        binding.recyclerView.setAdapter(new ButtonItemRecyclerViewAdapter(this.getContext(), allTripsDisplay));
    }
}
