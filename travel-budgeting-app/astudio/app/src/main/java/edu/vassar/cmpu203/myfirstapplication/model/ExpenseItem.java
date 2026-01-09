package edu.vassar.cmpu203.myfirstapplication.model;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.myfirstapplication.view.ExpenseItemRecyclerViewAdapter;
import edu.vassar.cmpu203.myfirstapplication.R;

/**
 * A fragment representing a list of Items.
 */
public class ExpenseItem {

    private static final String ARG_COLUMN_COUNT = "column-count";

    String expenseItemLabel;
    View.OnClickListener buttonAction;


    public ExpenseItem (String expenseItemLabel, View.OnClickListener buttonAction) {
        this.expenseItemLabel = expenseItemLabel;
        this.buttonAction = buttonAction;
    }

    public String getExpenseItemLabel() {
        return this.expenseItemLabel;
    }

    public View.OnClickListener getButtonAction(){ return this.buttonAction;}
    public void setButtonAction(View.OnClickListener buttonAction ){ this.buttonAction = buttonAction;}

}