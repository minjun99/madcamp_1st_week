package com.example.madcamp_1st_week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class FragmentContacts extends Fragment {
    View view;
    private RecyclerAdapter adapter;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_fragment, container, false);
        init();
        getData();
        return view;
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        List<String> listName = Arrays.asList(
                "Jason",
                "David",
                "John",
                "Kate",
                "Park",
                "Kim",
                "Elizabeth",
                "IU",
                "Lisa",
                "Marshmello",
                "Avicci",
                "Danny",
                "Ahri",
                "Han",
                "Smith",
                "Wick",
                "Joshua",
                "Harry",
                "Cho"
        );

        for (int i = 0; i < listName.size(); i++) {
            RecyclerItem recyclerItem = new RecyclerItem();
            recyclerItem.setName(listName.get(i));

            adapter.addItem(recyclerItem);
        }

        adapter.notifyDataSetChanged();
    }
}
