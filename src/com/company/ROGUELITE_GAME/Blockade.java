package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Shape;

public class Blockade extends CollidableEntity {

    public Blockade(Point coord, Shape shape) {
        setCoordinates(coord);
        setHurtBox(shape);
        CollidableRepository.getInstance().getEntities().add(this);
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {
        //buffer.drawRectangle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY()- (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth(), getHurtBox().getHeight(), Color.GREEN);
    }
}
