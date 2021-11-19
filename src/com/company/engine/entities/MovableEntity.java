package com.company.engine.entities;

import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;

public abstract class MovableEntity extends UpdatableEntity {

    private Vector2D currentVector;

    @Override
    public void update() {

    }

    public abstract void onColliding(StaticEntity other);

    public void move() {

        setCoordinates(new Point(getCoordinates().getX() + getCurrentVector().x,getCoordinates().getY() + getCurrentVector().y));
    }

    public Vector2D getCurrentVector() {
        return currentVector;
    }

    public void setCurrentVector(Vector2D currentVector) {
        this.currentVector = currentVector;
    }
}
