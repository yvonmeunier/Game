package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Rectangle;

import java.awt.*;


public class Blockade extends CollidableEntity {

    public Blockade(Point coord) {
        setCoordinates(coord);
        setHurtBox(new Rectangle(32,32));
        CollidableRepository.getInstance().getEntities().add(this);
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(getCoordinates().getX() - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - Camera.getInstance().getCoordinates().getY(),32,32, Color.BLUE);
    }
}
