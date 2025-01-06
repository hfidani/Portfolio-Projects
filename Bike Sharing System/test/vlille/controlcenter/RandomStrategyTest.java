package vlille.controlcenter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.OccupiedLocationException;
import vlille.bike.Bike;
import vlille.bike.InService;

public class RandomStrategyTest {

	@Test
	void testRedistribute() {
		RedistributionStrategy random = new RandomStrategy();
		ControlCenter cc = new ControlCenter(random);
		Bike bike1 = new Bike(1,new InService());
		Bike bike2 = new Bike(2,new InService());
		Bike bike3 = new Bike(3,new InService());
		Bike bike4 = new Bike(4,new InService());
		Bike bike5 = new Bike(5,new InService());
		Station s = new Station("ici",5,cc);
		try{
			s.addBike(bike1, 0);
		}
		catch(OccupiedLocationException e) {}
		try{
			s.addBike(bike2, 1);
		}
		catch(OccupiedLocationException e) {}
		try{
			s.addBike(bike3, 2);
		}
		catch(OccupiedLocationException e) {}
		try{
			s.addBike(bike4, 3);
		}
		catch(OccupiedLocationException e) {}
		try{
			s.addBike(bike5, 4);
		}
		catch(OccupiedLocationException e) {}
		assertEquals(s.getBikes().size(),5);
		Station s1 = new Station("la",3,cc);
		Station s2 = new Station("bas",2,cc);
		cc.AddStation(s);
		cc.AddStation(s1);
		cc.AddStation(s2);
		random.redistribute(cc);
		assertEquals(s.getBikes().size(),3);
		assertTrue(s1.getBikes().size() != 0 || s2.getBikes().size() != 0 );
		
	}

}
