package vlille.bike;

public class MockBikeDecorator extends BikeDecorator {
	
	public MockBikeDecorator(Bike bike) {
		super(bike);
	}
	@Override
	public  String decorate() {
		return decoratedBike.decorate();
	}
}
