package com.example.triptracker_annamoscoloni;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TripSampleData {
    public static ArrayList<Trip> getSampleTripData(){
        ArrayList<Trip> sampleData = new ArrayList<>();

        try{
            sampleData.add(new Trip(LocalDateTime.now(), 2.5, 24, 100, Trip.TripType.UBER));
            sampleData.add(new Trip(LocalDateTime.of(LocalDate.of(2022, 2, 2), LocalTime.NOON), 2.5, 24, 100, Trip.TripType.UBER));
            sampleData.add(new Trip(LocalDateTime.of(LocalDate.of(2022, 1, 22), LocalTime.NOON), 2, 100, 178, Trip.TripType.PERSONAL));
            sampleData.add(new Trip(LocalDateTime.now(), 2, 100, 240, Trip.TripType.UBER));
            sampleData.add(new Trip(LocalDateTime.now(), 0.5, 240, 270, Trip.TripType.PERSONAL));
            sampleData.add(new Trip(LocalDateTime.now(), 0.25, 270, 285, Trip.TripType.PERSONAL));
            sampleData.add(new Trip(LocalDateTime.now(), 0.5, 285, 300, Trip.TripType.UBER));
            sampleData.add(new Trip(LocalDateTime.now(), 0.75, 300, 350, Trip.TripType.PERSONAL));
            sampleData.add(new Trip(LocalDateTime.now(), 1, 350, 425, Trip.TripType.UBER));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sampleData;
    }
}
