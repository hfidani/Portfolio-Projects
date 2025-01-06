package vlille.bike;

import vlille.controlcenter.* ;


import exceptions.*;

/**
 * The State abstract class represents the state of a bike in the bike-sharing system.
 * This class serves as the base for different bike states, such as InService, OutOfService, etc.
 * It defines the common operations that can be performed on a bike, depending on its state.
 */
public abstract class State {


    /**
     * Attempts to repair the bike. The actual repair logic depends on the specific state.
     *
     * @param bike The bike to be repaired.
     * @param reparator The service responsible for repairing the bike.
     * @throws BikeNotRepairableException if the bike cannot be repaired in its current state.
     */
    public abstract void Repair(Bike bike, Service reparator) throws BikeNotRepairableException;

    /**
     * Attempts to rent the bike to a user. The ability to rent depends on the bike's state.
     *
     * @param bike The bike to be rented.
     * @throws BikeNotRentableException if the bike cannot be rented in its current state.
     */
    public abstract void Rent(Bike bike) throws BikeNotRentableException;

    /**
     * Attempts to deposit the bike at a station. The ability to deposit depends on the bike's state.
     *
     * @param bike The bike to be deposited.
     * @param station The station where the bike is being deposited.
     * @param space The specific space at the station where the bike will be deposited.
     * @throws BikeNotReturnableException if the bike cannot be deposited in its current state.
     */
    public abstract void Deposit(Bike bike, Station station, int space) throws BikeNotReturnableException;

    /**
     * Attempts to remove the bike from a station. This operation's availability depends on the bike's state.
     *
     * @param bike The bike to be removed.
     * @param station The station from which the bike is being removed.
     * @throws BikeNotRemovableException if the bike cannot be removed from the station in its current state.
     */
    public abstract void Take(Bike bike, Station station) throws BikeNotRemovableException;

    public boolean isRented() {
        return false;
    }
}
