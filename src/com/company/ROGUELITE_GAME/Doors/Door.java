package com.company.ROGUELITE_GAME.Doors;

import com.company.engine.entities.CollidableEntity;

import java.awt.image.BufferedImage;

public abstract class Door extends CollidableEntity {

    // width 49, height 43
    // sprite closed starts at 9, 2
    // sprite open starts at 73, 2
    private BufferedImage doorImage;
    private BufferedImage sprites;
    private boolean isOpen = false;

    public BufferedImage getDoorImage() {
        return doorImage;
    }

    public void setDoorImage(BufferedImage doorImage) {
        this.doorImage = doorImage;
    }

    public void setSprites(BufferedImage sprites) {
        this.sprites = sprites;
    }

    public BufferedImage getSprites() {
        return sprites;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
