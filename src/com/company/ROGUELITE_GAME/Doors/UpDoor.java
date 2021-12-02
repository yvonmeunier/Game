package com.company.ROGUELITE_GAME.Doors;

import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class UpDoor extends Door {

    public UpDoor(Point coord) {
        setCoordinates(coord);
        setHurtBox(new Rectangle(32,32));
        try {
            setSprites(ImageIO.read(this.getClass().getResourceAsStream("/LayoutEntities/Static/doors.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CollidableRepository.getInstance().getEntities().add(this);
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {
        setDoorImage(getSprites().getSubimage(9,2,49,43));
        buffer.drawImage(getDoorImage().getScaledInstance(75,75,Image.SCALE_DEFAULT), new Point(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX() - getHurtBox().getWidth()/2, getCoordinates().getY()- (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY() - 75/2));
    }
}
