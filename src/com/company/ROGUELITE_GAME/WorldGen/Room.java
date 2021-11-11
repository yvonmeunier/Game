package com.company.ROGUELITE_GAME.WorldGen;

public class Room {

    // 31 width 19 height
    private final int width = 31;
    private final int height = 19;
    private boolean hasUpNeighbour;
    private boolean hasDownNeighbour;
    private boolean hasLeftNeighbour;
    private boolean hasRightNeighbour;

    private Layout layout;

    public Room() {
        layout = new Layout(this);
    }

}
