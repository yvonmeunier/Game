package com.company.ROGUELITE_GAME.Entities.NPCs;

import com.company.ROGUELITE_GAME.Entities.Projectiles.Bullet;
import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Projectile;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fly extends NPC {

    private BufferedImage[] moveAnimation;
    private BufferedImage[] deathAnimation;
    private BufferedImage sprites;
    private int hp;

    public Fly(Point coord) {
        setCoordinates(coord);
        setHurtBox(new Circle(8f));
        setCurrentVector(new Vector2D());
        setSpeed(1);
        setAccelerationRate(0.2f);
        MovableRepository.getInstance().getEntities().add(this);
        CollidableRepository.getInstance().getEntities().add(this);

        this.hp = 500;
    }

    @Override
    public boolean onPlayerCollide(Player player) {
        if (player.isDashing()){
            this.hp = 0;
        }
        return false;
    }

    @Override
    public boolean onProjectileCollide(Projectile projectile) {
        return true;
    }



    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY()- (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.GREEN);
    }

    public void update(Player player) {
        setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(),Vector2D.ZERO,getAccelerationRate())));
        // move towards player

        if (getCurrentVector().x != 0 && getCurrentVector().y != 0) {
            setCurrentVector(getCurrentVector().multiplyVector(0.7f));
        }
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));
        capSpeed();
    }
}
