package com.company.engine.entities;

import com.company.engine.math.shapes.Shape;

public abstract class CollidableEntity extends StaticEntity {

    private Shape hurtBox;
    private Shape hitBox;

    public abstract void onCollide(MovableEntity other);

    public void setHurtBox(Shape shape) {
        this.hurtBox = shape;
    }

    public void setHitBox(Shape shape) {
        this.hitBox = shape;
    }

    public Shape getHurtBox() {
        return hurtBox;
    }

    public Shape getHitBox() {
        return hitBox;
    }

}
