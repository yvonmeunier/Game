package com.company.ROGUELITE_GAME.WorldGen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Room {

    // 31 width 19 height
    public BufferedImage roomImage;
    public Layout layout;

    public Room(boolean[] doors) throws IOException {
        roomImage = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/Rooms/Default/Default.png")));
        //layout = LayoutRepository.getInstance().getRandomLayout(doors);
    }

    public BufferedImage getRoomImage() {
        return roomImage;
    }
}
