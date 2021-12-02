package com.company.ROGUELITE_GAME.WorldGen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Room {

    // 31 width 19 height
    public BufferedImage roomImage;
    public Layout layout;


    public Room() throws IOException {
        roomImage = ImageIO.read(this.getClass().getResourceAsStream("/Rooms/Default/Default.png"));

    }

    public BufferedImage getRoomImage() {
        return roomImage;
    }
}
