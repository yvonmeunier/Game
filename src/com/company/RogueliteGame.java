package com.company;

public class RogueliteGame {

    FloorGenerator floorGenerator;
    Floor currentFloor;

    public void start() {

        floorGenerator = new FloorGenerator();
        currentFloor = floorGenerator.generateFloor(50,50,80);// level doesnt generate bugs but width does

    }

}
