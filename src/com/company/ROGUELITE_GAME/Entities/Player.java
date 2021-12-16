package com.company.ROGUELITE_GAME.Entities;

import com.company.ROGUELITE_GAME.Blockade;
import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Door;
import com.company.ROGUELITE_GAME.Entities.NPCs.Fly;
import com.company.ROGUELITE_GAME.Entities.NPCs.NPC;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Bomb;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Bullet;
import com.company.ROGUELITE_GAME.Entities.Projectiles.EnemyBullet;
import com.company.ROGUELITE_GAME.GamePad;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
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
import java.util.Random;

public class Player extends ControllableEntity {
    MouseController mouse;
    private float bulletDelay = 30;
    private float shootTimer = 0;
    private int shootCount = 0;
    private boolean dashing;
    private Vector2D dashingTo;
    private int iFrames;
    private int hp = 10;
    private BufferedImage sprites;
    private HashMap<String, Image[]> animations;
    private Image[] currentAnimation;
    private int currentFrame;
    private int animationTimer;
    private final int frameDelay = 10;
    private final Random rnd = new Random();

    public Player(MovementController keyboard, MouseController mouse, Point coord) {
        super(keyboard);
        this.mouse = mouse;
        animations = new HashMap<>();
        loadSprites();
        setHurtBox(new Circle(8f));
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
        if (!dashing) {
            updateVector();
            updateMouse();
        }

        if (mouse.buttonDown(1) && shootTimer <= 0) {
            shoot();
            shootTimer = bulletDelay;
        }
        if ((shootCount % 5) == 0 && shootTimer == 5) {
            new Sound("reload").play();
        }
        if (mouse.buttonDownOnce(3)) {
            dash();
        }
        if (getController() instanceof GamePad gp && gp.isBombPressed() && shootTimer <= 0) {
            bomb();
            shootTimer = bulletDelay;
        }
        if (dashing) {
            dash();
        }

        updateAnimation();

        if (animationTimer <= 0 && (Math.abs(getCurrentVector().x) > 0.3 || Math.abs(getCurrentVector().y) > 0.3)) {
            playFootStep();
            currentFrame++;
            animationTimer = frameDelay;

        }
        if (currentFrame > 5) {
            currentFrame = 0;
        }
        animationTimer--;
        shootTimer--;
        iFrames--;
    }

    private void playFootStep() {

        switch (currentFrame) {
            case 1:
                new Sound("foot1").play();
                break;
            case 3:
                new Sound("foot2").play();
                break;
            case 5:
                new Sound("foot3").play();
                break;
            default:
                break;
        }

    }

    private void updateAnimation() {

        Vector2D direction;
        direction = Vector2D.normalizeVector(new Vector2D(mouse.getPosition().x - getCoordinates().getX() + Camera.getInstance().getCoordinates().getX() - 16, mouse.getPosition().y - getCoordinates().getY() + Camera.getInstance().getCoordinates().getY() - 16));

        if (direction.y <= 0) {
            if (direction.x >= -0.45 && direction.x <= 0.45) {
                currentAnimation = animations.get("up");
            }
            if (direction.x < -0.45) {
                currentAnimation = animations.get("leftUp");
            }
            if (direction.x > 0.45) {
                currentAnimation = animations.get("rightUp");
            }
        }

        if (direction.y > 0) {
            if (direction.x >= -0.45 && direction.x <= 0.45) {
                currentAnimation = animations.get("down");
            }
            if (direction.x < -0.45) {
                currentAnimation = animations.get("leftDown");
            }
            if (direction.x > 0.45) {
                currentAnimation = animations.get("rightDown");
            }
        }

    }

    public int getHp() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    private void dash() {
        float dashSpeed = 50;
        if (!dashing) {
            dashingTo = this.getCoordinates().toVector().addVector(getCurrentVector().multiplyVector(dashSpeed));
            dashing = true;
            iFrames = 20;
        }
        Vector2D velocity = Vector2D.lerp(getCoordinates().toVector(), dashingTo, 0.3f);
        setCoordinates(velocity.toPoint());
        if (Vector2D.getDistanceBetweenVectors(getCoordinates().toVector(), dashingTo) < 10) {
            dashing = false;
            dashingTo = Vector2D.ZERO;
            return;
        }

    }

    private void shoot() {
        new Sound("gun").play();
        shootCount++;
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
        if ((other instanceof NPC || other instanceof EnemyBullet) && iFrames <= 0 && !dashing) {
            new Sound("oof").play();
            hp -= 3;
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
        buffer.drawImage(currentAnimation[currentFrame], getCoordinates().getX() - Camera.getInstance().getCoordinates().getX() - 17, getCoordinates().getY() - Camera.getInstance().getCoordinates().getY() - 28);
        //buffer.drawCircle(getCoordinates().getX() - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - Camera.getInstance().getCoordinates().getY(), 8f, Color.GREEN);
    }

    @Override
    public void loadSprites() {
        try {
            sprites = ImageIO.read(getClass().getResourceAsStream("/LayoutEntities/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image[] up = new Image[6];
        Image[] down = new Image[6];
        Image[] leftUp = new Image[6];
        Image[] rightUp = new Image[6];
        Image[] leftDown = new Image[6];
        Image[] rightDown = new Image[6];

        // F la taille de mes sprites sont pas consistante RIP
        // SORRY TUCKER
        // up X
        up[0] = sprites.getSubimage(0, 47, 18, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        up[1] = sprites.getSubimage(18, 47, 17, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        up[2] = sprites.getSubimage(35, 47, 17, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        up[3] = sprites.getSubimage(52, 47, 18, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        up[4] = sprites.getSubimage(70, 47, 18, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        up[5] = sprites.getSubimage(88, 47, 18, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        animations.put("up", up);
        // down X
        down[0] = sprites.getSubimage(0, 0, 17, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        down[1] = sprites.getSubimage(18, 0, 17, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        down[2] = sprites.getSubimage(35, 0, 18, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        down[3] = sprites.getSubimage(52, 0, 18, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        down[4] = sprites.getSubimage(70, 0, 17, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        down[5] = sprites.getSubimage(87, 0, 17, 23).getScaledInstance(52, 52, Image.SCALE_FAST);
        animations.put("down", down);
        //  left up X
        leftUp[0] = sprites.getSubimage(0, 118, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftUp[1] = sprites.getSubimage(19, 118, 18, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftUp[2] = sprites.getSubimage(37, 118, 18, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftUp[3] = sprites.getSubimage(55, 118, 16, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftUp[4] = sprites.getSubimage(71, 118, 17, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftUp[5] = sprites.getSubimage(88, 118, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        animations.put("leftUp", leftUp);
        // left down X
        leftDown[0] = sprites.getSubimage(0, 94, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftDown[1] = sprites.getSubimage(19, 94, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftDown[2] = sprites.getSubimage(38, 94, 20, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftDown[3] = sprites.getSubimage(58, 94, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftDown[4] = sprites.getSubimage(77, 94, 17, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        leftDown[5] = sprites.getSubimage(94, 94, 17, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        animations.put("leftDown", leftDown);
        // right up X
        rightUp[0] = sprites.getSubimage(0, 70, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightUp[1] = sprites.getSubimage(19, 70, 17, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightUp[2] = sprites.getSubimage(36, 70, 16, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightUp[3] = sprites.getSubimage(52, 70, 18, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightUp[4] = sprites.getSubimage(70, 70, 18, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightUp[5] = sprites.getSubimage(88, 70, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        animations.put("rightUp", rightUp);
        // right down X
        rightDown[0] = sprites.getSubimage(0, 24, 17, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightDown[1] = sprites.getSubimage(17, 24, 17, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightDown[2] = sprites.getSubimage(34, 24, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightDown[3] = sprites.getSubimage(53, 24, 20, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightDown[4] = sprites.getSubimage(73, 24, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        rightDown[5] = sprites.getSubimage(92, 24, 19, 24).getScaledInstance(52, 52, Image.SCALE_FAST);
        animations.put("rightDown", rightDown);
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