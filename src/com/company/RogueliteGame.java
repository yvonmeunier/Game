package com.company;

import com.company.engine.Buffer;
import com.company.engine.Game;

public class RogueliteGame extends Game {

    FloorGenerator floorGenerator;
    Floor currentFloor;


    @Override
    public void init() {
        floorGenerator = new FloorGenerator();
        currentFloor = new Floor();
    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Buffer buffer) {

    }
}
