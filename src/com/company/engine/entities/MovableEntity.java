package com.company.engine.entities;

import com.company.engine.math.Vector2;

public abstract class MovableEntity extends UpdatableEntity {

    private Vector2 currentVector;

    @Override
    public void update() {

    }



    public abstract void onColliding(StaticEntity other);

}
