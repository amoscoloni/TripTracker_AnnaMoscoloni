package com.example.triptracker_annamoscoloni;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Defines a car trip and its start data, the duration of the trip (time), odometer at the start and end of the trip
 * and whether the trip was personal or for Uber.
 */
public class Trip {
    // backing fields
    private int id;
    private LocalDateTime tripDate;
    private double tripTime;
    private double odometerStart;
    private double odometerEnd;
    private TripType tripType;

    // constants
    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
    public static final int MIN_ID = 1;
    private static final double DEFAULT_TIME = 0;
    private static final double DEFAULT_ODOMETER_START = 0;
    private static final double DEFAULT_ODOMETER_END = 0;
    private static final TripType DEFAULT_TRIP_TYPE = TripType.UBER;
    private static final double MIN_ODOMETER_VALUE = 0;

    public enum TripType{
        PERSONAL,
        UBER
    }

    private static int currentId = MIN_ID - 1; // stores which id will be assigned next

    // maybe add validation
    public Trip(LocalDateTime tripDate, double tripTime, double odometerStart, double odometerEnd, TripType tripType){
        if(odometerStart < MIN_ODOMETER_VALUE || odometerEnd < MIN_ODOMETER_VALUE)
            throw new IllegalArgumentException(String.format("Odometer value must be greater than or equal to ", MIN_ODOMETER_VALUE));

        id = ++currentId; // increment before id is assigned
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.odometerStart = odometerStart;
        this.odometerEnd = odometerEnd;
        this.tripType = tripType;
    }

    public Trip(){
        this(DEFAULT_DATE, DEFAULT_TIME, DEFAULT_ODOMETER_START, DEFAULT_ODOMETER_END, DEFAULT_TRIP_TYPE);
    }

    /**
     * Gets the current Trip instance's id.
     * @return The trip's id.
     */

    public int getId(){ return id; }

    /**
     * Gets the current Trip instance's trip date and time.
     * @return The date and time that the trip took place.
     */
    public LocalDateTime getTripDate() {
        return tripDate;
    }

    /**
     * Gets the current Trip instance's trip time (the amount of time that the trip took).
     * @return The amount of time that the trip took.
     */
    public double getTripTime() {
        return tripTime;
    }

    /**
     * Gets the current Trip's car's odometer value at the start of the trip.
     * @return The starting odometer value of the car used in the current trip.
     */
    public double getOdometerStart(){
        return odometerStart;
    }

    /**
     * Gets the current Trip's car's odometer value at the end of the trip.
     * @return The ending odometer value of the car used in the current trip.
     */
    public double getOdometerEnd(){
        return odometerEnd;
    }

    /**
     * Gets the current Trip's type of trip.
     * @return The trip type of the current Trip.
     */
    public TripType getTripType() {
        return tripType;
    }

    /**
     * Updates the current Trip with the values stored in the specified Trip object (except for Id)
     * @param trip The trip with the values to update the current trip with.
     */
    public void update(Trip trip){
        this.tripDate = trip.tripDate;
        this.tripTime = trip.tripTime;
        this.odometerStart = trip.odometerStart;
        this.odometerEnd = trip.odometerEnd;
        this.tripType = trip.tripType;
    }

}
