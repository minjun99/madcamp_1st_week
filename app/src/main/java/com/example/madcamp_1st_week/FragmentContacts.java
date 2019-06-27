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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {
    View view;
    private RecyclerAdapter adapter;
    private RequestQueue mQueue;

    private List<String> listName = new ArrayList<String>();
    private List<String> listUsername = new ArrayList<String>();
    private List<String> listEmail = new ArrayList<String>();
    private List<String> listAddress = new ArrayList<String>();
    private List<String> listPhone = new ArrayList<String>();
    private List<String> listWebsite = new ArrayList<String>();
    private List<String> listCompany = new ArrayList<String>();
    private List<Integer> listImage = new ArrayList<Integer>();

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_fragment, container, false);
        mQueue = Volley.newRequestQueue(view.getContext());
        init();
        jsonParse();
        return view;
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void jsonParse() {
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject user = response.getJSONObject(i);

                        listName.add(user.getString("name"));
                        listUsername.add(user.getString("username"));
                        listEmail.add(user.getString("email"));

                        JSONObject address = user.getJSONObject("address");
                        listAddress.add(
                                address.getString("street") +
                                        address.getString("suite") +
                                        address.getString("city") +
                                        address.getString("zipcode"));

                        listPhone.add(user.getString("phone"));
                        listWebsite.add(user.getString("website"));

                        JSONObject company = user.getJSONObject("company");
                        listCompany.add(
                                company.getString("name") +
                                        company.getString("catchPhrase") +
                                        company.getString("bs"));

                        listImage.add(R.drawable.ic_account_circle);

                        RecyclerItem recyclerItem = new RecyclerItem();
                        recyclerItem.setName(listName.get(i));
                        recyclerItem.setResId(listImage.get(i));
                        recyclerItem.setUsername(listUsername.get(i));
                        recyclerItem.setEmail(listEmail.get(i));
                        recyclerItem.setAddress(listAddress.get(i));
                        recyclerItem.setPhone(listPhone.get(i));
                        recyclerItem.setWebsite(listWebsite.get(i));
                        recyclerItem.setCompany(listCompany.get(i));

                        adapter.addItem(recyclerItem);

                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
