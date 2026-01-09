package edu.vassar.cmpu203.myfirstapplication.view;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import edu.vassar.cmpu203.myfirstapplication.model.ButtonItem;
import edu.vassar.cmpu203.myfirstapplication.model.Expense;
import edu.vassar.cmpu203.myfirstapplication.model.ExpenseItem;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentDashboardScreenBinding;


public class DashboardView extends Fragment implements IDashboardView {
    private FragmentDashboardScreenBinding binding;

    IDashboardView.Listener listener;
    User user;

    ArrayList<Expense> expenseList;
    ArrayList<ExpenseItem> expenseItemList;

    public DashboardView(User user, @NonNull IDashboardView.Listener listener) {
        this.user = user;
        this.listener = listener;
        this.expenseList = user.getCurrentTrip().getTripExpenses();
        this.expenseItemList = new ArrayList<>();
        updateExpenseItemList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentDashboardScreenBinding.inflate(inflater);

        return this.binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.binding.dashboardTitle.setText(String.format("Trip at %s", user.getCurrentTrip().getTripLocation()));

        double totalSpendings = 0;
        for (Expense e : expenseList) {
            totalSpendings += e.getCost();
        }
        this.binding.totalBudget.setText(String.format(Locale.getDefault(), "%.2f", user.getCurrentTrip().getBudget()));
        this.binding.budgetRemaining.setText(String.format(Locale.getDefault(), "%.2f", (user.getCurrentTrip().getBudget() - totalSpendings)));

        DashboardView.this.displayExpenses(expenseList);
        this.binding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.binding.expenseUserTagLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseList.sort(Comparator.comparing(Expense::getUserTag, Comparator.reverseOrder()));
                DashboardView.this.listener.onExpensesSorted(binding.expenseRecyclerView, expenseItemList);
            }
        });

        this.binding.expenseTypeLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseList.sort(Comparator.comparing(Expense::getExpenseType, Comparator.reverseOrder()));
                DashboardView.this.listener.onExpensesSorted(binding.expenseRecyclerView, expenseItemList);
            }
        });

        this.binding.expenseCostLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseList.sort(Comparator.comparing(Expense::getCost, Comparator.reverseOrder()));
                DashboardView.this.listener.onExpensesSorted(binding.expenseRecyclerView, expenseItemList);
            }
        });

        this.binding.expenseTimeLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expenseList.isEmpty()) {
                    expenseList.sort(Comparator.comparing(Expense::getTimeStamp, Comparator.reverseOrder()));
                    DashboardView.this.listener.onExpensesSorted(binding.expenseRecyclerView, expenseItemList);
                }
            }
        });

        this.binding.newExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardView.this.listener.onNewExpenseButtonClicked();
            }
        });

    }

    private void displayExpenses(ArrayList<Expense> allExpenses) {
        binding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<ExpenseItem> allExpensesDisplay = new ArrayList<>();

        for(int i=0; i<allExpenses.size(); i++){
            String expenseButtonText = allExpenses.get(i).getUserTag() + " | "  + allExpenses.get(i).getExpenseType().toString() + " | " + String.format(Locale.getDefault(), "%.2f", allExpenses.get(i).getCost()) + " | " + allExpenses.get(i).getTimeStamp().truncatedTo(ChronoUnit.MINUTES).toString();
            final String expenseButtonMemo = allExpenses.get(i).getMemo();
            ExpenseItem expense = new ExpenseItem(expenseButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    TextView info = new TextView(getActivity());
                    info.setText(expenseButtonMemo);
                    info.setTextSize(COMPLEX_UNIT_SP,20);
                    builder.setCustomTitle(info);
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            allExpensesDisplay.add(expense);
        }

        binding.expenseRecyclerView.setAdapter(new ExpenseItemRecyclerViewAdapter(this.getContext(), allExpensesDisplay));

    }

    private void onExpenseClicked(String memo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(memo);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateExpenseItemList() {
        for (Expense thisExpense : expenseList) {
            ExpenseItem expense = new ExpenseItem(thisExpense.getUserTag() + " | "  + thisExpense.getExpenseType().toString() + " | " + String.format(Locale.getDefault(), "%.2f", thisExpense.getCost()) + " | " + thisExpense.getTimeStamp().toString(), new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    TextView info = new TextView(getActivity());
                    info.setText(thisExpense.getMemo());
                    info.setTextSize(COMPLEX_UNIT_SP,20);
                    builder.setCustomTitle(info);
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            expenseItemList.add(expense);
        }
    }

}
