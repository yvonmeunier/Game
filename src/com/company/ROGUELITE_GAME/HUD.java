package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.GameTime;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.entities.StaticEntity;
import com.company.engine.math.Point;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends MovableEntity {

    private static HUD instance;
    private ArrayList<StaticEntity> HUDElements;
    private MovableEntity followedEntity;


    public static HUD getInstance() {
        if (instance == null) {
            instance = new HUD();
        }
        return instance;
    }

    private HUD() {
        HUDElements = new ArrayList<>();
        CollidableRepository.getInstance().getEntities().add(this);
    }

    public void update(MovableEntity target) {
        followedEntity = target;
        setCoordinates(new Point(followedEntity.getCoordinates().getX() - 606,followedEntity.getCoordinates().getY() - 328));
    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawText(String.valueOf(GameTime.getCurrentFps()),16,16,Color.cyan);
    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {

    }

    @Override
    public void onPhasing(CollidableEntity other) throws CloneNotSupportedException {

    }
}
