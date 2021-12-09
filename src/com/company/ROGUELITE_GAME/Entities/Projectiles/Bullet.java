package com.company.ROGUELITE_GAME.Entities.Projectiles;

import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.NPCs.Fly;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;

import java.awt.*;

public class Bullet extends Projectile {

    private final int lifespan = 120;
    private int lifeTimer;


    public Bullet(Point coordinates,Vector2D velo) {
        setCoordinates(coordinates);
        setHurtBox(new Circle(3));
        setSpeed(3);
        setAccelerationRate(0.3f);
        setCurrentVector(velo);
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));
        CollidableRepository.getInstance().getEntities().add(this);
        MovableRepository.getInstance().getEntities().add(this);
        MovingRepository.getInstance().getEntities().add(this);
        lifeTimer = 1;
    }


    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Fly) {
            active = false;
        }
    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY()- (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.GREEN);
    }
}
