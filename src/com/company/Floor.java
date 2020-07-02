package com.company;

import java.util.ArrayList;
import java.util.List;

import static com.company.Utility.rangeRandom;


public class Floor {
    private static final int GROUND_FLOOR = 1;
    private static final int MAX_FLOOR = 20;
    private static final int MIN_FLOOR = 5;
    private static final int FLOORS_AMOUNT = rangeRandom(MIN_FLOOR,MAX_FLOOR);
    private int floorQuantity;
    private int passengersQuantityOnTheFloor;
    private boolean buttonUp;
    private boolean buttonDown;
    private List<Passenger> passengersOnTheFloor;

    Floor(int floorQuantity) {
        this.floorQuantity = floorQuantity;
        this.passengersQuantityOnTheFloor = rangeRandom(Passenger.getMinPassengers(),Passenger.getMaxPassengers());
        this.buttonUp = false;
        this.buttonDown = false;
        generatePassangers();
        buttonPress();
    }

    private void generatePassangers() {
        this.passengersOnTheFloor = new ArrayList<>();
        for (int i = 0; i < passengersQuantityOnTheFloor; i++) {
            passengersOnTheFloor.add(new Passenger(floorQuantity));
        }
    }

    public void countPassengerOnTheFloor() {
        this.passengersQuantityOnTheFloor = passengersOnTheFloor.size();
    }
    
    public void buttonPress(){
        List<Passenger> passengersGoingUP = passengersOnTheFloor;
        List<Passenger> passengersGoingDown = passengersOnTheFloor;
        Passenger passenger = new Passenger();
        for (int i = 0; i < 1; i++) {
            if (passenger.getRequiredFloor() - passenger.getCurrentFloor() > 0){
                passengersGoingUP.add(passenger);
            }
        }
        for (int i = 0; i < 1; i++) {
            if (passenger.getRequiredFloor() - passenger.getCurrentFloor() < 0){
                passengersGoingDown.add(passenger);
            }
        }

    }

    public int getFloorQuantity() {
        return floorQuantity;
    }

    public List<Passenger> getPassengersOnTheFloor() {
        return passengersOnTheFloor;
    }

    public void setPassengersOnTheFloor(List<Passenger> passengersOnTheFloor) {
        this.passengersOnTheFloor = passengersOnTheFloor;
    }

    public boolean isButtonUp() {
        return buttonUp;
    }

    public boolean isButtonDown() {
        return buttonDown;
    }

    public int getPassengersQuantityOnTheFloor() {
        return passengersQuantityOnTheFloor;
    }

    public static int getMaxFloor() {
        return MAX_FLOOR;
    }

    public static int getMinFloor() {
        return MIN_FLOOR;
    }

    public static int getFloorsAmount() {
        return FLOORS_AMOUNT;
    }

    public static int getGroundFloor() {
        return GROUND_FLOOR;
    }
}
