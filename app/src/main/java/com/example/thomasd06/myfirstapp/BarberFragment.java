package com.example.thomasd06.myfirstapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BarberFragment extends Fragment {

    public boolean isBarber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barber, container, false);

        Button noButton = view.findViewById(R.id.not_barber_button);
        TextView warningText = view.findViewById(R.id.warning_text);
        Button yesButton = view.findViewById(R.id.is_barber_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)BarberFragment.this.getActivity()).goBackToHomeScreen(true);

            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)BarberFragment.this.getActivity()).goBackToHomeScreen(false);

            }
        });


        return view;

    }


}
