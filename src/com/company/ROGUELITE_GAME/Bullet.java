package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;

import java.awt.*;

public class Bullet extends MovableEntity {

    private final int lifespan = 60;
    private int lifeTimer;


    public Bullet(Point coordinates,Vector2D velo) {
        setCoordinates(coordinates);
        setHurtBox(new Circle(3));
        setSpeed(2);
        setAccelerationRate(0.3f);
        setCurrentVector(velo);
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));
        CollidableRepository.getInstance().getEntities().add(this);
        MovableRepository.getInstance().getEntities().add(this);
        lifeTimer = 1;
    }

    @Override
    public void update() {
        if (lifeTimer >= lifespan) {
            active = false;
        }
        capSpeed();
        move();
        lifeTimer++;
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

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY()- (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.GREEN);
    }
}
