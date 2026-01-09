package edu.vassar.cmpu203.myfirstapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import edu.vassar.cmpu203.myfirstapplication.databinding.ActivityMainBinding;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentLoginScreenBinding;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentNewTripScreenBinding;

public class LoginView extends Fragment implements ILoginView{
    private FragmentLoginScreenBinding binding;

    ILoginView.Listener listener;

    FragmentManager fmanager;

    public LoginView(Context context, final FragmentActivity factivity, ILoginView.Listener listener) {
        this.binding = FragmentLoginScreenBinding.inflate(LayoutInflater.from(context));
        this.listener = listener;
        this.fmanager = factivity.getSupportFragmentManager();
        this.haveInteractions();
    }

    private void haveInteractions() {
        this.binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginView.this.loginAndContinue();
            }
        });
    }

    @Override
    public void loginAndContinue() {
        String username = String.valueOf(this.binding.editUsername.getText());
        String password = String.valueOf(this.binding.editPassword.getText());

        LoginView.this.listener.onLogIn(username, password, this.binding.editUsername);

    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }
}