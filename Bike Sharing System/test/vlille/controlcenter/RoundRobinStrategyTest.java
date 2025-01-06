package vlille.controlcenter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.OccupiedLocationException;
import vlille.bike.Bike;
import vlille.bike.InService;

public class RoundRobinStrategyTest {

    private ControlCenter controlCenter;
    private RedistributionStrategy roundRobin;

    @BeforeEach
    public void setUp() {
        roundRobin = new RoundRobinStrategy();
        controlCenter = new ControlCenter(roundRobin);
    }

    @Test
    public void testRedistribute_AllStationsInitiallyEmpty() {
        Bike bike1 = new Bike(1, new InService());
        Bike bike2 = new Bike(2, new InService());
        Bike bike3 = new Bike(3, new InService());
        Bike bike4 = new Bike(4, new InService());
        Bike bike5 = new Bike(5, new InService());
        Station station1 = new Station("Station1", 5, controlCenter);
        Station station2 = new Station("Station2", 3, controlCenter);
        Station station3 = new Station("Station3", 2, controlCenter);

        try {
            station1.addBike(bike1, 0);
            station1.addBike(bike2, 1);
            station1.addBike(bike3, 2);
            station1.addBike(bike4, 3);
            station1.addBike(bike5, 4);
        } catch (OccupiedLocationException e) {
            fail("Failed to add bike: " + e.getMessage());
        }

        assertEquals(5, station1.getNumberOfBikes());
        assertEquals(0, station2.getNumberOfBikes());
        assertEquals(0, station3.getNumberOfBikes());

        controlCenter.AddStation(station1);
        controlCenter.AddStation(station2);
        controlCenter.AddStation(station3);

        roundRobin.redistribute(controlCenter);

        assertEquals(0, station1.getNumberOfBikes());
        assertEquals(3, station2.getNumberOfBikes());
        assertEquals(2, station3.getNumberOfBikes());
    }

    @Test
    public void testRedistribute_StationsPartiallyFilled() {
        Bike bike1 = new Bike(1, new InService());
        Bike bike2 = new Bike(2, new InService());
        Bike bike3 = new Bike(3, new InService());
        Station station1 = new Station("Station1", 5, controlCenter);
        Station station2 = new Station("Station2", 3, controlCenter);
        Station station3 = new Station("Station3", 2, controlCenter);

        try {
            station1.addBike(bike1, 0);
            station1.addBike(bike2, 1);
            station1.addBike(bike3, 2);

            station2.addBike(new Bike(4, new InService()), 0);
        } catch (OccupiedLocationException e) {
            fail("Failed to add bike: " + e.getMessage());
        }

        assertEquals(3, station1.getNumberOfBikes());
        assertEquals(1, station2.getNumberOfBikes());
        assertEquals(0, station3.getNumberOfBikes());

        controlCenter.AddStation(station1);
        controlCenter.AddStation(station2);
        controlCenter.AddStation(station3);

        roundRobin.redistribute(controlCenter);

        assertEquals(2, station1.getNumberOfBikes());
        assertEquals(1, station2.getNumberOfBikes());
        assertEquals(1, station3.getNumberOfBikes());
    }

    @Test
    public void testRedistribute_NoBikesAvailable() {
        Station station1 = new Station("Station1", 5, controlCenter);
        Station station2 = new Station("Station2", 3, controlCenter);
        Station station3 = new Station("Station3", 2, controlCenter);

        controlCenter.AddStation(station1);
        controlCenter.AddStation(station2);
        controlCenter.AddStation(station3);

        roundRobin.redistribute(controlCenter);

        assertEquals(0, station1.getNumberOfBikes());
        assertEquals(0, station2.getNumberOfBikes());
        assertEquals(0, station3.getNumberOfBikes());
    }

    @Test
    public void testRedistribute_StationCapacityExceeded() {
        Bike bike1 = new Bike(1, new InService());
        Bike bike2 = new Bike(2, new InService());
        Bike bike3 = new Bike(3, new InService());
        Bike bike4 = new Bike(4, new InService());
        Station station1 = new Station("Station1", 2, controlCenter);
        Station station2 = new Station("Station2", 3, controlCenter);
        Station station3 = new Station("Station3", 2, controlCenter);

        try {
            station1.addBike(bike1, 0);
            station1.addBike(bike2, 1);
            station1.addBike(bike3, 1);
        } catch (OccupiedLocationException e) {
            System.err.println("Caught exception as expected: " + e.getMessage());
        }

        controlCenter.AddStation(station1);
        controlCenter.AddStation(station2);
        controlCenter.AddStation(station3);

        roundRobin.redistribute(controlCenter);

        assertTrue(station1.getNumberOfBikes() <= station1.getCapacity());
        assertTrue(station2.getNumberOfBikes() <= station2.getCapacity());
        assertTrue(station3.getNumberOfBikes() <= station3.getCapacity());
    }
}
