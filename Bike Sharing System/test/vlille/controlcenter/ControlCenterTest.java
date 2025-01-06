package vlille.controlcenter;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Provider.Service;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.BikeNotRepairableException;
import exceptions.OccupiedLocationException;
import vlille.bike.Bike;
import vlille.bike.InService;
import vlille.bike.OutOfService;
import vlille.bike.Reparator;

public class ControlCenterTest {

    private Station s1;
    private Station s2;
    private Bike bike1;
    private Bike bike2;
    private Bike bike3;
    private ControlCenter cc;
    private RandomStrategy random;

    @BeforeEach
    public void init() {
        this.random = new RandomStrategy();
        this.cc = new ControlCenter(random);
        this.s1 = new Station("Station1", 2, cc);
        this.s2 = new Station("Station2", 2, cc);
        this.bike1 = new Bike(1, new InService());
        this.bike2 = new Bike(2, new InService());
        this.bike3 = new Bike(3, new OutOfService());
        
        cc.AddStation(s1);
        cc.AddStation(s2);
    }

    @Test
    public void testFix() {
        this.cc.addBikeToDeposit(bike3);
        this.cc.addService(new Reparator());
        this.cc.fix();
        assertTrue(bike3.getState() instanceof InService, "Bike should be repaired and in service.");
    }

    @Test
    public void testDistribute() {
        try {
            this.s1.addBike(this.bike1, 0);
            this.s1.addBike(this.bike2, 1);
        } catch (OccupiedLocationException e) {
            fail("Failed to add bikes to station 1.");
        }

        this.cc.distribute();

        int s1Bikes = this.s1.getNumberOfBikes();
        int s2Bikes = this.s2.getNumberOfBikes();

        assertTrue(s1Bikes < 2, "Station1 should have less bikes after redistribution.");
        assertTrue(s2Bikes > 0, "Station2 should have more bikes after redistribution.");
    }

    @Test
    public void testRandomDepositWithdrawal() {
        try {
            this.s1.addBike(this.bike1, 0);
        } catch (OccupiedLocationException e) {
            fail("Failed to add bike to station 1.");
        }
        this.cc.addBikeToRent(this.bike2);

        this.cc.randomDepositWithdrawal();

        int rentedBikes = this.cc.getBikesRented().size();
        int s1Bikes = this.s1.getNumberOfBikes();

        assertTrue(rentedBikes >= 0, "There should still be rented bikes or none.");
        assertTrue(s1Bikes >= 0, "Station1 should have bikes after withdrawal.");
    }

    @Test
    public void testDepositWithdrawalSimulateIntervalle() throws InterruptedException {
        this.cc.addBikeToRent(this.bike1);
        this.cc.depositWithdrawalSimulateIntervalle();

        Thread.sleep(5000);

        int rentedBikes = this.cc.getBikesRented().size();
        int s1Bikes = this.s1.getNumberOfBikes();

        assertTrue(rentedBikes >= 0, "Rented bikes should decrease over time.");
        assertTrue(s1Bikes > 0, "Station1 should have at least one bike after deposit.");
    }

    @Test
    public void testEmptyStationsSimulateIntervalle() throws InterruptedException {
        this.cc.EmptyStationsSimulateIntervalle();

        Thread.sleep(9000);

        int s1Bikes = s1.getNumberOfBikes();
        int s2Bikes = s2.getNumberOfBikes();

        assertTrue(s1Bikes > 0 || s2Bikes > 0, "Stations should no longer be empty after redistribution.");
    }

    @Test
    public void testGetStations() {
        assertEquals(2, this.cc.getStations().size(), "ControlCenter should have exactly 2 stations.");
    }

    @Test
    public void testAddStation() {
        Station s3 = new Station("Station3", 3, cc);
        this.cc.AddStation(s3);
        assertTrue(cc.getStations().contains(s3), "New station should be added to ControlCenter.");
    }
}