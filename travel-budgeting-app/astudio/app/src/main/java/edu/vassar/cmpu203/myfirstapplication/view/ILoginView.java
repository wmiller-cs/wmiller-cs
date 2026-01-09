package edu.vassar.cmpu203.myfirstapplication.view;

import android.view.View;
import android.widget.TextView;

public interface ILoginView {
    public View getRootView();

    void loginAndContinue();

    interface Listener{

        void onLogIn(String username, String password, TextView usernameView);

        void onNewUser(String username, String password);
    }
}
