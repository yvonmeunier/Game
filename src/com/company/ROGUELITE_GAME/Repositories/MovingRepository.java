package com.company.ROGUELITE_GAME.Repositories;

import com.company.engine.entities.MovableEntity;

import java.util.ArrayList;

public class MovingRepository {
    private ArrayList<MovableEntity> entities;
    private static MovingRepository movingRepository;

    public MovingRepository() {
        entities = new ArrayList<>();
    }

    public static MovingRepository getInstance() {
        if (movingRepository == null) {
            movingRepository = new MovingRepository();
        }
        return movingRepository;
    }

    public ArrayList<MovableEntity> getEntities() {
        return entities;
    }
}
