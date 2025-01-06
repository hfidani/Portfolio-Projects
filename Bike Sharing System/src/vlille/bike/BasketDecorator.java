package vlille.bike;

public class BasketDecorator extends BikeDecorator {

    public BasketDecorator(Bike bike) {
        super(bike);
    }

    @Override
    public String decorate() {
        return decoratedBike.decorate() + " with a basket.";
    }

    // Method to add a basket, specific to this decorator
    public String addBasket() {
        return "Basket added to the bike.";
    }


    @Override
    public String Accessory(Bike bike) {
        return super.Accessory(bike) + " with a basket.";
    }

}

