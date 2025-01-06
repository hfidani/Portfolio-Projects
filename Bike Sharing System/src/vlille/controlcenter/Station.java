package vlille.controlcenter ;

import vlille.bike.Bike;
import java.util.ArrayList;
import java.util.List;
import exceptions.*;

/**
 * The Station class represents a bike-sharing station, managing the bikes it contains
 * and allowing operations such as adding, removing, and querying bikes.
 */

public class Station implements Subject {
   private List<Bike> bikes; 
   private String name;
   private int capacity;
   private Observer observer;

   /**
    * Constructs a Station with a specified name and capacity.
    *
    * @param name     The name of the station.
    * @param capacity The maximum capacity of the station.
    * @param observer The observer to be notified of changes.
    */
   public Station(String name, int capacity ,Observer observer) {
      this.name = name;
      this.capacity = capacity;
      this.bikes = new ArrayList<>(capacity);
      this.observer = observer;
      for (int i = 0; i < capacity; i++) {
         bikes.add(null);
      }
   }

   /**
    * Notifies the obsever for a change.
    */
   @Override
   public void notifyObserver() {
         observer.update(this);
   }

   /**
    * Gets the name of the station.
    * 
    * @return The name of the station.
    */
   public String getName() {
      return this.name;
   }

   /**
    * Gets the maximum capacity of the station.
    * 
    * @return The maximum capacity of the station.
    */
   public int getCapacity() {
      return this.capacity;
   }

   /**
    * Gets the number of bikes currently available in the station.
    * 
    * @return The number of available bikes.
    */
   public int getNumberOfBikes() {
      int count = 0;
      for (Bike bike : bikes) {
         if (bike != null) count++;
      }
      return count;
   }

   /**
    * Removes a bike from the station.
    *
    * @param bike The bike to be removed.
    * @throws BikeNotRemovableException If the bike cannot be removed.
    * @throws BikeNotRepairableException If the bike is not repairable.
    */
   public void TakeBike(Bike bike) throws BikeNotRemovableException, BikeNotRepairableException {
      if (bikes.contains(bike)) {
          try {
              if (!bike.maxrental()) { 
                  bikes.remove(bike); 
                  bike.updateRentalCount(); 
                  notifyObserver(); 
              } else {
                  throw new BikeNotRemovableException("Impossible de retirer le vélo");
              }
          } catch (BikeNotRepairableException e) {
              
              throw e;
          }
      } else {
          throw new BikeNotRemovableException("Impossible de retirer le vélo");
      }
   }

   /**
    * Checks if the station is full.
    *
    * @return true if the station is full, false otherwise.
    */
   public Boolean IsFull() {
      return (this.getNumberOfBikes()) == (this.capacity);
   }

   /**
    * Adds a bike to the station at the specified location.
    *
    * @param bike  The bike to add.
    * @param space The location to add the bike.
    * @throws OccupiedLocationException Exception indicating that the location is already occupied.
    */
   public void addBike(Bike bike, int space) throws OccupiedLocationException {
      if (space < 0 || space >= this.capacity || this.bikes.get(space) != null ) {
         throw new OccupiedLocationException("La place est déjà occupée par un autre vélo");
      }
      
      this.bikes.set(space, bike);
      notifyObserver(); // Notify observers about the addition
   }

   /**
    * Finds the first empty slot in the station.
    *
    * @return the index of the first empty slot, or -1 if the station is full.
    */
   public int findEmptySlot() {
      for (int i = 0; i < bikes.size(); i++) {
         if (bikes.get(i) == null) {
            return i;
         }
      }
      return -1; // No empty slots available
   }

   /**
    * Selects a bike for removal.
    *
    * @return The bike to remove, or null if no bikes are available.
    */
   public Bike selectBikeForRemoval() {
      for (Bike bike : bikes) {
         if (bike != null) {
            return bike;
         }
      }
      return null; // No bikes available for removal
   }

   /**
    * Gets the list of bike's slot (null or not) in the station.
    * 
    * @return The list of bikes.
    */
   public List<Bike> getBikes() {
      return bikes;
   }

   /**
    * Checks if the station is empty.
    *
    * @return true if the station has no bikes, false otherwise.
    */

   public Boolean isEmpty() {
      return this.getNumberOfBikes() == 0 ;
   }

}
