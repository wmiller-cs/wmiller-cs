package edu.vassar.cmpu203.myfirstapplication.view;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;

import edu.vassar.cmpu203.myfirstapplication.R;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentAddExpenseScreenBinding;
import edu.vassar.cmpu203.myfirstapplication.model.DecimalInputFilter;
import edu.vassar.cmpu203.myfirstapplication.model.Expense;
import edu.vassar.cmpu203.myfirstapplication.model.ExpenseType;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;

public class AddExpenseView extends Fragment implements IAddExpenseView {
    private FragmentAddExpenseScreenBinding binding;

    private User user;
    private IAddExpenseView.Listener listener;

    public AddExpenseView(User user, IAddExpenseView.Listener listener){
        this.user = user;
        this.listener = listener;
    }

    public Trip updateTripExpenses(String newExpenseType, String newExpenseCost, String newExpenseMemo, Trip currentTrip) {

        Expense newExpense = new Expense();
        newExpense.setExpenseType(ExpenseType.parse(newExpenseType));
        newExpense.setCost(Double.parseDouble(newExpenseCost));
        newExpense.setMemo(newExpenseMemo);
        newExpense.setTimeStamp(LocalDateTime.now());
        newExpense.setUserTag(this.user.getName());

        currentTrip.addExpense(newExpense);
        return currentTrip;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentAddExpenseScreenBinding.inflate(inflater);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        Trip currentTrip = this.user.getCurrentTrip();

        this.binding.addExpenseTypeInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                TextView info = new TextView(getActivity());
                info.setText("Types of Expenses tracked:\nFood, Transportation, Housing, Merchandise, Excursion.\nAny text which doesn't match the above will be marked as Miscellaneous.");
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

        this.binding.addExpenseCost.setFilters(new InputFilter[] {new DecimalInputFilter(5,2)});

        this.binding.addExpenseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newExpenseType = binding.addExpenseType.getText().toString();
                String newExpenseCost = binding.addExpenseCost.getText().toString();
                String newExpenseMemo = binding.addExpenseMemo.getText().toString();

                if (!newExpenseType.isEmpty() && !newExpenseCost.isEmpty() && !newExpenseMemo.isEmpty()) {
                    Trip cTrip = updateTripExpenses(newExpenseType, newExpenseCost, newExpenseMemo, currentTrip);

                    AddExpenseView.this.listener.onTripExpensesChanged(cTrip);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Fields cannot be empty");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        this.binding.addExpenseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExpenseView.this.listener.onAddExpenseCanceled();
            }
        });

    }
}
