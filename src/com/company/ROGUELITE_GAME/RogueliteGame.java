package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Doors.UpDoor;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovingRepository;
import com.company.ROGUELITE_GAME.WorldGen.Floor;
import com.company.ROGUELITE_GAME.WorldGen.FloorGenerator;
import com.company.ROGUELITE_GAME.WorldGen.Room;
import com.company.engine.Buffer;
import com.company.engine.Game;
import com.company.engine.GameTime;
import com.company.engine.RenderingEngine;
import com.company.engine.controls.MouseController;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;

import java.awt.*;
import java.awt.event.MouseAdapter;

public class RogueliteGame extends Game {

    private int width;
    private int height;
    private int level;

    private GamePad gamePad;
    private Player player;
    private Blockade blockade;
    private MouseController mouse;
    private Camera camera;
    private HUD hud;
    private Floor currentFloor;
    private Room currentRoom;

    @Override
    public void init() {

        gamePad = new GamePad();
        mouse = new MouseController();
        player = new Player(gamePad,mouse,new Point(640,360));
        //blockade = new Blockade(new Point(100,100));
        UpDoor door = new UpDoor(new Point(600,300));
        camera = Camera.getInstance();
        hud = HUD.getInstance();
        level = 1;
        width = 20;
        height = 20;
        currentFloor = FloorGenerator.getInstance().generateFloor(width,height,level);
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
        hud.update(player);
        camera.update(player);
    }

    @Override
    public void draw(Buffer buffer) {
        camera.draw(buffer);
        hud.draw(buffer);
    }
}
