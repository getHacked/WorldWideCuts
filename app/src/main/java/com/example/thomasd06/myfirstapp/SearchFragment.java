package com.example.thomasd06.myfirstapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.Persistence;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class SearchFragment extends Fragment {
    private EditText searchBar;
    private Button enterButton;
    private boolean isBarber;
    private TextView searchResults;
    private EditText barbershopRating;
    private EditText barbershopLocation;
    private BarberShop newBarbershop;
    private List <String> currentBarbershop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchBar = view.findViewById(R.id.search_bar);
        enterButton = view.findViewById(R.id.enter_button);
        searchResults = view.findViewById(R.id.search_results);
        barbershopLocation = view.findViewById(R.id.barbershop_location);
        barbershopRating = view.findViewById(R.id.barbershop_rating);
        barbershopLocation.setVisibility(view.INVISIBLE);
        barbershopRating.setVisibility(view.INVISIBLE);

        Bundle args = getArguments();
        if (args != null) {
            Log.d("HomeFragment", args.toString());
            isBarber = args.getBoolean("IS_BARBER");

        }

        if (isBarber) {
            searchBar.setHint("Enter barbershop name");
            barbershopRating.setVisibility(view.VISIBLE);
            barbershopLocation.setVisibility(view.VISIBLE);
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String searchBarText = searchBar.getText().toString();




                if (!isBarber) {

                    if(!searchBarText.isEmpty()){
                        DataQueryBuilder query = DataQueryBuilder.create();
                        String whereClause = "shopName = '" + searchBarText + "'" ;
                        query.setWhereClause(whereClause);
                        Backendless.Persistence.of(BarberShop.class).find(query, new AsyncCallback<List<BarberShop>>() {
                            @Override
                            public void handleResponse(List<BarberShop> response) {
                                Log.d("HomeFragment", response.toString());
                                if(response.isEmpty()){
                                    searchResults.setText("It appears we don't have that barbershop in our database");
                                }else{
                                    searchResults.setText(response.toString() + " Are any of these the barbershop you're looking for?");
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                    }





                } else {
                    String name = searchBarText;
                    String location = barbershopLocation.getText().toString();
                    double rating = Double.parseDouble(barbershopRating.getText().toString());

                    newBarbershop = new BarberShop(name, location, rating);


                    searchResults.setText(name + " has been successfully added to the WWC database");


                    Backendless.Persistence.save(newBarbershop, new AsyncCallback<BarberShop>() {
                        @Override
                        public void handleResponse(BarberShop response) {

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });




                }
            }
        });
        return view;
    }
}
