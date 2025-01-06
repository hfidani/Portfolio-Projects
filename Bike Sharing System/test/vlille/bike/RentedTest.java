package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.BikeNotRemovableException;
import exceptions.BikeNotRentableException;
import exceptions.BikeNotRepairableException;
import exceptions.BikeNotReturnableException;
import exceptions.OccupiedLocationException;
import vlille.controlcenter.*;

public class RentedTest extends StateTest{

	protected State createState() {
		return new Rented();
	}
	
	@Test
	public void testRepair() throws BikeNotRepairableException{
		Rented state = new Rented(); //this.createState();
		Bike bike = new Bike(0, state);
		Service reparator  = new Reparator();
		assertThrows(BikeNotRepairableException.class,()->{
			state.Repair(bike,reparator);
		});
	}

	@Test
	public void testRent() throws BikeNotRentableException{
		Rented state = new Rented(); //this.createState();
		Bike bike = new Bike(0, state);
		assertThrows(BikeNotRentableException.class,()->{
			state.Rent(bike);
		});

	}

	@Test
	public void testDeposit() throws BikeNotReturnableException{
		Rented state = new Rented(); //this.createState();
		Bike bike = new Bike(0, state);

		Station station = new Station("blue", 2, new Observer() {
			@Override
			public void update(Station station) {
			}
		});
		state.Deposit(bike, station, 0);
		assertEquals(bike,station.getBikes().get(0));
		assertTrue(bike.getState() instanceof InService);
	}
	
	@Test
	public void testDepositWhenStationHasNotPlace() throws BikeNotReturnableException{
		Rented state = new Rented(); //this.createState();
		Bike bike = new Bike(0, state);
		Station station = new Station("blue" , 0, null);
		assertThrows(BikeNotReturnableException.class,()->{
			state.Deposit(bike, station, 0);
		});
	}

	@Test
	public void testTake() throws BikeNotRemovableException,OccupiedLocationException{
		Rented state = new Rented(); //this.createState();
		Bike bike = new Bike(0, state);

        Station station = new Station("blue", 2, new Observer() {
			@Override
			public void update(Station station) {
			}
		});

		station.addBike(bike, 0);
		assertThrows(BikeNotRemovableException.class,()->{
			state.Take(bike, station);
		});
	}

	@Test
	public void testIsRented() {
		Rented state = new Rented(); //this.createState();
		assertTrue(state.isRented());
	}

}
