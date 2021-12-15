package com.company.ROGUELITE_GAME.Entities.NPCs;

import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Projectile;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;

public class Obaoma extends NPC {

    public Obaoma() {

    }

    @Override
    public boolean onProjectileCollide(Projectile projectile) {
        return super.onProjectileCollide(projectile);
    }

    @Override
    public boolean onPlayerCollide(Player player) {
        return super.onPlayerCollide(player);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        super.onColliding(other);
    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {
        super.onPhasing(other);
    }

    @Override
    public void draw(Buffer buffer) {
        super.draw(buffer);
    }

    @Override
    public void loadSprites() {
        super.loadSprites();
    }
}
