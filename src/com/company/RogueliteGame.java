package com.company;

import com.company.engine.Buffer;
import com.company.engine.Game;

public class RogueliteGame extends Game {
    GamePad gamePad;
    Player player;

    @Override
    public void init() {
        gamePad = new GamePad();
        player = new Player(gamePad);
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

    }
}
