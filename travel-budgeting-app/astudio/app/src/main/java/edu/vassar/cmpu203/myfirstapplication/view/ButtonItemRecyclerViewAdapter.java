package edu.vassar.cmpu203.myfirstapplication.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import edu.vassar.cmpu203.myfirstapplication.databinding.FragmentButtonItemBinding;
import edu.vassar.cmpu203.myfirstapplication.model.ButtonItem;

public class ButtonItemRecyclerViewAdapter extends RecyclerView.Adapter<ButtonItemRecyclerViewAdapter.ButtonViewHolder> {

    Context context;
    List<ButtonItem> items;

    public ButtonItemRecyclerViewAdapter(Context context, List<ButtonItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentButtonItemBinding binding = FragmentButtonItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ButtonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        holder.binding.listItemText.setText(items.get(position).getItemText());
        if (items.get(position).getButtonExists()) {
            holder.binding.listItemButton.setText(items.get(position).getButtonText());
            holder.binding.listItemButton.setContentDescription(items.get(position).getButtonDescription());
            holder.binding.listItemButton.setOnClickListener(items.get(position).getButtonAction());
        }
        else{
            holder.binding.listItemButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        private final FragmentButtonItemBinding binding;
        public ButtonViewHolder(@NonNull FragmentButtonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public TextView getListItemText(){
            return this.binding.listItemText;
        }
        public Button getListItemButton(){
            return this.binding.listItemButton;
        }
    }
}