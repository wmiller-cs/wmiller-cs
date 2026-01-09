package edu.vassar.cmpu203.myfirstapplication.view;

import edu.vassar.cmpu203.myfirstapplication.model.Trip;

public interface IAddExpenseView {

    interface Listener{

        void onTripExpensesChanged(Trip cTrip);
        void onAddExpenseCanceled();
    }
}
