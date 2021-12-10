package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera extends MovableEntity {
    private MovableEntity followedEntity;
    private static Camera instance;

    public MovableEntity getFollowedEntity() {
        return followedEntity;
    }

    public static Camera getInstance() {
        if (instance == null){
            instance = new Camera();
        }
        return instance;
    }

    private Camera () {
        setHurtBox(new Rectangle(1280,720));
        setCoordinates(new Point(0,0));
    }

    public void update(MovableEntity target) {
        followedEntity = target;
        float camX = followedEntity.getCoordinates().getX() - 640;
        float camY = followedEntity.getCoordinates().getY() - 360;
        if (camX < 0) {
            camX = 0;
        }
        if (camY < 0) {
            camY = 0;
        }
        if (camX > 330) {
            camX = 330 - followedEntity.getSpeed();
        }
        if (camY > 269) {
            camY = 269 - followedEntity.getSpeed();
        }
        setCoordinates(new Point(camX,camY));
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }
    public void draw (Buffer buffer, BufferedImage world) {
        buffer.drawImage(world.getSubimage((int)getCoordinates().getX(),(int)getCoordinates().getY(),1280,720),new Point(0,0));
        for (CollidableEntity entity : CollidableRepository.getInstance().getEntities()) {
            entity.draw(buffer);
        }
        followedEntity.draw(buffer);
    }

    @Override
    public void draw(Buffer buffer) {

    }
}
