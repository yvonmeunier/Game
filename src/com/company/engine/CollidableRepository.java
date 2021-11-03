package com.company.engine;

import com.company.engine.entities.StaticEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollidableRepository implements Iterable<StaticEntity> {

    private static CollidableRepository instance;
    private final List<StaticEntity> registeredEntities;

    public static CollidableRepository getInstance() {
        if (instance == null) {
            instance = new CollidableRepository();
        }
        return instance;
    }

    public int count() {
        return registeredEntities.size();
    }

    public void registerEntities(Collection<StaticEntity> entities) {
        registeredEntities.addAll(entities);
    }

    public void unregisterEntities(Collection<StaticEntity> entities) {
        registeredEntities.removeAll(entities);
    }

    public void registerEntity(StaticEntity entity) {
        registeredEntities.add(entity);
    }

    public void unregisterEntity(StaticEntity entity) {
        registeredEntities.remove(entity);
    }

    private CollidableRepository() {
        registeredEntities = new ArrayList<>();
    }

    @Override
    public Iterator<StaticEntity> iterator() {
        return registeredEntities.iterator();
    }
}
