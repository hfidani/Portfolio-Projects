package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasketDecoratorTest extends BikeDecoratorTest{
	
	
	@Test
	public void testDecorate() {
		BasketDecorator dBike = new BasketDecorator(this.bike);
		assertEquals("Bike ID: 1 with a basket." ,dBike.decorate());
	}

	@Test
	public void testAccessory() {
		BasketDecorator dBike = new BasketDecorator(this.bike);
		assertEquals("Bike with ID : 1 is being decorated with a basket." ,dBike.Accessory(dBike));	
	}

	@Test
	public void testAddBasket() {
		BasketDecorator dBike = new BasketDecorator(this.bike);
		assertEquals("Basket added to the bike.", dBike.addBasket());
	}

}
