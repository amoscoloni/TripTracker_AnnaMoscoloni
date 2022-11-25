package com.example.triptracker_annamoscoloni;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);

        String tag = getTag();

        if(tag == getString(R.string.datePickerFilter))
            MainActivity.tripItemFragment.setFilterDate(LocalDate.of(year, month + 1, day));
        else
            MainActivity.addTripFragment.setInputDate(date);
        // AddTrip addTrip = (AddTrip) getActivity().getSupportFragmentManager().findFragmentById(R.id.add_trip);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
}