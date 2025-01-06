package vlille.controlcenter;

import java.util.List;
import java.util.ArrayList;
import exceptions.*;
import vlille.bike.*;

public class RoundRobinStrategy implements RedistributionStrategy {

    public RoundRobinStrategy() {
    }

    @Override
    public void redistribute(ControlCenter controlCenter) {
        List<Station> stations = controlCenter.getStations();
        List<Bike> bikesForRedistribution = new ArrayList<>();

        try {
            bikesForRedistribution = getBikesForRedistribution(stations);
        } catch (BikeNotRepairableException e) {
            System.err.println("Error while preparing bikes for redistribution: " + e.getMessage());
        }

        int retryLimit = stations.size();
        int retryCount = 0;

        while (!bikesForRedistribution.isEmpty() && retryCount < retryLimit) {
            for (Station station : stations) {
                if (station.getNumberOfBikes() < station.getCapacity() && !bikesForRedistribution.isEmpty()) {
                    Bike bike = bikesForRedistribution.remove(0);
                    try {
                        station.addBike(bike, station.findEmptySlot());
                        System.out.println("Bike added to station " + station.getName());
                    } catch (OccupiedLocationException e) {
                        System.err.println("Failed to add bike: " + e.getMessage());
                        bikesForRedistribution.add(bike);
                    }
                }
            }
            retryCount++;
        }

        if (!bikesForRedistribution.isEmpty()) {
            System.err.println("Some bikes could not be redistributed after " + retryLimit + " attempts.");
        }
    }

    private List<Bike> getBikesForRedistribution(List<Station> stations) throws BikeNotRepairableException {
        List<Bike> bikes = new ArrayList<>();
        for (Station station : stations) {
            while (station.getNumberOfBikes() > 0) {
                try {
                    Bike bikeToRemove = station.selectBikeForRemoval();
                    if (bikeToRemove != null) {
                        station.TakeBike(bikeToRemove);
                        bikes.add(bikeToRemove);
                    }
                } catch (BikeNotRemovableException e) {
                    System.err.println("Failed to remove bike: " + e.getMessage());
                    break;
                }
            }
        }
        return bikes;
    }
}