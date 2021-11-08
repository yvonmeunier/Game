package com.company.engine.entities;

import com.company.engine.math.shapes.Shape;

public abstract class CollidableEntity extends StaticEntity {

    private Shape hurtBox;

    public abstract void onCollide(MovableEntity other);

    public void setHurtBox(Shape shape){
        this.hurtBox = shape;
    }
}
