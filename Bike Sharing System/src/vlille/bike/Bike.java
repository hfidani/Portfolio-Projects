package vlille.bike;

import exceptions.BikeNotRepairableException;

/**
 * The Bike class represents a generic bike in the bike-sharing system.
 * It includes properties and behaviors common to all bikes, such as rental count,
 * state management, and unique identification.
 */
public class Bike implements Transport {

    protected int id; 
    protected int rentalCount; 
    protected final int rentalMax = 10; 
    protected State currentState; 

    /**
     * Constructs a Bike object with a unique identifier and initial state.
     *
     * @param id    The unique identifier of the bike.
     * @param state The initial state of the bike.
     */
    public Bike(int id, State state) {
        this.id = id;
        this.rentalCount = 0;
        this.currentState = state;
    }

    /**
     * Sets the current state of the bike.
     *
     * @param newState The new state of the bike.
     */
    public void setState(State newState) {
        this.currentState = newState;
    }

    /**
     * Gets the current state of the bike.
     *
     * @return The current state of the bike.
     */
    public State getState() {
        return this.currentState;
    }

    /**
     * Gets the unique identifier of the bike.
     *
     * @return The unique identifier of the bike.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the bike.
     *
     * @param id The new unique identifier.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the number of times the bike has been rented.
     *
     * @return The rental count of the bike.
     */
    public int getRentalCount() {
        return this.rentalCount;
    }

    /**
     * Updates the rental count of the bike by incrementing it by one.
     */
    public void updateRentalCount() {
        this.rentalCount++;
    }

    /**
     * Gets the maximum rental count allowed before maintenance is required.
     *
     * @return The maximum rental count.
     */
    public int getRentalMax() {
        return this.rentalMax;
    }

    /**
     * Checks if the bike has reached its maximum rental count.
     * If so, it transitions to the OutOfService state and triggers maintenance.
     *
     * @return true if the bike requires maintenance, false otherwise.
     * @throws BikeNotRepairableException If the bike cannot be repaired.
     */
    public boolean maxrental() throws BikeNotRepairableException {
        if (rentalCount == rentalMax) {
            this.setState(new OutOfService());
            this.getState().Repair(this, new Reparator());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Provides a description of the bike.
     *
     * @return A string representation of the bike's basic information.
     */
    public String decorate() {
        return "Bike ID: " + id;
    }

    @Override
    public String toString() {
        return "Bike [id=" + id + ", rentalCount=" + rentalCount + ", rentalMax=" + rentalMax + ", currentState=" + currentState + "]";
    }
}






