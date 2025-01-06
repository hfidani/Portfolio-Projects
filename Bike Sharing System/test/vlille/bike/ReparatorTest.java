package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.BikeNotRepairableException;

public class ReparatorTest {
	
	@Test
	public void testControlServiceWhenItsOutOfService() throws BikeNotRepairableException{
		Reparator mec = new Reparator();
		Bike bike = new Bike(22,new OutOfService());
		mec.ControlService(bike);
		assertTrue(bike.getState() instanceof InService);	
	}
	
	@Test
	public void testControlServiceWhenItsInService() throws BikeNotRepairableException{
		Reparator mec = new Reparator();
		Bike bike = new Bike(22,new InService());
		assertThrows(BikeNotRepairableException.class,()->{mec.ControlService(bike);});
	}
	
	@Test
	public void testControlServiceWhenItsStolen() throws BikeNotRepairableException{
		Reparator mec = new Reparator();
		Bike bike = new Bike(22,new Stolen());
		assertThrows(BikeNotRepairableException.class,()->{mec.ControlService(bike);});
	}
	@Test
	public void testControlServiceWhenItsUnderReparation() throws BikeNotRepairableException{
		Reparator mec = new Reparator();
		Bike bike = new Bike(22,new UnderReparation());
		assertThrows(BikeNotRepairableException.class,()->{mec.ControlService(bike);});
	}
	@Test
	public void testControlServiceWhenItsRented() throws BikeNotRepairableException{
		Reparator mec = new Reparator();
		Bike bike = new Bike(22,new Rented());
		assertThrows(BikeNotRepairableException.class,()->{mec.ControlService(bike);});
	}

}
