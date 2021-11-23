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
    }

    @Override
    public void update() {
        super.update();
        // friction
        setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(),Vector2D.ZERO,0.3f)));
        // apply player's desired vector
        if (getController().isUpPressed()) {
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(0,-1),0.3f)));
        }else {
            if (getController().isMoving()){
                setCurrentVector(new Vector2D(getCurrentVector().x,0));
            }
        }
        if (getController().isDownPressed()){
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(0,1),0.3f)));
        }else {
            if (getController().isMoving()){
                setCurrentVector(new Vector2D(getCurrentVector().x,0));
            }
        }
        if (getController().isLeftPressed()){
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(-1,0),0.3f)));
        }else {
            if (getController().isMoving()){
                setCurrentVector(new Vector2D(0,getCurrentVector().y));
            }
        }
        if (getController().isRightPressed()){
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(),new Vector2D(1,0),0.3f)));
        }else {
            if (getController().isMoving()){
                setCurrentVector(new Vector2D(0,getCurrentVector().y));
            }
        }
        if (getCurrentVector() == Vector2D.ONE || getCurrentVector() == Vector2D.MINUS_ONE) {
            setCurrentVector(getCurrentVector().multiplyVector(0.7f));
        }
        setCurrentVector(getCurrentVector().multiplyVector(2));//speed multiplier
        // speed cap
        if (getCurrentVector().x > 2) {
            setCurrentVector(new Vector2D(2,getCurrentVector().y));
        }
        if (getCurrentVector().x < -2) {
            setCurrentVector(new Vector2D(-2,getCurrentVector().y));
        }
        if (getCurrentVector().y > 2) {
            setCurrentVector(new Vector2D(getCurrentVector().x,2));
        }
        if (getCurrentVector().y < -2) {
            setCurrentVector(new Vector2D(getCurrentVector().x,-2));
        }
        System.out.println(getCurrentVector().toString());
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
}