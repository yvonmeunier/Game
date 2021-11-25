package com.company.engine.entities;

import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;

public abstract class MovableEntity extends UpdatableEntity implements Cloneable {

    private Vector2D currentVector;
    private float accelerationRate;
    private float speed;
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public float getAccelerationRate() {
        return accelerationRate;
    }

    public float getSpeed() {
        return speed;
    }

    public void setAccelerationRate(float accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void update() {

    }

    public abstract void onColliding(StaticEntity other);

    public void move() {
        setCoordinates(new Point(getCoordinates().getX() + getCurrentVector().x, getCoordinates().getY() + getCurrentVector().y));
    }

    public void capSpeed() {

        if (getCurrentVector().x > getSpeed()) {
            setCurrentVector(new Vector2D(getSpeed(),getCurrentVector().y));
        }
        if (getCurrentVector().x < -getSpeed()) {
            setCurrentVector(new Vector2D(-getSpeed(),getCurrentVector().y));
        }
        if (getCurrentVector().y > getSpeed()) {
            setCurrentVector(new Vector2D(getCurrentVector().x,getSpeed()));
        }
        if (getCurrentVector().y < -getSpeed()) {
            setCurrentVector(new Vector2D(getCurrentVector().x,-getSpeed()));
        }

    }

    public Vector2D getCurrentVector() {
        return currentVector;
    }

    public void setCurrentVector(Vector2D currentVector) {
        this.currentVector = currentVector;
    }
}
