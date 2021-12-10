package com.company.ROGUELITE_GAME.Entities;

import com.company.ROGUELITE_GAME.Blockade;
import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Door;
import com.company.ROGUELITE_GAME.Entities.NPCs.Fly;
import com.company.ROGUELITE_GAME.Entities.NPCs.NPC;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Bomb;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Bullet;
import com.company.ROGUELITE_GAME.GamePad;
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
    private float bulletDelay = 30;
    private float shootTimer = 0;
    private boolean dashing;
    private Vector2D dashingTo;

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
        if(!dashing) {
            updateVector();
            updateMouse();
        }

        if (mouse.buttonDown(1) && shootTimer <= 0) {
            shoot();
            shootTimer = bulletDelay;
        }
        if (mouse.buttonDownOnce(3)) {
            dash();
        }
        if(getController() instanceof GamePad gp && gp.isBombPressed()) {
            bomb();
        }
        if (getCurrentVector() != Vector2D.ZERO) {
            MovingRepository.getInstance().getEntities().add(this);
        } else {
            if (MovingRepository.getInstance().getEntities().contains(this)) {
                MovingRepository.getInstance().getEntities().remove(this);
            }
        }
        if(dashing) {
            dash();
        }
        shootTimer--;
    }



    private void dash() {
        float dashSpeed = 50;
        if(!dashing) {
            dashingTo = this.getCoordinates().toVector().addVector(getCurrentVector().multiplyVector(dashSpeed));
            dashing = true;
        }
        Vector2D velocity = Vector2D.lerp(getCoordinates().toVector(), dashingTo, 0.3f);
        setCoordinates(velocity.toPoint());
        if(Vector2D.getDistanceBetweenVectors(getCoordinates().toVector(), dashingTo) < 10) {
            dashing = false;
            dashingTo = Vector2D.ZERO;
            return;
        }

    }

    private void shoot() {
        Vector2D velocity;
        velocity = Vector2D.normalizeVector(new Vector2D(mouse.getPosition().x - getCoordinates().getX() + Camera.getInstance().getCoordinates().getX() - 16, mouse.getPosition().y - getCoordinates().getY() + Camera.getInstance().getCoordinates().getY() - 16));
        new Bullet(getCoordinates(), velocity);
    }

    private void bomb() {
        new Bomb(getCoordinates());
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
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
        }

        if (other instanceof Door) {
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
        }
        if (!isDashing()) {
            if (other instanceof NPC npc) {
                setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
            }
        }

    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Blockade) {
            setCurrentVector(CollisionManager.resolvePhasing(this, other));
        }
        if (other instanceof Door) {
            setCurrentVector(CollisionManager.resolvePhasing(this, other));
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - Camera.getInstance().getCoordinates().getY(), 16f, Color.GREEN);
    }

    private void updateVector() {
        setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), Vector2D.ZERO, getAccelerationRate())));
        if (getController().isUpPressed()) {
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(), new Vector2D(0, -1), getAccelerationRate())));
        }
        if (getController().isDownPressed()) {
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(), new Vector2D(0, 1), getAccelerationRate())));
        }
        if (getController().isLeftPressed()) {
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(), new Vector2D(-1, 0), getAccelerationRate())));
        }
        if (getController().isRightPressed()) {
            setCurrentVector(getCurrentVector().addVector(Vector2D.lerp(getCurrentVector(), new Vector2D(1, 0), getAccelerationRate())));
        }
        if (getCurrentVector().x != 0 && getCurrentVector().y != 0) {
            setCurrentVector(getCurrentVector().multiplyVector(0.7f));
        }
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));//speed multiplier
        capSpeed();
    }

    public boolean isDashing() {
        return dashing;
    }
}