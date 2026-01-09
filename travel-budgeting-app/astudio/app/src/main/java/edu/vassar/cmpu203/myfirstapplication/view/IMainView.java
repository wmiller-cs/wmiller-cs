package edu.vassar.cmpu203.myfirstapplication.view;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface IMainView {

    void displayNav(Fragment nav);

    void displayNav(Fragment nav, String transName);

    public View getRootView();
    public void displayFragment(Fragment fragment);
    public void displayFragment(Fragment fragment, String transName);

    interface Listener{

        void onNavigationChosen(int id);
    }
}

