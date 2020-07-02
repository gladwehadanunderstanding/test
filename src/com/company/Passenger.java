package com.company;

import java.util.Random;

public class Passenger {
    private static final int MIN_PASSENGERS = 0;
    private static final int MAX_PASSENGERS = 10;
    private static final int MAX_PASSENGERS_IN_LIFT = 5;
    private int currentFloor;
    private int requiredFloor;

    Passenger(int currentFloor) {
        this.currentFloor = currentFloor;
        setRequiredFloor();
    }
    Passenger(){
        setRequiredFloor();
    }

    public void setRequiredFloor() {
        Random random = new Random();
        requiredFloor =random.nextInt(Floor.getFloorsAmount())+1;
        while(requiredFloor ==currentFloor)
            requiredFloor =random.nextInt(Floor.getFloorsAmount())+1;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getRequiredFloor() {
        return requiredFloor;
    }

    public static int getMinPassengers() {
        return MIN_PASSENGERS;
    }

    public static int getMaxPassengers() {
        return MAX_PASSENGERS;
    }

    public static int getMaxPassengersInLift() {
        return MAX_PASSENGERS_IN_LIFT;
    }
}