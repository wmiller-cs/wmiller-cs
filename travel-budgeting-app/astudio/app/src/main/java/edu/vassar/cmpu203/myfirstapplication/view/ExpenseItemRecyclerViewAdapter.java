package edu.vassar.cmpu203.myfirstapplication.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentButtonItemBinding;
import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentExpenseItemBinding;
import edu.vassar.cmpu203.myfirstapplication.model.ExpenseItem;
import java.util.List;

/**
 * {@link RecyclerView.Adapter}
 */
public class ExpenseItemRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseItemRecyclerViewAdapter.ExpenseViewHolder> {

    List<ExpenseItem> items;
    Context context;

    public ExpenseItemRecyclerViewAdapter(Context context, List<ExpenseItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentExpenseItemBinding binding = FragmentExpenseItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        holder.binding.expenseItemButton.setText(items.get(position).getExpenseItemLabel());
        holder.binding.expenseItemButton.setOnClickListener(items.get(position).getButtonAction());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final FragmentExpenseItemBinding binding;

        public ExpenseViewHolder(FragmentExpenseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}