package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LuggageDecoratorTest extends BikeDecoratorTest{



	@Test
	public void testDecorate() {
		LuggageDecorator dBike = new LuggageDecorator(this.bike);
		assertEquals("Bike ID: 1 with a luggage rack." ,dBike.decorate());
	}

	@Test
	public void testAccessory() {
		LuggageDecorator dBike = new LuggageDecorator(this.bike);
		assertEquals("Bike with ID : 1 is being decorated with a luggage rack" ,dBike.Accessory(dBike));
	}


	@Test
	public void testAddLuggageRack() {
		LuggageDecorator dBike = new LuggageDecorator(this.bike);
		assertEquals("Luggage rack added to the bike.", dBike.addLuggageRack());
	}

}
