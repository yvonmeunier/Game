package com.company.ROGUELITE_GAME;

import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Circle;
import com.company.engine.math.shapes.Rectangle;

import java.awt.*;


public class Blockade extends CollidableEntity {

    public Blockade(Point coord) {
        setCoordinates(coord);
        setHurtBox(new Rectangle(32,32));
        //setHurtBox(new Circle(16f));
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(getCoordinates().getX(), getCoordinates().getY(),32,32, Color.BLUE);
        //buffer.drawCircle(getCoordinates().getX(), getCoordinates().getY(),16f, Color.BLUE);
    }
}
