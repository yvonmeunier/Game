package com.company.ROGUELITE_GAME;

import com.company.ROGUELITE_GAME.Entities.NPCs.*;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.ROGUELITE_GAME.WorldGen.Floor;
import com.company.ROGUELITE_GAME.WorldGen.FloorGenerator;
import com.company.ROGUELITE_GAME.WorldGen.Room;
import com.company.engine.Buffer;
import com.company.engine.Game;
import com.company.engine.RenderingEngine;
import com.company.engine.controls.MouseController;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Rectangle;
import com.company.engine.sound.Sound;

import java.util.ArrayList;
import java.util.Random;

public class RogueliteGame extends Game {

    private int width;
    private int height;
    private int level;
    private int enemyLeft;

    private GamePad gamePad;
    private Player player;
    private MouseController mouse;
    private Camera camera;
    private HUD hud;
    private Floor currentFloor;
    private Room currentRoom;
    private ArrayList<Blockade> walls;
    private ArrayList<NPC> npcs;

    private Random rnd;

    @Override
    public void init() {
        rnd = new Random();
        gamePad = new GamePad();
        mouse = new MouseController();
        player = new Player(gamePad,mouse,new Point(640,360));
        camera = Camera.getInstance();
        hud = HUD.getInstance();
        level = 1;
        width = 20;
        height = 20;
        currentFloor = FloorGenerator.getInstance().generateFloor(width,height,level);
        RenderingEngine.getInstance().getScreen().showCrossHair();
        //RenderingEngine.getInstance().getScreen().fullscreen();
        currentRoom = currentFloor.getRoomById(width * (height / 2) + (width / 2));
        npcs = new ArrayList<>();
        walls = new ArrayList<>();
        npcs.add(new Fly(new Point(52,level + 52)));
        walls.add(new Blockade(new Point(0,0),new Rectangle(3224,72)));
        walls.add(new Blockade(new Point(0,970),new Rectangle(3224,72)));
        walls.add(new Blockade(new Point(0,75),new Rectangle(72,3224)));
        walls.add(new Blockade(new Point(1582,75),new Rectangle(72,3224)));
        //new Sound("soundtrack").playWithLoop();
    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() throws CloneNotSupportedException {
        enemyLeft = npcs.size();
        if (gamePad.isQuitPressed()) {
            stop();
        }

        for (MovableEntity entity: MovableRepository.getInstance().getEntities()) {
            if (entity instanceof Fly fly) {
                fly.update(player);
            }
            if (entity instanceof Obaoma obaoma) {
                obaoma.update(player);
            }
            if (entity instanceof Amogus amogus) {
                amogus.update(player);
            }
            if (entity instanceof Duke duke) {
                duke.update(npcs);
            }
            entity.update();
        }

        for (MovableEntity entity: MovableRepository.getInstance().getEntities()) {
            for (CollidableEntity other: CollidableRepository.getInstance().getEntities()) {
                if (CollisionManager.isGoingToCollide(entity,other) && entity != other) {
                    entity.onColliding(other);
                    other.onCollide(entity);
                }
                if (CollisionManager.hasPhasedTrough(entity,other) && entity != other) {
                    entity.onPhasing(other);
                }
            }
        }
        for (MovableEntity entity: MovableRepository.getInstance().getEntities()) {
            entity.move();
        }
        hud.update(player,level);
        camera.update(player);

        MovableRepository.getInstance().getEntities().removeIf(movableEntity -> !movableEntity.isActive());
        CollidableRepository.getInstance().getEntities().removeIf(collidableEntity -> !collidableEntity.isActive());
        MovableRepository.getInstance().registerQueuedEntity();
        npcs.removeIf(npc -> !npc.isActive());
        if (enemyLeft == 0) {
            level++;
            player.setHP(player.getHp() + (level/2));
            for (int i = 0; i < level; i++) {

                npcs.add(new Fly(new Point(rnd.nextInt(1280 - 104),rnd.nextInt(720 - 104))));
                if ((i % 2 == 0) && (level >= 2)) {
                    npcs.add(new Obaoma(new Point(rnd.nextInt(1280 - 104),rnd.nextInt(720 - 104))));
                }
                if ((i % 5 == 0) && (level >= 5)) {
                    npcs.add(new Amogus(new Point(rnd.nextInt(1280 - 104),rnd.nextInt(720 - 104))));
                }
                if((level >= 10) && (i % 10 == 0)) {
                    npcs.add(new Duke(new Point(rnd.nextInt(1280 - 300) + 100,rnd.nextInt(720 - 300) + 100)));
                }
            }
        }
        if (player.getHp() <= 0) {
            new Sound("bonk").play();
            player.setHP(10);
            level = 1;
        }
    }

    @Override
    public void draw(Buffer buffer) {
        camera.draw(buffer, currentRoom.getRoomImage());
        hud.draw(buffer);
    }
}
