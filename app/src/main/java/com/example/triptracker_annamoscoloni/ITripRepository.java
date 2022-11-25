package com.example.triptracker_annamoscoloni;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Defines CRUD methods to be implemented in the class that holds
 * all of the trips.
 */
public interface ITripRepository {

    /**
     * Gets all of the trips in the current repository.
     * @return The collection of trip objects in the current trip repository.
     */
    public ArrayList<Trip> get();

    /**
     * Gets the trip in the trip repository that has the specified id.
     * An id that is below the id minimum will automatically return null.
     * @param id The id of the trip object to retrieve.
     * @return The Trip object from the trip repository with the specified id
     * if it is found in the trips collection; Null otherwise.
     */
    public Trip getById(int id);

    /**
     * Gets all of the trip objects in the trip repository
     * that took place on the specified date. If no trips
     * took place on the specified date, an empty collection of trips is returned.
     * @param date The date of the Trip object(s) to retrieved.
     * @return The collection of trip objects that took place on the
     * specified date.
     */
    public ArrayList<Trip> getByDate(LocalDate date);

    /**
     * Adds the specified Trip object to the trip repository.
     * @param trip The Trip object to add to the trip repository.
     */
    public void add(Trip trip);

    /** Updates the trip object with the corresponding id in the repository
     * with the specified Trip object.
     * @param id The id of the trip in the repository to be updated.
     * @param trip The Trip object in the repository to update the trip with.
     * @return The updated trip object if the trip to update is found in the
     * repository; Null otherwise.
     */
    public Trip update(int id, Trip trip);

    /**
     * Deletes the specified trip object in the repository.
     * @param trip The Trip object in the repository to delete.
     */
    public void delete(Trip trip);
}
