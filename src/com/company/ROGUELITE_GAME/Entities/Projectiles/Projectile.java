package com.company.ROGUELITE_GAME.Entities.Projectiles;

import com.company.ROGUELITE_GAME.Entities.NPCs.NPC;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;

public class Projectile extends MovableEntity {
    protected int lifespan = 120;
    protected int lifeTimer;


    protected boolean friendly = false; // Can hurt the enemy
    protected boolean hostile = false; // Can hurt the player
    protected int damage;

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isHostile() {
        return hostile;
    }

    public int getDamage() {
        return damage;
    }

    public boolean onNPCHit(NPC npc) {
        return true;
    }

    public boolean onPlayerCollide(Player player) {
        return true;
    }

    @Override
    public void update() {
        if (lifeTimer >= lifespan) {
            active = false;
        }
        lifeTimer++;
    }

    @Override
    public void onCollide(MovableEntity other) {

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

    @Override
    public void loadSprites() {

    }
}
