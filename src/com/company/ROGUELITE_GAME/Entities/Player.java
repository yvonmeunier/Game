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
import com.company.engine.sound.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Player extends ControllableEntity {
    // width : 17, height : 22
    MouseController mouse;
    private float bulletDelay = 30;
    private float shootTimer = 0;
    private boolean dashing;
    private Vector2D dashingTo;
    private int iFrames;
    private int hp = 10;
    private BufferedImage sprites;
    private HashMap<String, BufferedImage[]> animations;

    public Player(MovementController keyboard, MouseController mouse, Point coord) {
        super(keyboard);
        this.mouse = mouse;
        animations = new HashMap<>();
        loadSprites();
        setHurtBox(new Circle(16f));
        setCoordinates(coord);
        setCurrentVector(new Vector2D());
        setSpeed(2.5f);
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
        if(getController() instanceof GamePad gp && gp.isBombPressed() && shootTimer <= 0) {
            bomb();
            shootTimer = bulletDelay;
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
        iFrames--;
    }

    public int getHp() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    private void dash() {
        float dashSpeed = 50;
        if(!dashing) {
            dashingTo = this.getCoordinates().toVector().addVector(getCurrentVector().multiplyVector(dashSpeed));
            dashing = true;
            iFrames = 20;
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
        new Sound("mur").play();
        new Bomb(getCoordinates());
    }

    private void updateMouse() {
        mouse.poll();
    }

    @Override
    public void onCollide(MovableEntity other) {
        if (other instanceof NPC && iFrames <= 0 && !dashing) {
            new Sound("oof").play();
            hp -=3;
            iFrames = 60;
        }
    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Blockade) {
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
        }

        if (other instanceof Door) {
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
        }
        if (other instanceof Fly) {
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
        }
        if (!isDashing()) {
            if (other instanceof NPC) {
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

    @Override
    public void loadSprites() {
        try {
            sprites = ImageIO.read(getClass().getResourceAsStream("/LayoutEntities/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {

        }

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