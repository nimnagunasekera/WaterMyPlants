package com.example.watermyplants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeFragment extends Fragment {

    private TextView dateTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dateTextView = view.findViewById(R.id.dateTextView);
        updateDateTextView(); // Set the initial date and time

        PlantAdapter adapter = new PlantAdapter(getContext(), new ArrayList<Plants>());
        RecyclerView plantsRecView = view.findViewById(R.id.plantsRecView);

        plantsRecView.setAdapter(adapter);
        plantsRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Plants> plants = new ArrayList<>();
        plants.add(new Plants("Pothos Marble Queen Plant", "Today, 17:00", ContextCompat.getDrawable(requireContext(), R.drawable.marble)));
        plants.add(new Plants("Fittonia Plant", "Tomorrow, 08:00", ContextCompat.getDrawable(requireContext(), R.drawable.fittonia)));
        plants.add(new Plants("Birds Nest Fern Plant", "2022-09-10, 12:00", ContextCompat.getDrawable(requireContext(), R.drawable.birds)));
        adapter.notifyDataSetChanged();
        adapter.mPlantsList.addAll(plants);

        return view;
    }

    private void updateDateTextView(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String dateString = dateFormat.format(currentDate) + " - " + timeFormat.format(currentDate);
        dateTextView.setText(dateString);
    }


}




