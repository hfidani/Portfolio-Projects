package vlille.bike;
 

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.BikeNotRemovableException;
import exceptions.BikeNotRentableException;
import exceptions.BikeNotRepairableException;
import exceptions.BikeNotReturnableException;
import exceptions.OccupiedLocationException;
import vlille.controlcenter.* ; 

public class InServiceTest extends StateTest{

	protected State createState() {
		return new InService();
	}
	
	@Test
	public void testRepair() throws BikeNotRepairableException{
		InService state = new InService(); //this.createState();
		Bike bike = new Bike(0, state);
		Service reparator  = new Reparator();
		assertThrows(BikeNotRepairableException.class,()->{
			state.Repair(bike,reparator);
		});
	}

	@Test
	public void testRent() throws BikeNotRentableException{
		InService state = new InService(); //this.createState();
		Bike bike = new Bike(0, state);
		int numberOfRent  = bike.getRentalCount() ;
		state.Rent(bike);
		assertEquals(bike.getRentalCount(),numberOfRent + 1);

	}
	
	@Test
	public void testRentWhenWearrivedAtTheRentalCountMax() throws BikeNotRentableException{
		InService state = new InService(); //this.createState();
		Bike bike = new Bike(0, state);
		for (int i= 0;i<10;i++) {
			state.Rent(bike);
		}
		assertThrows(BikeNotRentableException.class,() -> {state.Rent(bike);});
		assertTrue(bike.getState() instanceof OutOfService);
	}

	@Test
	public void testDeposit() throws BikeNotReturnableException {
		InService state = new InService(); // this.createState();
		Bike bike = new Bike(0, state);

		// Provide a mock Observer to the Station
		Station station = new Station("blue", 4, new Observer() {
			@Override
			public void update(Station station) {
				// No-op for test
			}
		});

		// Deposit the bike in the station
		state.Deposit(bike, station, 0);

		// Verify the bike was added successfully
		assertEquals(bike, station.getBikes().get(0));
	}


	@Test
	public void testDepositWhenStationIsFull() throws BikeNotReturnableException {
		InService state = new InService(); // this.createState();
		Bike bike = new Bike(0, state);
		Bike bikee = new Bike(1, state);

		// Provide a mock Observer to the Station
		Station station = new Station("blue", 1, new Observer() {
			@Override
			public void update(Station station) {
				// No-op for test
			}
		});

		// Add a bike to the station, filling it
		state.Deposit(bike, station, 0);

		// Attempt to deposit another bike, which should throw an exception
		assertThrows(BikeNotReturnableException.class, () -> {
			state.Deposit(bikee, station, 0);
		});
	}

	

	@Test
	public void testDepositWhenStationIsOccupied() throws BikeNotReturnableException {
		InService state = new InService(); // this.createState();
		Bike bike = new Bike(0, state);
		Bike bikee = new Bike(1, state);
		
		// Provide a mock Observer to the Station
		Station station = new Station("blue", 1, new Observer() {
			@Override
			public void update(Station station) {
				// Simple implementation for the test; do nothing
			}
		});

		// Add the first bike to the station
		state.Deposit(bike, station, 0);
		
		// Try adding a second bike to the same spot, which should fail
		assertThrows(BikeNotReturnableException.class, () -> {
			state.Deposit(bikee, station, 0);
		});
	}


	@Test
	public void testTake() throws BikeNotRemovableException,OccupiedLocationException{
		InService state = new InService(); //this.createState();
		Bike bike = new Bike(0, state);

		Station station = new Station("blue", 2, new Observer() {
			@Override
			public void update(Station station) {
			}
		});
		
		station.addBike(bike, 0);
		assertEquals(bike,station.getBikes().get(0));
		state.Take(bike, station);
		assertEquals(null,station.getBikes().get(0));
	}

}
