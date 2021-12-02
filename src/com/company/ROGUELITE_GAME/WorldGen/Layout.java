package com.company.ROGUELITE_GAME.WorldGen;

import com.company.ROGUELITE_GAME.Door;
import com.company.engine.entities.StaticEntity;

import java.util.List;

public class Layout {

    private List<StaticEntity> entities;
    public boolean[] doors;

    public Layout(List<StaticEntity> entities) {
     this.entities = entities;
        for (StaticEntity entity:entities) {
            if (entity instanceof Door) {
                if (((Door) entity).getDirection() == 0) {
                    doors[0] = true;
                }
                if (((Door) entity).getDirection() == 1) {
                    doors[1] = true;
                }
                if (((Door) entity).getDirection() == 2) {
                    doors[2] = true;
                }
                if (((Door) entity).getDirection() == 3) {
                    doors[3] = true;
                }
            }
        }
    }
}
