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
        setCurrentVector(Vector2D.lerp(getCurrentVector(),getCurrentVector().multiplyVector(-1),0.3f));
        if (getController().isUpPressed()) {
            setCurrentVector(Vector2D.lerp(getCurrentVector(),new Vector2D(0,-1),0.3f).addVector(getCurrentVector()));
        }
        if (getController().isDownPressed()){
            setCurrentVector(Vector2D.lerp(getCurrentVector(),new Vector2D(0,1),0.3f).addVector(getCurrentVector()));
        }
        if (getController().isLeftPressed()){
            setCurrentVector(Vector2D.lerp(getCurrentVector(),new Vector2D(-1,0),0.3f).addVector(getCurrentVector()));
        }
        if (getController().isRightPressed()){
            setCurrentVector(Vector2D.lerp(getCurrentVector(),new Vector2D(1,0),0.3f).addVector(getCurrentVector()));
        }
        if ((getController().isDownPressed() && (getController().isLeftPressed() || getController().isRightPressed())) || (getController().isUpPressed() && (getController().isLeftPressed() || getController().isRightPressed()))) {
            getCurrentVector().multiplyVector(0.7f);
        }
        getCurrentVector().multiplyVector(8);//speed
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
