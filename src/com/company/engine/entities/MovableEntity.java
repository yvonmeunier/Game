package com.company.engine.entities;

import com.company.engine.math.Vector2D;

public abstract class MovableEntity extends UpdatableEntity {

    private Vector2D currentVector;

    @Override
    public void update() {

    }



    public abstract void onColliding(StaticEntity other);

}
