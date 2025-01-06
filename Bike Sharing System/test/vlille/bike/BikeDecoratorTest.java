package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class BikeDecoratorTest extends BikeTest{

	MockBikeDecorator mock;

	@BeforeEach
	public void Before() {
		this.mock = new MockBikeDecorator(bike);
	}
	@Test
	public void testDecorate() {
		assertEquals("Bike ID : 1" , mock.decorate());	
	}            

	@Test
	public void testAccessory() {
		assertEquals("Bike with ID : 1 is being decorated" , mock.Accessory(bike));
	}

}
