package exceptions;


public class BikeNotRentableException extends Exception {
    public BikeNotRentableException(String message) {
        super(message);
    }
}