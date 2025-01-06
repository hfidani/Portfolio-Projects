package vlille;

import vlille.controlcenter.*;
import vlille.bike.*;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The Main class serves as the entry point for the bike-sharing system simulation.
 * It offers three modes of simulation: Random, Interactive, and Round Robin,
 * and manages station and bike interactions.
 */
public class Main {

    /**
     * Prints the current state of all stations and rented bikes.
     * 
     * @param controlCenter The control center managing the bike-sharing system.
     */
    private static void printStationsAndRentedBikes(ControlCenter controlCenter) {
        System.out.println("---- Current State of Stations ----");
        for (Station station : controlCenter.getStations()) {
            System.out.println(station.getName() + " (" + station.getNumberOfBikes() + "/" + station.getCapacity() + "):");
            for (Bike bike : station.getBikes()) {
                if (bike != null) {
                    System.out.println("  - Bike ID: " + bike.getId() 
                                       + ", State: " + bike.getState().getClass().getSimpleName() 
                                       + ", Rentals: " + bike.getRentalCount());
                }
            }
        }
        System.out.println("-----------------------------------");

        System.out.println("---- Bikes Currently Rented Out ----");
        for (Bike rentedBike : controlCenter.getBikesRented()) {
            System.out.println("  - Bike ID: " + rentedBike.getId() 
                               + ", State: " + rentedBike.getState().getClass().getSimpleName() 
                               + ", Rentals: " + rentedBike.getRentalCount());
        }
        System.out.println("------------------------------------");
    }

    /**
     * Runs a simulation of the bike-sharing system with the specified redistribution strategy.
     *
     * @param strategy       The redistribution strategy to use (Random or Round Robin).
     * @param simulationName The name of the simulation mode.
     */
    public static void runSimulation(RedistributionStrategy strategy, String simulationName) {
        // Initialize ControlCenter with the selected strategy
        ControlCenter controlCenter = ControlCenter.getInstance(strategy);

        // Add services to ControlCenter
        Reparator reparator = new Reparator();
        controlCenter.addService(reparator);

        // Create stations
        Random rand = new Random();
        for (int i = 1; i <= 5; i++) {
            Station station = new Station("Station " + i, rand.nextInt(11) + 10, controlCenter);
            controlCenter.AddStation(station);
        }

        // Populate stations with bikes
        for (int i = 0; i < 30; i++) {
            Bike bike = (i % 2 == 0) ? new ClassicBike(i, new InService()) : new ElectricBike(i, new InService());
            Station station = controlCenter.getStations().get(rand.nextInt(controlCenter.getStations().size()));
            int slot = station.findEmptySlot();
            if (slot != -1) {
                try {
                    station.addBike(bike, slot);
                } catch (OccupiedLocationException e) {
                    System.err.println("Error adding bike: " + e.getMessage());
                }
            }
        }

        System.out.println("Starting " + simulationName + " simulation...");

        // Run simulation iterations
        for (int iteration = 1; iteration <= 10; iteration++) {
            System.out.println("\n--- Simulation Iteration: " + iteration + " ---");
            controlCenter.randomDepositWithdrawal();

            if (iteration % 3 == 0) {
                controlCenter.fix();
                System.out.println("Attempted to fix bikes (every 3 iterations).");
            }

            if (iteration % 5 == 0) {
                controlCenter.distribute();
                System.out.println("Redistributed bikes (every 5 iterations).");
            }

            printStationsAndRentedBikes(controlCenter);

            try {
                Thread.sleep(2000); // Pause for 2 seconds between iterations
            } catch (InterruptedException e) {
                System.err.println("Simulation interrupted.");
                break;
            }
        }

        System.out.println(simulationName + " simulation ended.");
    }

    /**
     * Runs a random simulation of the bike-sharing system.
     * The simulation includes random deposits, withdrawals, fixes, and redistributions.
     */
    public static void runRandomSimulation() {
        runSimulation(new RandomStrategy(), "Random");
    }

    /**
     * Runs a round-robin simulation of the bike-sharing system.
     * The simulation includes round-robin deposits, withdrawals, fixes, and redistributions.
     */
    public static void runRoundRobinSimulation() {
        runSimulation(new RoundRobinStrategy(), "Round Robin");
    }

    /**
     * Runs an interactive simulation where users can manage the bike-sharing system.
     * Users can perform actions like renting, returning, fixing, and redistributing bikes.
     */
    public static void runInteractiveSimulation() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Initialize ControlCenter with RandomStrategy by default
        RedistributionStrategy strategy = new RandomStrategy();
        ControlCenter controlCenter = ControlCenter.getInstance(strategy);
        Reparator reparator = new Reparator();
        controlCenter.addService(reparator);

        System.out.print("How many stations would you like to create? ");
        int nbOfStations = scanner.nextInt();

        for (int i = 0; i < nbOfStations; i++) {
            Station station = new Station("Station " + (i + 1), random.nextInt(10) + 10, controlCenter);
            controlCenter.AddStation(station);

            for (int j = 0; j < station.getCapacity(); j++) {
                Bike bike = (random.nextBoolean()) 
                    ? new ClassicBike(j, new InService())
                    : new ElectricBike(j, new InService());
                try {
                    station.addBike(bike, j);
                } catch (OccupiedLocationException e) {
                    System.err.println("Occupied slot at station " + station.getName() + ": " + e.getMessage());
                }
            }
        }

        printStationsAndRentedBikes(controlCenter);

        while (true) {
            System.out.println("\nSimulation Actions:");
            System.out.println("1. Rent a bike");
            System.out.println("2. Return a bike");
            System.out.println("3. Repair bikes");
            System.out.println("4. Redistribute bikes");
            System.out.println("5. Show station and rented bike states");
            System.out.println("6. Exit simulation");
            System.out.print("Choose an action: ");
            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    System.out.println("Select a station to rent a bike (1-" + nbOfStations + "):");
                    int stationIndexToRent = scanner.nextInt();
                    if (stationIndexToRent < 1 || stationIndexToRent > nbOfStations) {
                        System.out.println("Invalid station index.");
                        break;
                    }
                    Station stationToRent = controlCenter.getStations().get(stationIndexToRent - 1);

                    List<Bike> availableBikes = new ArrayList<>();
                    for (Bike bike : stationToRent.getBikes()) {
                        if (bike != null && bike.getState() instanceof InService) {
                            availableBikes.add(bike);
                        }
                    }

                    if (availableBikes.isEmpty()) {
                        System.out.println("No available bikes to rent at this station.");
                        break;
                    }

                    System.out.println("Available bikes at " + stationToRent.getName() + ":");
                    for (Bike b : availableBikes) {
                        System.out.println("  Bike ID: " + b.getId() + ", Rentals: " + b.getRentalCount());
                    }

                    System.out.print("Enter Bike ID to rent: ");
                    int bikeIdToRent = scanner.nextInt();
                    Bike bikeToRent = availableBikes.stream()
                                .filter(b -> b.getId() == bikeIdToRent)
                                .findFirst()
                                .orElse(null);
                    if (bikeToRent != null) {
                        try {
                            bikeToRent.getState().Rent(bikeToRent);
                            bikeToRent.setState(new Rented());
                            stationToRent.TakeBike(bikeToRent);
                            controlCenter.addBikeToRent(bikeToRent);
                            System.out.println("You have rented Bike ID: " + bikeIdToRent);
                        } catch (BikeNotRentableException e) {
                            System.out.println("Could not rent the bike: " + e.getMessage());
                        } catch (BikeNotRemovableException | BikeNotRepairableException e) {
                            System.out.println("Error during bike removal: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Bike not found or not available for rent.");
                    }
                    printStationsAndRentedBikes(controlCenter);
                    break;

                case 2:
                    System.out.println("Select a station to return a bike (1-" + nbOfStations + "):");
                    int stationIndexToReturn = scanner.nextInt();
                    if (stationIndexToReturn < 1 || stationIndexToReturn > nbOfStations) {
                        System.out.println("Invalid station index.");
                        break;
                    }
                    Station stationToReturn = controlCenter.getStations().get(stationIndexToReturn - 1);

                    System.out.print("Enter Bike ID to return: ");
                    int bikeIdToReturn = scanner.nextInt();

                    Bike returningBike = null;
                    for (Bike rb : controlCenter.getBikesRented()) {
                        if (rb.getId() == bikeIdToReturn && rb.getState() instanceof Rented) {
                            returningBike = rb;
                            break;
                        }
                    }

                    if (returningBike == null) {
                        System.out.println("Bike not found among rented bikes.");
                        break;
                    }

                    int emptySlot = stationToReturn.findEmptySlot();
                    if (emptySlot == -1) {
                        System.out.println("No empty slot available to return the bike.");
                        break;
                    }
                    try {
                        returningBike.getState().Deposit(returningBike, stationToReturn, emptySlot);
                        controlCenter.getBikesRented().remove(returningBike);
                        System.out.println("You have returned Bike ID: " + bikeIdToReturn);
                    } catch (BikeNotReturnableException e) {
                        System.out.println("Could not return the bike: " + e.getMessage());
                    }

                    printStationsAndRentedBikes(controlCenter);
                    break;

                case 3:
                    controlCenter.fix();
                    System.out.println("Attempted to repair bikes.");
                    printStationsAndRentedBikes(controlCenter);
                    break;

                case 4:
                    controlCenter.distribute();
                    System.out.println("Bikes redistributed.");
                    printStationsAndRentedBikes(controlCenter);
                    break;

                case 5:
                    printStationsAndRentedBikes(controlCenter);
                    break;

                case 6:
                    System.out.println("Exiting simulation.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * The main method to start the bike-sharing simulation.
     * Users can choose between random, interactive, or round-robin simulation modes.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Mode: ");
        System.out.println("1. Random Simulation");
        System.out.println("2. Interactive Simulation");
        System.out.println("3. Round Robin Simulation");
        System.out.print("Enter choice (1, 2, or 3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                runRandomSimulation();
                break;
            case 2:
                runInteractiveSimulation();
                break;
            case 3:
                runRoundRobinSimulation();
                break;
            default:
                System.out.println("Invalid choice. Exiting.");
                break;
        }

        scanner.close();
    }
}

