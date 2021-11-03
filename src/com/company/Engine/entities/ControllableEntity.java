package com.company.Engine.entities;

import com.company.Engine.controls.MovementController;

public abstract class ControllableEntity extends MovableEntity {

    private MovementController controller;

    public ControllableEntity(MovementController controller) {

        this.controller = controller;

    }

    public void moveAccordingToController() {

        if (!controller.isMoving()) {
            return;
        }
        if (controller.isUpPressed()) {
            moveUp();
        }
        if (controller.isDownPressed()) {
            moveDown();
        }
        if (controller.isLeftPressed()) {
            moveLeft();
        }
        if (controller.isRightPressed()) {
            moveRight();
        }

    }

}
