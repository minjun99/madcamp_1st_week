package com.example.madcamp_1st_week;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private ArrayList<RecyclerItem> listItem = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    void addItem(RecyclerItem recyclerItem) {
        listItem.add(recyclerItem);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView_id);
        }

        void onBind(RecyclerItem recyclerItem) {
            textView.setText(recyclerItem.getName());
        }
    }
}
