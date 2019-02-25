package com.example.thomasd06.myfirstapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.Persistence;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private EditText searchBar;
    private Button enterButton;
    ArrayAdapter<BarberShop> adapter;
    List<BarberShop> shops;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchBar = view.findViewById(R.id.search_bar);
        enterButton = view.findViewById(R.id.enter_button);


        final ListView listView = view.findViewById(R.id.search_results);
        shops = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, shops);
        listView.setAdapter(adapter);


        Bundle args = getArguments();

        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String searchBarText = searchBar.getText().toString();


                    if(!searchBarText.isEmpty()){
                        DataQueryBuilder query = DataQueryBuilder.create();
                        String whereClause = "shopName = '" + searchBarText + "'" ;
                        query.setWhereClause(whereClause);
                        Backendless.Persistence.of(BarberShop.class).find(query, new AsyncCallback<List<BarberShop>>() {
                            @Override
                            public void handleResponse(List<BarberShop> response) {
                                Log.d("HomeFragment", response.toString());
                                shops.clear();
                                shops.addAll(response);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                    }






    }
});
        return view;
    }}
