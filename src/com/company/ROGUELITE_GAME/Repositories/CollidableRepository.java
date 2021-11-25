package com.company.ROGUELITE_GAME.Repositories;

import com.company.engine.entities.StaticEntity;

import java.util.ArrayList;

public class CollidableRepository {
    ArrayList<StaticEntity> entities;
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
}
