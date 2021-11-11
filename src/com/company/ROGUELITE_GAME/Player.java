package com.company.ROGUELITE_GAME;

import com.company.engine.Buffer;
import com.company.engine.controls.MovementController;
import com.company.engine.entities.ControllableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.entities.StaticEntity;
import com.company.engine.math.shapes.Rectangle;

public class Player extends ControllableEntity {

    public Player(MovementController controller) {
        super(controller);
        setShape(new Rectangle(32f,32f));
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void onCollide(MovableEntity other) {

    }

    @Override
    public void onColliding(StaticEntity other) {

    }

    @Override
    public void draw(Buffer buffer) {

    }
}
