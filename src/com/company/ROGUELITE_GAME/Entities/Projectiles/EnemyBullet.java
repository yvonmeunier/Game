package com.company.ROGUELITE_GAME.Entities.Projectiles;

import com.company.ROGUELITE_GAME.Blockade;
import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.NPCs.NPC;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;
import com.company.engine.sound.Sound;

import java.awt.*;

public class EnemyBullet extends Projectile {

    private final int lifespan = 120;

    public EnemyBullet(Point coordinates, Vector2D velo) {
        setCoordinates(coordinates);
        setHurtBox(new Circle(8));
        setSpeed(3);
        setAccelerationRate(0.3f);
        setCurrentVector(velo);
        setCurrentVector(getCurrentVector().multiplyVector(getSpeed()));
        CollidableRepository.getInstance().getEntities().add(this);
        MovableRepository.getInstance().queueNewEntity(this);
        lifeTimer = 1;
        damage = 3;
    }

    @Override
    public void update() {
        capSpeed();
        move();
        super.update();
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public boolean onNPCHit(NPC npc) {
        return false;
    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Player || other instanceof Blockade) {
            new Sound("impact").play();
            active = false;
        }
    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY()- (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.RED);
    }

}
