package edu.vassar.cmpu203.myfirstapplication;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.*;


import edu.vassar.cmpu203.myfirstapplication.model.Expense;
import edu.vassar.cmpu203.myfirstapplication.model.ExpenseItem;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;
import edu.vassar.cmpu203.myfirstapplication.view.*;
//import edu.vassar.cmpu203.myfirstapplication.view.MainView;

public class MainActivity extends AppCompatActivity implements IAddExpenseView.Listener, IDashboardView.Listener, ILoginView.Listener, IMainView.Listener, IFriendsView.Listener, IEditTripView.Listener, INewTripView.Listener, IAllTripsView.Listener, DatePickerDialog.OnDateSetListener {
    IMainView mainView;
    TextView dateView;
    INavBarView navBarView;

    // Text to set date to
    TextView targetOfDatePicker;

    // RecyclerView for Expense List
    RecyclerView expenseListRecyclerView;


    // Text in login to tell user invalid credentials
    TextView invalidCredentials;

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    User testUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainView = new MainView(this, this, this);

        EdgeToEdge.enable(this);
        LoginView loginView = new LoginView(this, this, this);
        setContentView(loginView.getRootView());
    }

    @Override
    public void onNavigationChosen(int id){
        //int id = item.getId();
        if (id == R.id.allTripsButton){
            setContentView(this.mainView.getRootView());
            this.mainView.displayFragment(new AllTripsView(this.user,this));
        }
        else if (id == R.id.currentTripButton) {
            setContentView(this.mainView.getRootView());
            this.mainView.displayFragment(new EditTripView(this.user,this));
        }
        else if (id == R.id.dashboardButton) {
            setContentView(this.mainView.getRootView());
            this.mainView.displayFragment(new DashboardView(this.user,this));
        }
        else if (id == R.id.friendsButton){
            setContentView(this.mainView.getRootView());
            this.mainView.displayFragment(new FriendsView(this.user,this));
        }
    }

    @Override
    public void onCurrentTripInfoChanged(Trip currentTrip) {
        this.user.setCurrentTrip(currentTrip);
        db.collection("users").document(String.valueOf(user.getId())).set(user.toMap());
        db.collection("trips").document(String.valueOf(currentTrip.getId())).set(currentTrip.toMap());
        this.mainView.displayFragment(new EditTripView(this.user,this));
    }

    @Override
    public void onNewTripInfoChanged(Trip newTrip){
        this.user.getAllTrips().add(newTrip);
        db.collection("users").document(String.valueOf(user.getId())).set(user.toMap());
        db.collection("trips").document(String.valueOf(newTrip.getId())).set(newTrip.toMap());

        this.mainView.displayFragment(new EditTripView(this.user,this));
    }


    @Override
    public void onFriendAdd(User searchedUser) {
        this.user.getFriends().add(searchedUser);
        db.collection("users").document(String.valueOf(user.getId())).set(user.toMap());

        searchedUser.getFriends().add(this.user);
        db.collection("users").document(String.valueOf(searchedUser.getId())).set(searchedUser.toMap());

        //should add user to searchedUser friend list too
        this.mainView.displayFragment(new FriendsView(this.user, this));
    }

    @Override
    public void onParticipantAdd(User participant) {
        Trip thisTrip = user.getCurrentTrip();
        thisTrip.addParticipant(participant);
        participant.getAllTrips().add(thisTrip);

        db.collection("trips").document(String.valueOf(user.getCurrentTrip().getId())).set(thisTrip.toMap());
        db.collection("users").document(String.valueOf(user.getId())).set(user.toMap());
        db.collection("users").document(String.valueOf(participant.getId())).set(participant.toMap());
        this.mainView.displayFragment(new FriendsView(user, this));
    }

    @Override
    public void onFriendSearched(String username, Button searchedUserButton) {
        db.collection("users").whereEqualTo("name", username).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qsnap) {
                for (DocumentSnapshot dsnap : qsnap){
                    User searchedUser = User.fromMap(dsnap.getData(), 0);
                    if (user.searchFriends(searchedUser.getId(), 1)==null) {
                        searchedUserButton.setText(searchedUser.getName());
                        searchedUserButton.setVisibility(View.VISIBLE);
                        searchedUserButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onFriendAdd(searchedUser);
                            }
                        });
                    }
                    break;
                }
            }
        });
    }

    @Override
    public void onTripSelected(int i, Trip selectedTrip) {
        this.user.getAllTrips().remove(i);
        this.user.getAllTrips().add(selectedTrip);
        db.collection("users").document(String.valueOf(user.getId())).set(user.toMap());

        this.mainView.displayFragment(new EditTripView(this.user,this));
    }

    @Override
    public void onNewTrip(){
        this.mainView.displayFragment(new NewTripView(user,this));
    }

    @Override
    public void onEditStartDate(TextView currentTripStartDate){
        DatePickerFragment mDatePickerDialogFragment = new DatePickerFragment();
        mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        targetOfDatePicker = currentTripStartDate;
    }

    @Override
    public void onEditEndDate(TextView currentTripEndDate) {
        DatePickerFragment mDatePickerDialogFragment = new DatePickerFragment();
        mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        targetOfDatePicker = currentTripEndDate;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String selectedDate = formatter.format(calendar.getTime());
        targetOfDatePicker.setText(selectedDate);
    }

    @Override
    public void onParticipantRemove(User participant) {
        user.getCurrentTrip().getTripParticipants().remove(participant);
        participant.getAllTrips().remove(user.getCurrentTrip());

        db.collection("trips").document(String.valueOf(user.getCurrentTrip().getId())).set(user.getCurrentTrip().toMap());
        db.collection("users").document(String.valueOf(participant.getId())).set(participant.toMap());
        this.mainView.displayFragment(new EditTripView(this.user, this));
    }

    @Override
    public void onExpensesSorted(RecyclerView recyclerView ,ArrayList<ExpenseItem> expenseListItems) {
        expenseListRecyclerView = recyclerView;
        expenseListRecyclerView.setAdapter(new ExpenseItemRecyclerViewAdapter(this.getBaseContext(), expenseListItems));
        this.mainView.displayFragment(new DashboardView(this.user, this));
    }

    @Override
    public void onNewExpenseButtonClicked() {
        AddExpenseView addExpenseView = new AddExpenseView(user, this);
        this.mainView.displayFragment(addExpenseView);
    }

    @Override
    public void onTripExpensesChanged(Trip currentTrip) {
        this.user.setCurrentTrip(currentTrip);
        db.collection("users").document(String.valueOf(user.getId())).set(user.toMap());
        db.collection("trips").document(String.valueOf(currentTrip.getId())).set(currentTrip.toMap());
        this.mainView.displayFragment(new DashboardView(user, this));
    }

    @Override
    public void onAddExpenseCanceled() {
        this.mainView.displayFragment(new DashboardView(user, this));
    }

    @Override
    public void onLogIn(String username, String password, TextView usernameView) {
        invalidCredentials = usernameView;
        testUser = null;

        db.collection("users").whereEqualTo("name", username).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qsnap) {
                testUser = null;
                for (DocumentSnapshot dsnap : qsnap) {
                    testUser = User.fromMap(dsnap.getData(), 0);
                    break;
                }
                MainActivity.this.onCheckLogin(username, password);
            }
        });
    }

    private void onCheckLogin(String username, String password){
        if (testUser!=null) {
            if (testUser.getPassword().equals(password)) {
                this.user = testUser;
                this.goHome();
                return;
            } else {
                invalidCredentials.setText("Invalid password");
                SystemClock.sleep(700);
                LoginView loginView = new LoginView(this, this, this);

                setContentView(loginView.getRootView());
            }
        }
        else {
            invalidCredentials.setText("New user");
            this.onNewUser(username, password);
        }
    }

    @Override
    public void onNewUser(String username, String password) {
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            int id;
            @Override
            public void onSuccess(QuerySnapshot qsnap) {
                for (DocumentSnapshot dsnap : qsnap){
                   id = 1 + Integer.parseInt(String.valueOf(dsnap.get("id")));
                }

                User newUser = new User();
                Trip firstTrip = new Trip();
                firstTrip.getTripParticipants().add(newUser);
                newUser.getAllTrips().add(firstTrip);

                newUser.setId(id);
                newUser.setName(username);
                newUser.setLocation("Gotham");
                newUser.setPassword(password);

                db.collection("users").document(String.valueOf(id)).set(newUser.toMap());

                user = newUser;
                goHome();
            }
        });
    }

    public User searchUserByName(String username){

        testUser = null;
        db.collection("users").whereEqualTo("name", username).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qsnap) {
                for (DocumentSnapshot dsnap : qsnap){
                    testUser = User.fromMap(dsnap.getData(), 0);
                    break;
                }
            }
        });

        return testUser;
    }

    public void goHome(){
        setContentView(this.mainView.getRootView());
        SystemClock.sleep(1000);
        this.mainView.displayFragment(new DashboardView(user,this));
    }

    public static void throwException(String message) {
        throw new RuntimeException(message);
    }

}