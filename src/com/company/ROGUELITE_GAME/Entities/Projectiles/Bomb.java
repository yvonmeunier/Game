package com.company.ROGUELITE_GAME.Entities.Projectiles;

import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.NPCs.NPC;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;

import java.awt.*;

public class Bomb extends Projectile {
    public Bomb(Point coordinates) {
        setCoordinates(coordinates);
        setHurtBox(new Circle(5));
        setCurrentVector(Vector2D.ZERO);
        this.lifespan = 240;
        this.damage = 500;
        CollidableRepository.getInstance().getEntities().add(this);
        MovableRepository.getInstance().queueNewEntity(this);
        MovingRepository.getInstance().getEntities().add(this);

        lifeTimer = 1;
    }

    @Override
    public boolean onNPCHit(NPC npc) {
        if (lifeTimer < 239) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onPlayerCollide(Player player) {
        if (lifeTimer < 239) {
            return false;
        }
        return true;
    }

    @Override
    public final void onCollide(MovableEntity other) {

    }

    @Override
    public void update() {
        super.update();
        if (lifeTimer > 239) {
            setHurtBox(new Circle(48f));
        }
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.RED);
    }
}
