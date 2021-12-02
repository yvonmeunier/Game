package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.controls.Direction;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.entities.UpdatableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Door extends UpdatableEntity {

    // width 49, height 43
    // sprite closed starts at 9, 2
    // sprite open starts at 73, 2
    private BufferedImage doorImage;
    private BufferedImage sprites;
    private boolean isOpen = false;


    public static final int UP = 0;
    public static final int DOWN = 2;
    public static final int RIGHT = 1;
    public static final int LEFT = 3;

    private int direction;

    public int getDirection() {
        return direction;
    }

    public Door(Point coord, int direction) throws Exception {
        if (direction < UP || direction > LEFT) {
            throw new Exception("Door have an invalid direction");
        }
        this.direction = direction;
        setCoordinates(coord);
        switch (direction) {
            case UP | DOWN:
                setHurtBox(new Rectangle(75, 75));
                break;
            case RIGHT | LEFT:
                setHurtBox(new Rectangle(75, 75));
                break;
        }

        try {
            setSprites(ImageIO.read(this.getClass().getResourceAsStream("/LayoutEntities/Static/doors.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CollidableRepository.getInstance().getEntities().add(this);
    }


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
        if (!isOpen) {
            return sprites.getSubimage(73, 2, 49, 43);
        }
        return sprites.getSubimage(9, 2, 49, 43);
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void draw(Buffer buffer) {
        setDoorImage(getSprites());
        setDoorImage(buffer.flipImage(getDoorImage(), getCoordinates(), Math.toRadians(90 * direction)));
        buffer.drawImage(getDoorImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT), new Point(getCoordinates().getX() - (75 / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(),
                getCoordinates().getY() - (75 / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY()));
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void update() {
        if (isOpen) {
            switch (direction) {
                case UP | DOWN:
                    setHurtBox(new Rectangle(75, 50));
                    break;
                case RIGHT | LEFT:
                    setHurtBox(new Rectangle(70, 75));
                    break;
            }
        } else {
            setHurtBox(new Rectangle(75, 75));
        }
    }
}
