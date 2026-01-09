package edu.vassar.cmpu203.myfirstapplication.view;

import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import edu.vassar.cmpu203.myfirstapplication.model.ButtonItem;
import edu.vassar.cmpu203.myfirstapplication.model.User;

public interface IFriendsView {
    void updateFriendSearch();

    interface Listener{

        void onFriendAdd(User searchedUser);

        void onParticipantAdd(User participant);

        void onFriendSearched(String username, Button searchedUserButton);
    }


}
