package com.company.ROGUELITE_GAME.Entities.NPCs;

import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Bullet;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Projectile;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;

public class NPC extends MovableEntity {

    private int hp;
    private boolean immortal;
    private boolean invincible;
    private int iFrameDuration;

    public NPC() {

    }

    // Override this to have
    public boolean onProjectileCollide(Projectile projectile) {
        return true;
    }

    // Override this to have
    public boolean onPlayerCollide(Player player) {
        return true;
    }

    @Override
    public final void onCollide(MovableEntity other) {
        if (other instanceof Projectile projectile) {
            if (onProjectileCollide(projectile) && projectile.onNPCHit(this)) {
                hurt(projectile.getDamage());
            }
        }
        if (other instanceof Player player) {
            onPlayerCollide(player);
        }
    }

    protected void hurt(int damage) {
        if (!invincible) {
            this.hp -= damage;
            iFrameDuration = 30;
        }
        if(this.hp <= 0) {
            active = false;
        }
    }

    @Override
    public void update() {
        super.update();
        if (invincible) {
            iFrameDuration--;
        }

        if (iFrameDuration <= 0) {
            invincible = !invincible;
        }
    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void draw(Buffer buffer) {

    }
}
