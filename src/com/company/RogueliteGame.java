package com.company;

public class RogueliteGame {

    FloorGenerator floorGenerator;
    Floor currentFloor;

    public void start() {

        floorGenerator = new FloorGenerator();
        currentFloor = floorGenerator.generateFloor(500,500,800);

    }

}
