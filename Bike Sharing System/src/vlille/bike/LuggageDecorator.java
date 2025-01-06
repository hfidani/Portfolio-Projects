package vlille.bike;

public class LuggageDecorator extends BikeDecorator {

    public LuggageDecorator(Bike bike) {
        super(bike);
    }

    @Override
    public String decorate() {
        return decoratedBike.decorate() + " with a luggage rack.";
    }

    // Method to add a luggage rack
    public String addLuggageRack() {
        return "Luggage rack added to the bike.";
    }


    @Override
    public String Accessory(Bike bike) {
        return super.Accessory(bike) + " with a luggage rack";
    }

}
