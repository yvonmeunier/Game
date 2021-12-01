package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.engine.Buffer;
import com.company.engine.Game;
import com.company.engine.RenderingEngine;
import com.company.engine.controls.MouseController;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;

import java.awt.event.MouseAdapter;

public class RogueliteGame extends Game {
    GamePad gamePad;
    Player player;
    Blockade blockade;
    MouseController mouse;
    @Override
    public void init() {

        gamePad = new GamePad();
        mouse = new MouseController();
        player = new Player(gamePad,mouse,new Point(0,100));
        blockade = new Blockade(new Point(100,100));

        CollidableRepository.getInstance().getEntities().add(player);
        CollidableRepository.getInstance().getEntities().add(blockade);
        MovableRepository.getInstance().getEntities().add(player);
        RenderingEngine.getInstance().getScreen().showCrossHair();
    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() throws CloneNotSupportedException {
        if (gamePad.isQuitPressed()) {
            stop();
        }

        player.update();
        for (MovableEntity entity: MovingRepository.getInstance().getEntities()) {
            for (CollidableEntity other: CollidableRepository.getInstance().getEntities()) {
                if (CollisionManager.isGoingToCollide( entity,other) && entity != other) {
                    entity.onColliding(other);
                    other.onCollide(entity);
                }
                if (CollisionManager.hasPhasedTrough(entity,other) && entity != other) {
                    entity.onPhasing(other);
                }
            }
        }
        player.move();
    }

    @Override
    public void draw(Buffer buffer) {

        blockade.draw(buffer);
        player.draw(buffer);
    }
}
