package com.company.ROGUELITE_GAME.Repositories;

import com.company.engine.entities.CollidableEntity;

import java.util.ArrayList;

public class CollidableRepository {
    private ArrayList<CollidableEntity> entities;
    private static CollidableRepository collidableRepository;

    public CollidableRepository() {
        entities = new ArrayList<>();
    }

    public static CollidableRepository getInstance() {
        if (collidableRepository == null) {
            collidableRepository = new CollidableRepository();
        }
        return collidableRepository;
    }
    public ArrayList<CollidableEntity> getEntities() {
        return entities;
    }
}
