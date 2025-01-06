package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.*;
import vlille.controlcenter.*;

public class OutOfServiceTest extends StateTest{


	protected State createState() {
		return new OutOfService();
	}
	@Test
	public void testRepair() throws BikeNotRepairableException{
		OutOfService state = new OutOfService(); //this.createState();
		Bike bike = new Bike(0, state);
		Service reparator  = new Reparator();
		state.Repair(bike,reparator);
		assertTrue(bike.getState() instanceof InService);
		
	}

	@Test
	public void testRent() throws BikeNotRentableException{
		OutOfService state = new OutOfService(); //this.createState();
		Bike bike = new Bike(0, state);
		assertThrows(BikeNotRentableException.class,()->{
			state.Rent(bike);
		});

	}

	@Test
	public void testDeposit() throws BikeNotReturnableException{
		OutOfService state = new OutOfService(); //this.createState();
		Bike bike = new Bike(0, state);
		Station station = new Station("blue" , 2, null);
		assertThrows(BikeNotReturnableException.class,() ->{
			state.Deposit(bike, station, 0);
		});
	}

	@Test
	public void testTake() throws BikeNotRemovableException{
		OutOfService state = new OutOfService(); //this.createState();
		Bike bike = new Bike(0, state);
		Station station = new Station("blue" , 2, null);
		assertThrows(BikeNotRemovableException.class,()->{
			state.Take(bike, station);
		});
	}

}
