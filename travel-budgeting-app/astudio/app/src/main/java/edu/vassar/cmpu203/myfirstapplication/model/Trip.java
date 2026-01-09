package edu.vassar.cmpu203.myfirstapplication.model;
import static edu.vassar.cmpu203.myfirstapplication.MainActivity.db;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;
import java.time.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Trip {
    int id;
    String location;
    LocalDate startDate;
    LocalDate endDate;
    double budget;
    ArrayList<Expense> tripExpenses = new ArrayList<Expense>();
    Set<User> tripParticipants = new HashSet<User>(); // Change to integer hashset of unique IDs

    public Trip() {
        this.id = new Random().nextInt();
        this.location = "Poughkeepsie";
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
        this.budget = 0.0;
    }

    public Trip(int id, String location, LocalDate startDate, LocalDate endDate, double budget) {
        this.id = id;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    public String toString() {
        return "Trip Details:\nID: " + this.id + "\nLocation: " + this.location +
        "\nStart Date: " + this.startDate.toString() +
        "\nEnd Date: " + this.endDate.toString() +
        "\nBudget: " + String.format("%.2f", this.budget) +
        "\nParticipants: " + this.tripParticipants.toString();
    }

    public void editTrip(String location, LocalDate tripStart, LocalDate tripEnd, double budget) {
        this.location = location;
        this.startDate = tripStart;
        this.endDate = tripEnd;
        this.budget = budget;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    private void setParticipants(Set<User> participants) {
        this.tripParticipants = participants;
    }

    public boolean addParticipant(User friend) {
        return this.tripParticipants.add(friend);
    }

    public void clearParticipants(){this.tripParticipants.clear();}

    private void setExpenses(ArrayList<Expense> expenses) {this.tripExpenses = expenses; }

    public boolean addExpense(Expense expense) { return this.tripExpenses.add(expense); }

    public String getTripLocation() {
        return this.location;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public double getBudget() {
        return this.budget;
    }

    public ArrayList<Expense> getTripExpenses() { return this.tripExpenses; }

    public Set<User> getTripParticipants() {
        return this.tripParticipants;
    }

    public Integer getId() {
        return this.id;
    }

    public static Trip fromMap(Map<String, Object> data, int iterations) {

        Trip trip = new Trip(Integer.parseInt(String.valueOf(data.get("id"))), String.valueOf(data.get("location")), LocalDate.parse((String) data.get("startDate")), LocalDate.parse((String) data.get("endDate")), (double) data.get("budget"));

        if (iterations == 0) {
            ArrayList<Integer> tripParticipantsIds = (ArrayList<Integer>) data.get("tripParticipants");

            if (tripParticipantsIds != null) {
                for (Object thisParticipantId : tripParticipantsIds) {

                    db.collection("users").document(String.valueOf(thisParticipantId)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot dsnap) {
                            User thisParticipant = User.fromMap(dsnap.getData(), 1);
                            trip.getTripParticipants().add(thisParticipant);
                        }
                    });
                }
            }

            ArrayList<Integer> tripExpensesIds = (ArrayList<Integer>) data.get("tripExpenses");

            if (tripExpensesIds != null) {
                for (Object thisExpenseId : tripExpensesIds) {

                    db.collection("expenses").document(String.valueOf(thisExpenseId)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot dsnap) {
                            Expense thisExpense = Expense.fromMap(dsnap.getData(), 1);
                            trip.getTripExpenses().add(thisExpense);
                        }
                    });
                }
            }
        }


        return trip;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> tripInfo = new HashMap<>();
        tripInfo.put("id", id);
        tripInfo.put("location", location);
        tripInfo.put("startDate", startDate.toString());
        tripInfo.put("endDate", endDate.toString());
        tripInfo.put("budget", budget);

        if (!tripParticipants.isEmpty()){
            ArrayList<Integer> participantsIds = new ArrayList<Integer>();
            for (User thisParticipant : tripParticipants) {
                participantsIds.add(thisParticipant.getId());
            }
            tripInfo.put("tripParticipants", participantsIds);
        }

        if(!tripExpenses.isEmpty()){
            ArrayList<Integer> expenseIds = new ArrayList<>();
            for (Expense thisExpense : tripExpenses){
                db.collection("expenses").document(thisExpense.getId().toString()).set(thisExpense.toMap());
                expenseIds.add(thisExpense.getId());
            }
            tripInfo.put("tripExpenses", expenseIds);
        }

        return tripInfo;
    }
}
