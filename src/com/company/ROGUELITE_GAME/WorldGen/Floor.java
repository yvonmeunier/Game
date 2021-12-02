package com.company.ROGUELITE_GAME.WorldGen;

import java.io.IOException;

public class Floor {
    private Room[] floor;

    public Floor(int width, int height) {
        floor = new Room[width * height];
    }

    public Room getRoomById(int id) {
        return floor[id];
    }

    public void placeRoomAtId(int id, boolean[] doors) {
        try {
            floor[id] = new Room(doors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
