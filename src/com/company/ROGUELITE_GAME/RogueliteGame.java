package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.engine.Buffer;
import com.company.engine.Game;
import com.company.engine.RenderingEngine;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.entities.StaticEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;

public class RogueliteGame extends Game {
    GamePad gamePad;
    Player player;
    Blockade blockade;
    @Override
    public void init() {

        gamePad = new GamePad();
        player = new Player(gamePad,new Point(0,0));
        blockade = new Blockade(new Point(100,100));
        CollidableRepository.getInstance().getEntities().add(player);
        MovableRepository.getInstance().getEntities().add(player);
        CollidableRepository.getInstance().getEntities().add(blockade);
        RenderingEngine.getInstance().getScreen().showCrossHair();
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

        for (CollidableEntity entity: MovingRepository.getInstance().getEntities()) {
            for (CollidableEntity other: CollidableRepository.getInstance().getEntities()) {
                if (CollisionManager.collisionCheck(entity,other) && entity != other) {
                    System.out.println("BABABOE");
                }
            }
        }

    }

    @Override
    public void draw(Buffer buffer) {
        player.draw(buffer);
        blockade.draw(buffer);
    }
}
