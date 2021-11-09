package com.company.Tiled;

import com.company.Room;

public class RoomParser extends XMLParser<Room>{
    public RoomParser(String file) {
        super(file);
    }

    @Override
    public Room result() {
        return null;
    }
}
