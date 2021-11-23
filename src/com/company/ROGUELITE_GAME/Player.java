package com.company.ROGUELITE_GAME;

import com.company.engine.Buffer;
import com.company.engine.controls.MovementController;
import com.company.engine.entities.ControllableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.entities.StaticEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Rectangle;
import com.company.engine.math.shapes.Shape;

import java.awt.*;

public class Player extends ControllableEntity {

    public Player(MovementController controller, Point coord) {
        super(controller);
        setHurtBox(new Rectangle(32f,32f));
        setCoordinates(coord);
        setCurrentVector(new Vector2D());
        setSpeed(2);
        setAccelerationRate(0.3f);
    }

    @Override
    public void update() {
        super.update();
        updateVector();
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));//speed multiplier
        capSpeed();
        move();
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void onColliding(StaticEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(this.getCoordinates().getX(),this.getCoordinates().getY(),32f,32f, Color.GREEN);
    }

    private void updateVector() {
        setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(),Vector2D.ZERO,getAccelerationRate())));
        if (getController().isUpPressed()) {
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(0,-1),getAccelerationRate())));
        }
        if (getController().isDownPressed()){
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(0,1),getAccelerationRate())));
        }
        if (getController().isLeftPressed()){
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(-1,0),getAccelerationRate())));
        }
        if (getController().isRightPressed()){
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(1,0),getAccelerationRate())));
        }
        if (getCurrentVector().x != 0 && getCurrentVector().y != 0) {
            setCurrentVector(getCurrentVector().multiplyVector(0.7f));
        }
    }

}