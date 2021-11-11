package com.company.ROGUELITE_GAME;

import com.company.engine.RenderingEngine;
import com.company.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private int consumableKey = KeyEvent.VK_Q;
    private int bombKey = KeyEvent.VK_E;
    private int activeItemKey = KeyEvent.VK_SPACE;
    private int quitKey = KeyEvent.VK_ESCAPE;

    public GamePad() {
        RenderingEngine.getInstance().addKeyListener(this);
        bindKeys(new int[]{consumableKey, bombKey, activeItemKey,quitKey});
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isConsumablePressed() {
        return isKeyPressed(consumableKey);
    }

    public boolean isBombPressed() {
        return isKeyPressed(bombKey);
    }

    public boolean isActiveItemPressed() {
        return isKeyPressed(activeItemKey);
    }
}
