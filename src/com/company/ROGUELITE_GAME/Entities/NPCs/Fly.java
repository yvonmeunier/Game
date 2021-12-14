package com.company.ROGUELITE_GAME.Entities.NPCs;

import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Projectile;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Fly extends NPC {

    private BufferedImage[] moveAnimation;
    private BufferedImage[] deathAnimation;
    private BufferedImage sprites;
    private int frameDelay = 5;
    private int currentFrame = 0;
    private int timer = 0;

    public Fly(Point coord) {
        setCoordinates(coord);
        setHurtBox(new Circle(10f));
        setCurrentVector(new Vector2D());
        setSpeed(2);
        setAccelerationRate(0.2f);
        MovableRepository.getInstance().getEntities().add(this);
        CollidableRepository.getInstance().getEntities().add(this);
        MovingRepository.getInstance().getEntities().add(this);
        moveAnimation = new BufferedImage[2];
        deathAnimation = new BufferedImage[11];
        hp = 5;
        try {
            sprites = ImageIO.read(this.getClass().getResourceAsStream("/LayoutEntities/Dynamic/Enemies/monster_010_fly.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        moveAnimation[0] = sprites.getSubimage(0, 32, 32, 32);
        moveAnimation[1] = sprites.getSubimage(32, 32, 32, 32);

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 4; j++) {
                deathAnimation[i + j] = sprites.getSubimage(j, i * 64,64,64);
            }
        }
    }

    @Override
    public boolean onPlayerCollide(Player player) {
        if (player.isDashing()) {
            hp = 0;
        }
        return false;
    }

    @Override
    public boolean onProjectileCollide(Projectile projectile) {
        return true;
    }


    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Player) {
            setCurrentVector(getCurrentVector().subVector(CollisionManager.resolve(this, other).multiplyVector(0.1f)));
        }
    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void draw(Buffer buffer) {
        //buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.GREEN);
        if (hp <= 0) {
            buffer.drawImage(deathAnimation[currentFrame].getScaledInstance(52, 52, Image.SCALE_DEFAULT), new Point(getCoordinates().getX() - (this.getHurtBox().getWidth() - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY()));
        }else {
            buffer.drawImage(moveAnimation[currentFrame].getScaledInstance(52, 52, Image.SCALE_DEFAULT), new Point(getCoordinates().getX() - (this.getHurtBox().getWidth() - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY()));
        }
    }

    public void update(Player player) {
        timer++;
        if (hp > 0) {
            setCurrentVector(Vector2D.normalizeVector(new Vector2D(player.getCoordinates().getX() - getCoordinates().getX(), player.getCoordinates().getY() - getCoordinates().getY())));
            if (getCurrentVector().x != 0 && getCurrentVector().y != 0) {
                setCurrentVector(getCurrentVector().multiplyVector(0.7f));
            }
            setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));
            capSpeed();
            if (timer > frameDelay) {
                timer = 0;
                currentFrame++;
                if (currentFrame > 1) {
                    currentFrame = 0;
                }
            }
        } else {
            if (timer > frameDelay) {
                timer = 0;
                currentFrame++;
                if (currentFrame > 11) {
                    currentFrame = 0;
                }
            }
        }


    }
}
