package com.example.triptracker_annamoscoloni;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Defines the model for the repository that stores and manipulates the trips in our application.
 */
public class TripRepository implements ITripRepository{
    public ArrayList<Trip> trips;

    public TripRepository(){
        this(new ArrayList<Trip>());
    }

    public TripRepository(ArrayList<Trip> trips){
        this.trips = trips;
    }

    @Override
    public ArrayList<Trip> get() {
        return trips;
    }

    @Override
    public Trip getById(int id) {
        if(id < Trip.MIN_ID)
            return null;

        for(int i = 0; i < trips.size(); i++){
            Trip trip = trips.get(i);

            if(trip.getId() == id)
                return trip;
        }

        return null;
    }

    @Override
    public ArrayList<Trip> getByDate(LocalDate date) {
        ArrayList<Trip> tripsWithSpecifiedDate = new ArrayList<>();

        for(int i = 0; i < trips.size(); i++){
            Trip trip = trips.get(i);

            if(trip.getTripDate().toLocalDate() == date)
                tripsWithSpecifiedDate.add(trip);
        }

        return tripsWithSpecifiedDate;
    }

    @Override
    public void add(Trip trip) {
        if(trip != null)
            trips.add(trip);
    }

    @Override
    public Trip update(int id, Trip trip) {
        Trip tripToUpdate = getById(id);

        if (trip == null)
            return null;

        tripToUpdate.update(trip);

        return tripToUpdate;
    }

    @Override
    public void delete(Trip trip) {
        for(int i = 0; i < trips.size(); i++){
            if(trips.get(i).equals(trip)){
                trips.remove(i);
                break;
            }
        }
    }
}
