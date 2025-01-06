package vlille.bike;

import exceptions.BikeNotRepairableException;

/**
 * The Service interface defines the contract for services that can interact with bikes,
 * such as maintenance and repairs, in the bike-sharing system.
 */
public interface Service {

    /**
     * Performs a service operation on the specified bike, such as repairing it.
     *
     * @param bike The bike to service.
     * @throws BikeNotRepairableException If the bike cannot be repaired or serviced.
     */
    void ControlService(Bike bike) throws BikeNotRepairableException;
}
