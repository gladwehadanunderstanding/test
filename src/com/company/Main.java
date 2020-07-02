package com.company;

public class Main {
    public static void main(String[] args) {
        Building building = new Building(Floor.getFloorsAmount());
        Lift lift = new Lift(Floor.getGroundFloor(), Passenger.getMaxPassengersInLift(), building);
        lift.showLift();
    }
}