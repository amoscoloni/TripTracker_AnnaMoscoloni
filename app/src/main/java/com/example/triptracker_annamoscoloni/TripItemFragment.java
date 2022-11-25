package com.example.triptracker_annamoscoloni;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.triptracker_annamoscoloni.databinding.FragmentTripItemBinding;
import com.example.triptracker_annamoscoloni.databinding.FragmentTripItemListBinding;
import com.example.triptracker_annamoscoloni.placeholder.PlaceholderContent;

import org.w3c.dom.Text;

import java.time.LocalDate;

/**
 * A fragment representing a list of Items.
 */
public class TripItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FragmentTripItemListBinding binding;
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(MainActivity.tripRepository.trips);
    private TextView filterDisplay;
    private static RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TripItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TripItemFragment newInstance(int columnCount) {
        TripItemFragment fragment = new TripItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTripItemListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getActivity().findViewById(R.id.filter_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(getView());
            }
        });

        MainActivity.displayFilterValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    myItemRecyclerViewAdapter.changeList(false, LocalDate.now());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        filterDisplay = getActivity().findViewById(R.id.date_filter_value);

        // Set the adapter
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            resetAdapter();

            registerForContextMenu(recyclerView);
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MainActivity activity = (MainActivity) getActivity();

        switch (item.getItemId()) {
            case R.id.edit:
                activity.loadAddFragmentWithData(myItemRecyclerViewAdapter.getSelectedTrip().getId());
                return true;
            case R.id.delete:
                MainActivity.tripRepository.delete(myItemRecyclerViewAdapter.getSelectedTrip());
                myItemRecyclerViewAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void showDatePickerDialog(View view) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.datePickerFilter));
    }

    public void resetAdapter(){
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
    }

    public void setFilterDate(LocalDate inputFilter){
        MainActivity.displayFilterValue.setText(AddTrip.formatDate(inputFilter.getDayOfMonth(), inputFilter.getMonthValue(), inputFilter.getYear()));
        myItemRecyclerViewAdapter.changeList(true, inputFilter);
    }


}