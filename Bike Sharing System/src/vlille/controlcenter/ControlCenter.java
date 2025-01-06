package vlille.controlcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import exceptions.*;
import vlille.bike.Bike;
import vlille.bike.OutOfService;
import vlille.bike.Service;
import vlille.bike.UnderReparation;
import vlille.bike.InService;
import vlille.bike.Rented;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The ControlCenter class manages the bike sharing system's operations,
 * including bike redistribution, maintenance, and rentals.
 */

public class ControlCenter implements Observer {
    private static ControlCenter instance; 
    private ArrayList<Station> stations;
    private List<Service> services; // Services available for repairing bikes
    private ArrayList<Bike> bikesRented; // Bikes that are currently rented
    private ArrayList<Bike> bikesDeposit; // Bikes that are deposited at stations
    private RedistributionStrategy modeDistribution;
    private Timer intervalle;

    /**
     *constructor for the ControlCenter class.
     *
     * @param modeDistribution The redistribution strategy to use.
     */

    public ControlCenter(RedistributionStrategy modeDistribution) {
        this.stations = new ArrayList<>();
        this.services = new ArrayList<>();
        this.bikesRented = new ArrayList<>();
        this.bikesDeposit = new ArrayList<>();
        this.modeDistribution = modeDistribution;
        this.stations = new ArrayList<>(stations);
        this.intervalle = new Timer () ;
    }

    /**
     * Singleton method to get the instance of the ControlCenter.
     *
     * @param modeDistribution The redistribution strategy to use.
     * @return The singleton instance of ControlCenter.
     */

    public static ControlCenter getInstance(RedistributionStrategy modeDistribution) {
        if (instance == null) {
            instance = new ControlCenter(modeDistribution);
        }
        return instance;
    }

    /**
     * Repairs all bikes that need maintenance using available services.
     */

    public void fix() {
        for (Service service : services) {
            for (Bike bike : bikesDeposit) {
                    try {
                        service.ControlService(bike);
                    } catch (BikeNotRepairableException e) {
                        System.err.println("Failed to repair bike: " + e.getMessage());
                    }
            }
        }
    }
    
    /**
     * Redistributes bikes across stations based on the chosen strategy.
     */

    public void distribute() {
        modeDistribution.redistribute(this);
    }

    /**
     * Adds a service to the system (e.g., repair services).
     *
     * @param service The service to add.
     */

    public void addService(Service service) {
        this.services.add(service);
    }

    /**
     * Removes a service from the system.
     *
     * @param service The service to remove.
     */

    public void removeService(Service service) {
        this.services.remove(service);
    }

    /**
     * Returns the list of services in the system.
     *
     * @return List of services.
     */
    public List<Service> getServices(){
    	return this.services;
    }

    /**
     * Adds a bike to the list of rented bikes.
     *
     * @param bike The bike to add.
     */

    public void addBikeToRent(Bike bike) {
      
       this.bikesRented.add(bike);
    }

    /**
     * Adds a bike to the list of deposited bikes.
     *
     * @param bike The bike to add.
     */

    public void addBikeToDeposit(Bike bike) {
        this.bikesDeposit.add(bike);
    }

    /**
     * Returns the list of deposited bikes.
     *
     * @return List of deposited bikes.
     */
    
    public List<Bike> getBikeToDeposit(){
    	return this.bikesDeposit;
    }

    /**
     * Simulates random bike deposits and withdrawals at stations.
     */

    public void randomDepositWithdrawal() {
        Random rand = new Random();
        for (Station station : stations) {
            // 50% chance to return a bike if we have any rented bikes
            if (rand.nextBoolean() && !bikesRented.isEmpty()) {
                Bike bike = bikesRented.remove(rand.nextInt(bikesRented.size()));
                int emptySlot = station.findEmptySlot();
                if (emptySlot != -1) {
                    try {
                        station.addBike(bike, emptySlot);
                    } catch (OccupiedLocationException e) {
                        System.err.println("Failed to deposit bike: " + e.getMessage());
                        bikesRented.add(bike);
                    }
                } else {
                    bikesRented.add(bike);
                }
            } else {
                List<Bike> stationBikes = station.getBikes();
                List<Bike> inServiceBikes = new ArrayList<>();
                for (Bike b : stationBikes) {
                    if (b != null && b.getState() instanceof InService) {
                        inServiceBikes.add(b);
                    }
                }
    
                if (!inServiceBikes.isEmpty()) {
                    Bike bikeToRent = inServiceBikes.get(rand.nextInt(inServiceBikes.size()));
                    try {
                        bikeToRent.getState().Rent(bikeToRent);
                        bikeToRent.setState(new Rented());
                        stationBikes.remove(bikeToRent);
                        bikesRented.add(bikeToRent);
                    } catch (BikeNotRentableException e) {
                    }
                }
            }
        }
    }
    

    /**
     * Periodically simulates deposits and withdrawals at stations.
     */
    
    public void depositWithdrawalSimulateIntervalle() {
        intervalle.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                randomDepositWithdrawal();
                System.out.println("random Deposit and Withdrawal done");
            }
        }, 0, 4 *1000);
    }

    /**
     * Periodically checks for empty stations and redistributes bikes.
     */

    public void EmptyStationsSimulateIntervalle(){
        intervalle.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Station station : stations){
                    if (station.getNumberOfBikes() == 0){
                        distribute();
                        System.out.println("distribute done");
                    } 
                } 
                
            }
        }, 0, 8 *1000);

    }

    /**
     * Returns the list of currently rented bikes.
     *
     * @return List of rented bikes.
     */

    public List<Bike> getBikesRented() {
        return this.bikesRented;
    }

    /**
     * Returns the list of all stations.
     *
     * @return List of stations.
     */

    public List<Station> getStations() {
        return this.stations; 
    }

    /**
     * Adds a station to the system.
     *
     * @param station The station to add.
     */

    public void AddStation(Station station){
        this.stations.add(station) ;
    } 

	public void update(Station station) {
		
	}
    
}


