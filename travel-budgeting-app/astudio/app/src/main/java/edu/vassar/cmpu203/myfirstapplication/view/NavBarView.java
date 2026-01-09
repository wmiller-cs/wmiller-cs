package edu.vassar.cmpu203.myfirstapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.myfirstapplication.R;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentNavBarMenuBinding;

public class NavBarView extends Fragment implements INavBarView {
    private FragmentNavBarMenuBinding binding;

    Listener listener;


    public NavBarView(){}

    public NavBarView(@NonNull Listener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentNavBarMenuBinding.inflate(inflater);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        this.binding.allTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBarView.this.listener.onNavigationPressed(R.id.allTripsButton);
            }
        });

        this.binding.currentTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBarView.this.listener.onNavigationPressed(R.id.currentTripButton);
            }
        });

        this.binding.dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBarView.this.listener.onNavigationPressed(R.id.dashboardButton);
            }
        });



        this.binding.friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBarView.this.listener.onNavigationPressed(R.id.friendsButton);
            }
        });

    }

}
