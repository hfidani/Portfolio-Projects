package vlille.bike;

public abstract class BikeDecorator extends Bike {
    protected Bike decoratedBike;

    /**
     * Constructs a BikeDecorator with a specified bike to decorate.
     *
     * @param bike The bike to decorate.
     */
    public BikeDecorator(Bike bike) {
        super(bike.getId(), bike.getState());
        this.decoratedBike = bike;
    }

    /**
     * Abstract method for decoration logic. Subclasses must implement this method.
     *
     * @return A string describing the decorated bike.
     */
    public abstract String decorate();

   /**
     * Adds a general accessory to the bike. This method can be extended by subclasses
     * to add specific types of accessories.
     *
     * @param bike The bike to add an accessory to.
     * @return A string indicating the accessory added.
     */
    public String Accessory(Bike bike) {
        return "Bike with ID : " + bike.getId() + " is being decorated";
    }


}


