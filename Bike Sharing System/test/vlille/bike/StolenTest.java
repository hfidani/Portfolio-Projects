package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.BikeNotRemovableException;
import exceptions.BikeNotRentableException;
import exceptions.BikeNotRepairableException;
import exceptions.BikeNotReturnableException;
import exceptions.OccupiedLocationException;
import vlille.controlcenter.*;

public class StolenTest extends StateTest{

	protected State createState() {
		return new Stolen();
	}
	@Test
	public void testRepair() throws BikeNotRepairableException{
		Stolen state = new Stolen(); //this.createState();
		Bike bike = new Bike(0, state);
		Service reparator  = new Reparator();
		assertThrows(BikeNotRepairableException.class,()->{
			state.Repair(bike,reparator);
		});
	}

	@Test
	public void testRent() throws BikeNotRentableException{
		Stolen state = new Stolen(); //this.createState();
		Bike bike = new Bike(0, state);
		assertThrows(BikeNotRentableException.class,()->{
			state.Rent(bike);
		});

	}

	@Test
	public void testDeposit() throws BikeNotReturnableException{
		Stolen state = new Stolen(); //this.createState();
		Bike bike = new Bike(0, state);
		Station station = new Station("blue" , 2, null);
		assertThrows(BikeNotReturnableException.class,() ->{
			state.Deposit(bike, station, 0);
		});
	}

	@Test
	public void testTake() throws BikeNotRemovableException,OccupiedLocationException{
		Stolen state = new Stolen(); //this.createState();
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

}
