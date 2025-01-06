package vlille.controlcenter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.BikeNotRemovableException;
import exceptions.BikeNotRepairableException;
import exceptions.OccupiedLocationException;
import vlille.bike.Bike;
import vlille.bike.InService;

public class StationTest {

	private Station station;
	private ControlCenter cc;
	private RandomStrategy random;
	private Bike bike1;
	private Bike bike2;
	private Bike bike3;
	private Bike bike4;
	private Bike bike5;
	
	
	@BeforeEach
	public void init() {
		this.random = new RandomStrategy();
		this.cc = new ControlCenter(random);
		this.station = new Station("vlille",10,cc);
		this.bike1 = new Bike(1,new InService());
		this.bike2 = new Bike(2,new InService());
		this.bike3 = new Bike(3,new InService());
		this.bike4 = new Bike(4,new InService());
		this.bike5 = new Bike(5,new InService());
	}

	@Test
	public void testGetName() {
		assertEquals(this.station.getName(),"vlille");
	}

	@Test
	public void testGetCapacity() {
		assertEquals(this.station.getCapacity(),10);
	}

	@Test
	public void testGetNumberOfBikes() {
		assertEquals(this.station.getNumberOfBikes(),0);
	}

	@Test
	public void testTakeBike() throws BikeNotRemovableException, BikeNotRepairableException {
		try {
			this.station.addBike(bike1, 0);
		}
		catch(OccupiedLocationException e) {}
		assertEquals(this.station.getNumberOfBikes(),1);
		this.station.TakeBike(bike1);
		assertEquals(this.station.getNumberOfBikes(),0);
		
	}
	@Test
	public void testTakeBikeIfWeTakeANotExistBike() throws BikeNotRemovableException, BikeNotRepairableException {
		try {
			this.station.addBike(bike1, 0);
		}
		catch(OccupiedLocationException e) {}
		assertEquals(this.station.getNumberOfBikes(),1);
		assertThrows(BikeNotRemovableException.class,()-> {this.station.TakeBike(bike2);});
		assertEquals(this.station.getNumberOfBikes(),1);
		
	}
	@Test
	public void testTakeBikeIfWeHaveMaxRentalOfBike() throws BikeNotRemovableException, BikeNotRepairableException {
		for(int i=0;i<10;i++) {
			this.bike1.updateRentalCount();
		}
		try {
			this.station.addBike(bike1, 0);
		}
		catch(OccupiedLocationException e) {}
		assertThrows(BikeNotRemovableException.class,()-> {this.station.TakeBike(bike1);});
		
	}

	@Test
	public void testIsFull() {
		try {
			this.station.addBike(bike1, 0);
			this.station.addBike(bike1, 1);
			this.station.addBike(bike2, 2);
			this.station.addBike(bike2, 3);
			this.station.addBike(bike3, 4);
			this.station.addBike(bike3, 5);
			this.station.addBike(bike4, 6);
			this.station.addBike(bike4, 7);
			this.station.addBike(bike5, 8);
			this.station.addBike(bike5, 9);
		}
		catch(OccupiedLocationException e) {}
		assertTrue(this.station.IsFull());
	}

	public void testIsNotFull() {
		try {
			this.station.addBike(bike1, 0);
			this.station.addBike(bike2, 1);
		}
		catch(OccupiedLocationException e) {}
		assertFalse(this.station.IsFull());
	}

	@Test
	public void testAddBike() throws OccupiedLocationException{
		assertEquals(this.station.getNumberOfBikes(),0);
		this.station.addBike(bike1, 0);
		assertEquals(this.station.getNumberOfBikes(),1);
		
	}
	@Test
	public void testAddBikeWithOccupationException() throws OccupiedLocationException{
		this.station.addBike(bike1, 0);
		assertThrows(OccupiedLocationException.class,()-> {this.station.addBike(bike2, 0);});	
	}

	@Test
	public void testFindEmptySlot() {
		assertEquals(0,this.station.findEmptySlot());
	}

	@Test
	public void testSelectBikeForRemoval() {
		try {
			this.station.addBike(bike1, 0);
			this.station.addBike(bike2, 1);
		}
		catch(OccupiedLocationException e) {}
		assertEquals(bike1,this.station.selectBikeForRemoval());
	}
	
	public void testSelectBikeForRemovalIsNULL() {

		assertEquals(null,this.station.selectBikeForRemoval());
	}

	@Test
	void testGetBikes() {
		assertEquals(this.station.getBikes().size(),10);
	}

}
