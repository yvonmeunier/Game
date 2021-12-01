package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Rectangle;

public class Camera extends MovableEntity {
    private MovableEntity followedEntity;
    private static Camera instance;

    public static Camera getInstance() {
        if (instance == null){
            instance = new Camera();
        }
        return instance;
    }

    private Camera () {
        setHurtBox(new Rectangle(1280,720));
    }

    public void update(MovableEntity target) {
        followedEntity = target;
        setCoordinates(new Point(followedEntity.getCoordinates().getX() - 606,followedEntity.getCoordinates().getY() - 328));
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
    public void draw (Buffer buffer) {
        for (CollidableEntity entity : CollidableRepository.getInstance().getEntities()) {
            if (CollisionManager.collisionCheck(entity,this)) {
                entity.draw(buffer);
            }
        }
    }
}
