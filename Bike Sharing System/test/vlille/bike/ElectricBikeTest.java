package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ElectricBikeTest extends BikeTest {

	@Override
	protected Bike createBike() {
		return new ElectricBike(1,inServiceState);
	}

}
