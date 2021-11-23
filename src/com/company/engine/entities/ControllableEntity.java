package com.company.engine.entities;

import com.company.engine.controls.MovementController;

public abstract class ControllableEntity extends MovableEntity {

    private MovementController controller;

    public ControllableEntity(MovementController controller) {
        this.controller = controller;
    }

    public MovementController getController() {
        return controller;
    }

}
