package com.example.madcamp_1st_week;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;

        private RelativeLayout relativeLayout;

        private RecyclerItem recyclerItem;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView_id);
            imageView = itemView.findViewById(R.id.imageView_id);
            relativeLayout = itemView.findViewById(R.id.linearItem_id);
        }

        void onBind(RecyclerItem recyclerItem) {
            this.recyclerItem = recyclerItem;

            textView.setText(recyclerItem.getName());
            imageView.setImageResource(recyclerItem.getResId());

            textView.setOnClickListener(this);
            imageView.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linearItem_id:
                    show(view);
                    break;
                case R.id.textView_id:
                    show(view);
                    break;
                case R.id.imageView_id:
                    show(view);
                    break;
            }
        }

        void show(View view) {
            final Context context = view.getContext();

            List<String> InfoItems = new ArrayList<>();
            InfoItems.add("Name: \n" + recyclerItem.getName());
            InfoItems.add("Username: \n" + recyclerItem.getUsername());
            InfoItems.add("Email: \n" + recyclerItem.getEmail());
            InfoItems.add("Address: \n" + recyclerItem.getAddress());
            InfoItems.add("Phone: \n" + recyclerItem.getPhone());
            InfoItems.add("Website: \n" + recyclerItem.getWebsite());
            InfoItems.add("Company: \n" + recyclerItem.getCompany());

            final CharSequence[] Infos = InfoItems.toArray(new String[ InfoItems.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Contact Info of " + recyclerItem.getName());
            builder.setItems(Infos, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String selectedInfo = Infos[i].toString();
                    Toast.makeText(context, selectedInfo, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "Closed " + recyclerItem.getName() + "'s Contact Info", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
    }
}
