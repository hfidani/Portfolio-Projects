package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BikeTest {

	protected Bike bike;
	protected State inServiceState;
	protected State outServiceState;
	protected Bike createBike() {
		return new Bike(1,inServiceState);
	}
	@BeforeEach
	public void init() {
		this.inServiceState = new InService();
		this.outServiceState = new OutOfService();
		this.bike = this.createBike();
	}

	@Test
	public void testSetEtGetState() {
		bike.setState(outServiceState);
		assertEquals(outServiceState , bike.getState());
	}

	@Test
	public void testGetetSetId() {
		bike.setId(34);
		assertEquals(34,bike.getId());
	}

	@Test
	public void testGetInitialRentalCount() {
		int initialRentalCount = bike.getRentalCount();
		assertEquals(0,initialRentalCount);
	}
	@Test
	public void testGetRentalCountWhenWeUpdateIt() {
		int initialRentalCount = bike.getRentalCount();
		bike.updateRentalCount();
		assertEquals(initialRentalCount+1,bike.getRentalCount());
	}

	@Test
	public void testGetRentalMax() {
		int rentalMax = bike.getRentalMax();
		assertEquals(10,rentalMax);
		
	}

	@Test
	public void testDecorateWhithNothing() {
		assertEquals("Bike ID: 1", bike.decorate());
		
	}


}
