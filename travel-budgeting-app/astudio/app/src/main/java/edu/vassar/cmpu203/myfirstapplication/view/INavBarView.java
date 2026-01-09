package edu.vassar.cmpu203.myfirstapplication.view;

import androidx.annotation.NonNull;

public interface INavBarView {
    interface Listener{
        void onNavigationPressed(int id);
    }
}
