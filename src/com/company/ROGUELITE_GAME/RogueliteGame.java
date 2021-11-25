package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.engine.Buffer;
import com.company.engine.Game;
import com.company.engine.RenderingEngine;
import com.company.engine.math.Point;

public class RogueliteGame extends Game {
    GamePad gamePad;
    Player player;
    CollidableRepository collidables;

    @Override
    public void init() {

        gamePad = new GamePad();
        player = new Player(gamePad,new Point(0,0));
        collidables = CollidableRepository.getInstance();
        RenderingEngine.getInstance().getScreen().showCrossHair();
        //RenderingEngine.getInstance().getScreen().fullscreen();
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
