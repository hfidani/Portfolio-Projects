package vlille.bike;
import vlille.controlcenter.* ;

import exceptions.*;

public class Rented extends State {

    @Override
    public void Repair(Bike bike, Service reparator) throws BikeNotRepairableException {
        throw new BikeNotRepairableException("Bike is rented and cannot be repaired.");
    }

    @Override
    public void Rent(Bike bike) throws BikeNotRentableException {
        throw new BikeNotRentableException("Bike is already rented.");
    }

    @Override
    public void Deposit(Bike bike, Station station, int space) throws BikeNotReturnableException {
        try {
            station.addBike(bike, space);
            bike.setState(new InService()); // Change state back to InService after return
        } catch (OccupiedLocationException e) {
            throw new BikeNotReturnableException("No space available to return the bike.");
        }
    }

    @Override
    public void Take(Bike bike, Station station) throws BikeNotRemovableException {
        throw new BikeNotRemovableException("Bike is rented and cannot be removed from the station.");
    }

    @Override
    public boolean isRented() {
        return true;
    }
}
