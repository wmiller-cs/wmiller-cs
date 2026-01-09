package edu.vassar.cmpu203.myfirstapplication.model;


import static edu.vassar.cmpu203.myfirstapplication.MainActivity.db;
import static edu.vassar.cmpu203.myfirstapplication.MainActivity.throwException;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class User {

    private String password;
    int id; // Switch to Java UUID
    String name;
    String location;
    List<User> friends;

    //currentTrip is at the last index of allTrips
    List<Trip> allTrips;

    public User() {
        this.id = 0;
        this.name = "Matthew Vassar";
        this.location = "Poughkeepsie";
        this.friends = new ArrayList<>();
        this.allTrips = new ArrayList<Trip>();
        this.password = "password";
    }

    public User(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.friends = new ArrayList<User>();
        Trip currentTrip = new Trip();
        currentTrip.setId(new Random().nextInt());
        this.allTrips = new ArrayList<Trip>();
        this.allTrips.add(currentTrip);
        this.password = "password";
    }

    public String toString() {
        return name;
    }

    public void editUser(String name, String location, List<User> friends, List<Trip> allTrips) {
        this.name = name;
        this.location = location;
        this.friends = friends;
        this.allTrips = allTrips;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setAllTrips(ArrayList<Trip> allTrips){
        this.allTrips = allTrips;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.allTrips.remove(this.allTrips.size()-1);
        this.allTrips.add(currentTrip);
    }

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public String getPassword() { return this.password; }

    public String getLocation(){return this.location;}

    public List<User> getFriends(){return this.friends;}

    public Trip getCurrentTrip(){return this.allTrips.get(this.allTrips.size()-1);}

    public List<Trip> getAllTrips(){return this.allTrips;}

    public User searchFriends(Object searchTerm, int category) {

        for (User friend : friends ){
            if (category==1 && friend.getId()==(int)searchTerm) {
                return friend;
            }
            else if (category==2 && friend.getName().equalsIgnoreCase(searchTerm.toString())) {
                return friend;
            }
            else if (category==3 && friend.getLocation().equals(searchTerm)) {
                return friend;
            }
            else if (category==4 && friend.getFriends() == searchTerm) {
                return friend;
            }
        }

        if (category==1 && this.id==(int)searchTerm){
            return this;
        }
        else if (category==2 && this.name.equalsIgnoreCase(searchTerm.toString())) {
            return this;
        }
        else if (category==3 && this.location.equals(searchTerm)) {
            return this;
        }
        else if (category==4 && this.friends == searchTerm) {
            return this;
        }

        return null;
    }

    public static User fromMap(Map<String, Object> data, int iterations) {

        User user = new User(Integer.parseInt(String.valueOf(data.get("id"))), String.valueOf(data.get("name")), String.valueOf(data.get("location")));

        if (iterations==0) {
            ArrayList<Integer> friendsIds = (ArrayList<Integer>) data.get("friends");

            if (friendsIds!=null) {
                for (Object thisFriendId : friendsIds) {
                    db.collection("users").document(String.valueOf(thisFriendId)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot dsnap) {
                            User thisFriend = User.fromMap(dsnap.getData(), 1);
                            user.getFriends().add(thisFriend);
                        }
                    });
                }
            }

            ArrayList<Integer> allTripsIds = (ArrayList<Integer>) data.get("allTrips");

            if (allTripsIds!=null) {
                for (Object thisTripId : allTripsIds) {
                    db.collection("trips").document(thisTripId.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot dsnap) {
                            Trip thisTrip = Trip.fromMap(dsnap.getData(), 0);
                            user.getAllTrips().add(thisTrip);
                        }
                    });
                }
            }
        }


        user.setPassword(String.valueOf(data.get("password")));

        return user;
    }

    public Map<String, Object>  toMap() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("name", name);
        userInfo.put("location", location);
        userInfo.put("password", password);

        if (!friends.isEmpty()){
            ArrayList<Integer> friendIds = new ArrayList<Integer>();
            for (User thisFriend : friends) {
                friendIds.add(thisFriend.getId());
            }
            userInfo.put("friends", friendIds);
        }

        if (!allTrips.isEmpty()) {
            ArrayList<Integer> tripIds = new ArrayList<Integer>();
            for (Trip thisTrip : allTrips) {
                db.collection("trips").document(thisTrip.getId().toString()).set(thisTrip.toMap());
                tripIds.add(thisTrip.getId());
            }
            userInfo.put("allTrips", tripIds);
        }

        return userInfo;
    }
}

