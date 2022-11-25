package com.example.triptracker_annamoscoloni;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.triptracker_annamoscoloni.databinding.FragmentAddTripBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTrip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTrip extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentAddTripBinding binding;
    public static final int ADD_MODE_ID = -1;  // if passed in id is -1, we know we're not editing a trip but rather adding it
    private LocalDate inputDate;
    private LocalTime inputTime;
    private int idToEdit;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddTrip() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTrip.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTrip newInstance(String param1, String param2) {
        AddTrip fragment = new AddTrip();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTripBinding.inflate(inflater, container, false);
        // int idToEdit = Integer.parseInt(savedInstanceState.getString(getString(R.string.trip_id_add_fragment)));
        idToEdit = MainActivity.idToModify;

        if(idToEdit != ADD_MODE_ID)
            loadTripIntoEditView(idToEdit);


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
            }
        });

        binding.dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(getView());
            }
        });

        binding.timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(getView());
            }
        });

        return binding.getRoot();
    }

    private void getInput(){
        String odoStartString = binding.odometerStartInput.getText().toString();
        String odoEndString = binding.odometerEndInput.getText().toString();
        String durationString = binding.durationInput.getText().toString();

        // do not execute rest of the method if any field is left empty
        if(odoStartString == "" || odoEndString == "" || durationString == "" || inputDate == null
                || inputTime == null){ return; }

        double odometerStart = Double.parseDouble(odoStartString);
        double odometerEnd = Double.parseDouble(odoEndString);
        double duration = Double.parseDouble(durationString);
        Trip.TripType tripType = Trip.TripType.PERSONAL;

        if(binding.uberInput.isChecked())
            tripType = Trip.TripType.UBER;

        LocalDateTime dateAndTime = LocalDateTime.of(inputDate, inputTime);

        Trip newTrip = new Trip(dateAndTime, duration, odometerStart, odometerEnd, tripType);

        if(idToEdit != ADD_MODE_ID)
            MainActivity.tripRepository.update(idToEdit, newTrip);
        else
            MainActivity.tripRepository.add(newTrip);

        clearInputs();
    }

    private void clearInputs(){
        binding.timeOutput.setText("");
        binding.dateOutput.setText("");
        binding.odometerStartInput.setText("");
        binding.odometerEndInput.setText("");
        binding.durationInput.setText("");
        binding.tripTypeInput.clearCheck();
    }

    public void showDatePickerDialog(View view) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.datePickerFragment));
    }

    public void showTimePicker(View view) {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.timePickerFragment));
    }

    private void loadTripIntoEditView(int id){
        try{
            Trip tripToEdit = MainActivity.tripRepository.getById(id);

            setInputTime(tripToEdit.getTripDate().toLocalTime());
            setInputDate(tripToEdit.getTripDate().toLocalDate());

            binding.odometerStartInput.setText(String.format("%.2f", tripToEdit.getOdometerStart()));
            binding.odometerEndInput.setText(String.format("%.2f", tripToEdit.getOdometerEnd()));
            binding.durationInput.setText(String.format("%.2f", tripToEdit.getTripTime()));

            if(tripToEdit.getTripType() == Trip.TripType.PERSONAL)
                binding.personnalInput.setChecked(true);
            else
                binding.uberInput.setChecked(true);

        } catch(Exception e){  e.printStackTrace(); }
    }

    public static String formatDate(int day, int month, int year){
        return String.format("%d/%d/%d", day, month, year);
    }

    private String formatTime(LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public void setInputDate(LocalDate date){
        inputDate = date;
        binding.dateOutput.setText(formatDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear()));
    }

    public void setInputTime(LocalTime time){
        inputTime = time;
        binding.timeOutput.setText(formatTime(time));
    }
}