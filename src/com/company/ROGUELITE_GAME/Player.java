package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.engine.Buffer;
import com.company.engine.controls.MouseController;
import com.company.engine.controls.MovementController;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.ControllableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;

import java.awt.*;

public class Player extends ControllableEntity {

    MouseController mouse;

    public Player(MovementController keyboard, MouseController mouse, Point coord) {
        super(keyboard);
        this.mouse = mouse;
        setHurtBox(new Circle(16f));
        setCoordinates(coord);
        setCurrentVector(new Vector2D());
        setSpeed(2);
        setAccelerationRate(0.3f);
        CollidableRepository.getInstance().getEntities().add(this);
        MovableRepository.getInstance().getEntities().add(this);
    }

    @Override
    public void update() {
        super.update();
        updateVector();
        updateMouse();
        if (getCurrentVector() != new Vector2D()) {
            MovingRepository.getInstance().getEntities().add(this);
        }else {
            if (MovingRepository.getInstance().getEntities().contains(this)) {
                MovingRepository.getInstance().getEntities().remove(this);
            }
        }
    }

    private void updateMouse() {
        mouse.poll();
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Blockade) {
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(),CollisionManager.resolve(this,other),0.0001f)));
        }

        if (other instanceof Door) {
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(),CollisionManager.resolve(this,other),0.0001f)));
        }

    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Blockade) {
            setCurrentVector(CollisionManager.resolvePhasing(this,other));
        }
        if (other instanceof Door) {
            setCurrentVector(CollisionManager.resolvePhasing(this,other));
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - Camera.getInstance().getCoordinates().getX(),getCoordinates().getY() - Camera.getInstance().getCoordinates().getY(),16f, Color.GREEN);

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
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));//speed multiplier
        capSpeed();
    }

}