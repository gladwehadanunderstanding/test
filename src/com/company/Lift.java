package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public class Lift {
    private int currentFloorNumber;
    private int nextFloor;
    private int freePlaces;
    private int targetFloor;
    private int direction;
    private int capacity;
    private Building building;
    private Floor currentFloor;
    private List<Floor> floorlist;
    private List<Passenger> liftPassangers = new ArrayList<>();
    private List<Passenger> passangersEnter = new ArrayList<>();
    private List<Passenger> passengersExit = new ArrayList<>();


    Lift(int currentFloor, int capacity, Building building) {
        this.currentFloorNumber = currentFloor;
        this.capacity = capacity;
        this.building = building;
        this.floorlist = building.getFloorList();
        this.targetFloor = Floor.getFloorsAmount();
    }

    public void showLift(){
        int counter = 1;
        while (true) {
            System.out.println("Step #" + counter);
            setCurrentFloor();
            System.out.println("Current floor: " + currentFloorNumber + " | " + "Passengers in lift :" + liftPassangers.size() + " | " + "Passengers on the floor: "+ currentFloor.getPassengersQuantityOnTheFloor());
            leaveLift();
            enterLift();
            System.out.println("Passengers entered: " + passangersEnter.size() + " | " + "Passengers left: " + passengersExit.size() + " | " + "Passengers stayed: " + liftPassangers.size());
            theGoalFloor();
            theNextFloor();
            System.out.println("Lift moving to: " + targetFloor + " floor");
            System.out.println("Next floor: " + nextFloor);
            followTheNextFloor();
            counter++;
            if (counter == 15)
            {
                System.exit(1);
            }
        }
    }

    public void enterLift() {
        List<Passenger> passengersOnTheFloor = currentFloor.getPassengersOnTheFloor();
        passangersEnter.clear();
        if (direction > 0) {
            passangersEnter = passengersOnTheFloor.stream()
                    .filter(passenger -> passenger.getRequiredFloor() - currentFloorNumber > 0)
                    .limit(freePlaces)
                    .collect(toCollection(ArrayList::new));
        } else if (direction < 0) {
            passangersEnter = passengersOnTheFloor.stream()
                    .filter(passenger -> passenger.getRequiredFloor() - currentFloorNumber < 0)
                    .limit(freePlaces)
                    .collect(toCollection(ArrayList::new));
        } else if (liftPassangers.isEmpty()) {
            List<Passenger> passengersToGoUp = passengersOnTheFloor.stream()
                    .filter(passenger -> passenger.getRequiredFloor() > currentFloorNumber)
                    .collect(toCollection(ArrayList::new));
            List<Passenger> passengersToGoDown = passengersOnTheFloor.stream()
                    .filter(passenger -> passenger.getRequiredFloor() < currentFloorNumber)
                    .collect(toCollection(ArrayList::new));
            if (passengersToGoUp.size() > passengersToGoDown.size())
                passangersEnter = passengersToGoUp.stream()
                        .limit(freePlaces)
                        .collect(toCollection(ArrayList::new));
            else
                passangersEnter = passengersToGoDown.stream()
                        .limit(freePlaces)
                        .collect(toCollection(ArrayList::new));
        }
        passengersOnTheFloor.removeAll(passangersEnter);
        currentFloor.setPassengersOnTheFloor(passengersOnTheFloor);
        liftPassangers.addAll(passangersEnter);
        liftPassangers = liftPassangers.stream()
                .peek(passenger -> passenger.setCurrentFloor(currentFloorNumber))
                .collect(toCollection(ArrayList::new));
        freePlaces = capacity - liftPassangers.size();
    }

    public void leaveLift() {
        passengersExit.clear();
        freePlaces = capacity - liftPassangers.size();
        if (!liftPassangers.isEmpty()) {
            List<Passenger> passengersOnTheFloor = currentFloor.getPassengersOnTheFloor();
            passengersExit = liftPassangers.stream()
                    .filter(passenger -> passenger.getRequiredFloor() == currentFloorNumber)
                    .peek(passenger -> passenger.setCurrentFloor(currentFloorNumber))
                    .peek(passenger -> passenger.getRequiredFloor())
                    .collect(toCollection(ArrayList::new));
            passengersOnTheFloor.addAll(passengersExit);
            currentFloor.setPassengersOnTheFloor(passengersOnTheFloor);
            liftPassangers.removeAll(passengersExit);
        }
    }

    public void theGoalFloor() {
        if (!liftPassangers.isEmpty())
            targetFloor = liftPassangers.stream()
                    .max(Comparator.comparingInt(passenger -> Math.abs(passenger.getRequiredFloor() - currentFloorNumber)))
                    .get()
                    .getRequiredFloor();
        else if (targetFloor < building.getFloors())
            targetFloor = building.getFloors();
        else if (targetFloor == building.getFloors())
            targetFloor = 1;
    }

    public void theNextFloor() {
        int theNextFloorForEntered = Floor.getFloorsAmount();
        if (freePlaces != 0) {
            List<Floor> theNextFloors = building.getFloorList().stream().filter(floor -> {
                if (direction > 0)
                    return floor.getFloorQuantity() > currentFloorNumber && floor.getFloorQuantity() < targetFloor;
                else if (direction < 0)
                    return floor.getFloorQuantity() < currentFloorNumber && floor.getFloorQuantity() > targetFloor;
                return false;
            }).collect(toCollection(ArrayList::new));
            if (direction > 0)
                theNextFloorForEntered = theNextFloors.stream()
                        .filter(floor -> floor.isButtonUp())
                        .map(floor -> floor.getFloorQuantity())
                        .min(Comparator.naturalOrder())
                        .orElse(targetFloor);
            else if (direction < 0)
                theNextFloorForEntered = theNextFloors.stream()
                        .filter(floor -> floor.isButtonDown())
                        .map(floor -> floor.getFloorQuantity())
                        .max(Comparator.naturalOrder())
                        .orElse(targetFloor);
        }
           int theNextFloorForPassengers = liftPassangers.stream()
                .min(Comparator.comparingInt(value -> Math.abs(value.getRequiredFloor() - currentFloorNumber)))
                   .get()
                   .getRequiredFloor();

        nextFloor = Math.min(theNextFloorForEntered, theNextFloorForPassengers);
    }

    private void followTheNextFloor() {
        currentFloorNumber = nextFloor;
        currentFloor.buttonPress();
        currentFloor.countPassengerOnTheFloor();
    }

    private void setCurrentFloor() {
        currentFloor = floorlist.stream()
                .filter(floor -> floor.getFloorQuantity() == currentFloorNumber)
                .findFirst()
                .get();
        direction = targetFloor - currentFloorNumber;
    }


    public int getCurrentFloorNumber() {
        return currentFloorNumber;
    }

    public int getNextFloor() {
        return nextFloor;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public int getDirection() {
        return direction;
    }

    public Building getBuilding() {
        return building;
    }

    public List<Passenger> getLiftPassangers() {
        return liftPassangers;
    }

    public List<Floor> getFloorlist() {
        return floorlist;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public List<Passenger> getPassangersEnter() {
        return passangersEnter;
    }

    public List<Passenger> getPassengersExit() {
        return passengersExit;
    }
}