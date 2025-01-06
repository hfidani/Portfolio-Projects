package vlille.controlcenter;

import java.util.List;
import java.util.Random;
import exceptions.*;
import vlille.bike.Bike;

/**
 * The RandomStrategy class implements a random redistribution strategy
 * for balancing the bike-sharing system.
 */

public class RandomStrategy implements RedistributionStrategy {

    private Random random = new Random();
    private final double IDEAL_CAPACITY_PERCENTAGE = 0.75; 


    /**
     * Default constructor for RandomStrategy.
     */
    public RandomStrategy() {}


    /**
     * Redistributes bikes randomly among stations to maintain a balance.
     *
     * @param controlCenter The control center managing the bike-sharing system.
     */
    
    @Override
    public void redistribute(ControlCenter controlCenter) {
        List<Station> stations = controlCenter.getStations();
        for (Station station : stations) {
            int idealCapacity = (int) (station.getCapacity() * IDEAL_CAPACITY_PERCENTAGE);
            int currentBikeCount = station.getNumberOfBikes();
            int bikesToMove = currentBikeCount - idealCapacity;

            while (bikesToMove > 0) {
                Station targetStation = selectRandomStation(stations, station);
                if (targetStation != null && !targetStation.IsFull() && targetStation != station) {
                    try {
                        Bike bikeToRemove = station.selectBikeForRemoval();
                        try {
                            station.TakeBike(bikeToRemove); // Catch BikeNotRemovableException here
                            targetStation.addBike(bikeToRemove, targetStation.findEmptySlot());
                            bikesToMove--;
                        } catch (BikeNotRepairableException e) {
                            System.err.println("Failed to take bike: " + e.getMessage());
                        } catch (BikeNotRemovableException e) {
                            System.err.println("Failed to remove bike: " + e.getMessage());
                        }
                    } catch (OccupiedLocationException e) {
                        System.err.println("Failed to add bike: " + e.getMessage());
                    }
                }
            }
        }
    }



    /**
     * Selects a random station, excluding the current station.
     *
     * @param stations       List of all stations.
     * @param currentStation The station to exclude from selection.
     * @return A randomly selected station different from the current station.
     */

    private Station selectRandomStation(List<Station> stations, Station currentStation) {
        Station targetStation ;
        do {
            targetStation = stations.get(random.nextInt(stations.size()));
        } while (targetStation == currentStation);
        
        return targetStation;
    }
}
