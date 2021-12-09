package com.company.ROGUELITE_GAME.Repositories;

import com.company.engine.entities.MovableEntity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class MovableRepository {
    public static ArrayList<MovableEntity> movableEntities;
    private static MovableRepository instance;

    public Queue<MovableEntity> movableEntityQueue;

    public MovableRepository() {
        movableEntityQueue = new ArrayDeque<>();
        movableEntities = new ArrayList<>();
    }

    public static MovableRepository getInstance() {
        if (instance == null) {
            instance = new MovableRepository();
        }
        return instance;
    }

    public void queueNewEntity(MovableEntity me) {
        movableEntityQueue.add(me);
    }

    public void registerQueuedEntity() {
        while (movableEntityQueue.size() != 0) {
            movableEntities.add(movableEntityQueue.remove());
        }
    }

    public ArrayList<MovableEntity> getEntities() {
        return movableEntities;
    }
}
