package com.company.ROGUELITE_GAME;

import com.company.engine.Buffer;
import com.company.engine.Game;
import com.company.engine.math.Point;

public class RogueliteGame extends Game {
    GamePad gamePad;
    Player player;

    @Override
    public void init() {
        gamePad = new GamePad();
        player = new Player(gamePad,new Point(32,32));
    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }

        player.update();

    }

    @Override
    public void draw(Buffer buffer) {
        player.draw(buffer);
    }
}
