package edu.vassar.cmpu203.myfirstapplication.view;



import android.os.Bundle;
import android.view.*;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.vassar.cmpu203.myfirstapplication.model.ButtonItem;
import edu.vassar.cmpu203.myfirstapplication.model.User;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentFriendsScreenBinding;

public class FriendsView extends Fragment  implements IFriendsView{
    private FragmentFriendsScreenBinding binding;


    User user;
    Listener listener;

    ListView currentFriendsView;

    public FriendsView(User user, Listener listener) {
        this.user = user;
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        currentFriendsView = getView().findViewById(R.id.currentFriendsList);
        friends = (String[]) user.getFriends().toArray();
        MainAdapter friendsAdapter = new MainAdapter(FriendsView.this.getContext(), friends);
        currentFriendsView.setAdapter(friendsAdapter);
        //Toast.makeText(getActivity().getApplicationContext(), ""+friends[i], )


        //Adapter<User> friendsAdapter = new ArrayAdapter<>(requireActivity().getApplicationContext(), android.R.layout.simple_list_item_1, user.getFriends());

        this.binding.currentFriendsList.setAdapter(friendsAdapter);
         */



        this.binding = FragmentFriendsScreenBinding.inflate(inflater);

        FriendsView.this.displayCurrentFriends();

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        this.binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFriendSearch();
            }
        });
    }


    @Override
    public void updateFriendSearch() {
        String username = this.binding.editFriendUsername.getText().toString();

        FriendsView.this.listener.onFriendSearched(username, this.binding.addUserButton);
    }

    public void displayCurrentFriends(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<ButtonItem> currentFriendsDisplay = new ArrayList<>();
        for (User thisFriend : user.getFriends()){
            String friendName = thisFriend.getName();
            if (!(user.getCurrentTrip().getTripParticipants().contains(thisFriend))) {
                ButtonItem friend = new ButtonItem(friendName, "Add to this trip", "Add " + friendName + " to current trip", true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FriendsView.this.listener.onParticipantAdd(thisFriend);
                    }
                });
                currentFriendsDisplay.add(friend);
            }
            else{
                ButtonItem friend = new ButtonItem(friendName, "Add to this trip", "Add " + friendName + " to current trip", false, null);
                currentFriendsDisplay.add(friend);
                }
        }
        binding.recyclerView.setAdapter(new ButtonItemRecyclerViewAdapter(this.getContext(), currentFriendsDisplay));
    }
}
