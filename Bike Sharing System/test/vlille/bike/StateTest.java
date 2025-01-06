package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.*;

public abstract class StateTest {

	protected State state;
	
	protected abstract State createState();
	
	@BeforeEach
	public void init() {
		this.state = this.createState();
	}
	
	@Test
	public abstract void testRepair() throws BikeNotRepairableException;

	@Test
	public abstract void testRent() throws BikeNotRentableException;

	@Test
	public abstract void testDeposit() throws BikeNotReturnableException ;

	@Test
	public abstract void testTake() throws BikeNotRemovableException,OccupiedLocationException;

	@Test
	public void testIsRented() {
		assertFalse(state.isRented());
	}

}
