package com.company.ROGUELITE_GAME.Repositories;

import com.company.engine.entities.MovableEntity;

import java.util.ArrayList;

public class MovableRepository {
    private ArrayList<MovableEntity> movableEntities;
    private static MovableRepository instance;

    public MovableRepository() {
        movableEntities = new ArrayList<>();
    }

    public static MovableRepository getInstance() {
        if (instance == null) {
            instance = new MovableRepository();
        }
        return instance;
    }

    public ArrayList<MovableEntity> getEntities() {
        return movableEntities;
    }
}
