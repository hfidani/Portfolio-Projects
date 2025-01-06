package vlille.bike;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FoldableBikeTest extends BikeTest{

	@Override
	protected Bike createBike() {
		return new FoldableBike(1,inServiceState);
	}
	
}
