package com.company;


import java.util.ArrayList;
import java.util.List;

public class Building {
    private int floors;
    private List<Floor> floorList;

    public Building(int floors) {
        this.floors = floors;
        generateFloors();
    }

    private void generateFloors() {
        floorList = new ArrayList<>();
        for (int i = 1; i <= floors; i++) {
            floorList.add(new Floor(i));
        }
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    public int getFloors() {
        return floors;
    }

}