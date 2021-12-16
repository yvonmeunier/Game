package com.company.ROGUELITE_GAME.Entities.NPCs;

import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Entities.Projectiles.EnemyBullet;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Projectile;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Rectangle;
import com.company.engine.sound.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Amogus extends NPC {
    private Image moveAnimation;
    private Image[] deathAnimation;
    private BufferedImage sprites;
    private BufferedImage deathSprites;
    private int frameDelay = 5;
    private int currentFrame = 0;
    private int timer = 0;
    private Random rnd = new Random();

    @Override
    public void loadSprites() {
        try {
            sprites = ImageIO.read(this.getClass().getResourceAsStream("/LayoutEntities/Dynamic/Enemies/test.png"));
            deathSprites = ImageIO.read(this.getClass().getResourceAsStream("/LayoutEntities/Dynamic/Enemies/monster_010_fly.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        moveAnimation = sprites.getScaledInstance(75, 75, Image.SCALE_FAST);

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 4; j++) {
                deathAnimation[i + j] = deathSprites.getSubimage(j, i * 64, 64, 64);
            }
        }
    }

    public Amogus(com.company.engine.math.Point coord) {
        setCoordinates(coord);
        setHurtBox(new Rectangle(20, 20));
        setCurrentVector(new Vector2D());
        setSpeed(5);
        setAccelerationRate(0.2f);
        MovableRepository.getInstance().queueNewEntity(this);
        CollidableRepository.getInstance().getEntities().add(this);
        deathAnimation = new BufferedImage[11];
        hp = 1;
        loadSprites();
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
        //buffer.drawRectangle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth(),getHurtBox().getHeight(), Color.GREEN);
        if (hp <= 0) {
            buffer.drawImage(deathAnimation[currentFrame], new com.company.engine.math.Point(getCoordinates().getX() - (this.getHurtBox().getWidth() - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth()) - Camera.getInstance().getCoordinates().getX() - 25, getCoordinates().getY() - (this.getHurtBox().getHeight() - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight()) - Camera.getInstance().getCoordinates().getY() - 40));
        } else {
            buffer.drawImage(moveAnimation, new Point(getCoordinates().getX() - (this.getHurtBox().getWidth() - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth()) - Camera.getInstance().getCoordinates().getX() - 25, getCoordinates().getY() - (this.getHurtBox().getHeight() - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight()) - Camera.getInstance().getCoordinates().getY() - 40));
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

            if (timer % 60 == 0) {
                shoot(player);
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

    private void shoot(Player player) {

        Vector2D velocityA = Vector2D.normalizeVector(new Vector2D(player.getCoordinates().getX() - getCoordinates().getX(), player.getCoordinates().getY() - getCoordinates().getY()));
        Vector2D velocityB = Vector2D.normalizeVector(velocityA.addVector(rnd.nextFloat()));
        Vector2D velocityC = Vector2D.normalizeVector(velocityA.subVector(rnd.nextFloat()));
        new EnemyBullet(getCoordinates(), velocityA);
        new EnemyBullet(getCoordinates(),velocityB);
        new EnemyBullet(getCoordinates(),velocityC);
    }

}
