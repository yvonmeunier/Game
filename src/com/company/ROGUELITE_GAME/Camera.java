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
    }

    public void update(MovableEntity target) {
        followedEntity = target;
        setCoordinates(new Point(followedEntity.getCoordinates().getX() - 640,followedEntity.getCoordinates().getY() - 360));
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
