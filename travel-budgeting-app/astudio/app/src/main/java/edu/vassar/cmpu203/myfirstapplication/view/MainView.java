package edu.vassar.cmpu203.myfirstapplication.view;

import edu.vassar.cmpu203.myfirstapplication.*;
import edu.vassar.cmpu203.myfirstapplication.databinding.ActivityMainBinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.ViewGroup;

public class MainView extends Fragment implements IMainView, INavBarView.Listener {
    private ActivityMainBinding binding;
    IMainView.Listener listener;

    FragmentManager fmanager;

    Fragment nav;

    public MainView(Context context, final FragmentActivity factivity, Listener listener) {
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));
        this.listener = listener;
        // configure app to maximize space usage by drawing of top of system bars
        EdgeToEdge.enable(factivity);
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.fmanager = factivity.getSupportFragmentManager();
        this.nav = new NavBarView(this);
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment) {
        this.displayFragment(fragment, null);
        this.displayNav(this.nav);
    }

    @Override
    public void displayFragment(Fragment fragment, String transName) {
        FragmentTransaction ft = fmanager.beginTransaction().replace(this.binding.mainActivity.getId(), fragment);
        if (transName != null) ft.addToBackStack(transName);
        ft.commit();
    }

    @Override
    public void displayNav(Fragment nav){
        this.displayNav(nav, null);
    }

    @Override
    public void displayNav(Fragment nav, String transName){
        FragmentTransaction ft = fmanager.beginTransaction().replace(this.binding.navBarMenuContainer.getId(), nav);
        if (transName != null) ft.addToBackStack(transName);
        ft.commit();
    }

    @Override
    public void onNavigationPressed(int id) {
        MainView.this.listener.onNavigationChosen(id);
    }
}
