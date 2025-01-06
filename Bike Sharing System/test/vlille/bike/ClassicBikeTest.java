package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ClassicBikeTest extends BikeTest {

	@Override
	protected Bike createBike() {
		return new ClassicBike(1,inServiceState);
	}
}
