package com.example.triptracker_annamoscoloni;

import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.triptracker_annamoscoloni.databinding.FragmentTripItemBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    // context menu help for recycler view found at https://www.bswen.com/2021/05/others-android-how-to-add-context-menu-to-recyclerview.html
    private ArrayList<Trip> trips;
    private int position;

    public MyItemRecyclerViewAdapter(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTripItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public Trip getSelectedTrip(){
        if(position >= 0 && trips != null && position < trips.size()){
            return trips.get(position);
        }

        return null;
    }

    public void changeList(boolean filter, LocalDate filterDate){
        if(!filter){
            trips = MainActivity.tripRepository.trips;
        }
        else{
            ArrayList<Trip> filterTrip = new ArrayList<>();

            for(int i = 0; i < trips.size(); i++){
                if(trips.get(i).getTripDate().toLocalDate().equals(filterDate))
                    filterTrip.add(trips.get(i));
            }

            this.trips = filterTrip;
        }

        MainActivity.tripItemFragment.resetAdapter();
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private FragmentTripItemBinding binding;
        public Trip trip;

        public ViewHolder(FragmentTripItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemView.setOnLongClickListener(this);
        }

        private void bind(Trip trip) {
            trip = trip;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
            binding.tripDate.setText(trip.getTripDate().format(formatter));
            binding.tripStartTime.setText(trip.getTripDate().format(timeFormatter));
            binding.startOdometer.setText(String.format("%.2f", trip.getOdometerStart()));
            binding.endOdometer.setText(String.format("%.2f", trip.getOdometerEnd()));
            binding.totalTime.setText(String.format("%.1f hours", trip.getTripTime()));

            // set background colour for trip depending on type of trip
            Trip.TripType tripType = trip.getTripType();
            int resourceId;

            switch (tripType){
                case UBER:
                    resourceId = R.drawable.rounded_rectangle_uber;
                    break;
                case PERSONAL:
                    resourceId = R.drawable.rounded_rectangle_personnal;
                    break;
                default:
                    resourceId = R.drawable.rounded_rectangle_personnal;
                    break;
            }

            binding.item.setBackgroundResource(resourceId);
        }


        @Override
        public boolean onLongClick(View view) {
            position = getAbsoluteAdapterPosition();
            return false;
        }

    }


}