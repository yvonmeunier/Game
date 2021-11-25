package com.company.ROGUELITE_GAME.Repositories;

import com.company.engine.entities.CollidableEntity;

import java.util.ArrayList;

public class CollidableRepository {
    private ArrayList<CollidableEntity> entities;
    private static CollidableRepository instance;

    public CollidableRepository() {
        entities = new ArrayList<>();
    }

    public static CollidableRepository getInstance() {
        if (instance == null) {
            instance = new CollidableRepository();
        }
        return instance;
    }

    public ArrayList<CollidableEntity> getEntities() {
        return entities;
    }
}
