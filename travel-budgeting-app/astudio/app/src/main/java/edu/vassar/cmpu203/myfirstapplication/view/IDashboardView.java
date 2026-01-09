package edu.vassar.cmpu203.myfirstapplication.view;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.vassar.cmpu203.myfirstapplication.model.Expense;
import edu.vassar.cmpu203.myfirstapplication.model.ExpenseItem;


public interface IDashboardView {

    interface Listener{
        void onExpensesSorted(RecyclerView recyclerView, ArrayList<ExpenseItem> expenseList);
        void onNewExpenseButtonClicked();
    }
}