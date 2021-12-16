package com.company.ROGUELITE_GAME.Entities.NPCs;

import com.company.ROGUELITE_GAME.Blockade;
import com.company.ROGUELITE_GAME.Camera;
import com.company.ROGUELITE_GAME.Entities.Player;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Bullet;
import com.company.ROGUELITE_GAME.Entities.Projectiles.Projectile;
import com.company.ROGUELITE_GAME.Repositories.CollidableRepository;
import com.company.ROGUELITE_GAME.Repositories.MovableRepository;
import com.company.engine.Buffer;
import com.company.engine.entities.CollidableEntity;
import com.company.engine.math.CollisionManager;
import com.company.engine.math.Point;
import com.company.engine.math.Vector2D;
import com.company.engine.math.shapes.Circle;
import com.company.engine.sound.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Duke extends NPC {

    public Image flyAnimation;
    public Image[] attackAnimation;
    private Random rnd;

    private int attackTimer = 500;
    private int currentFrame = 3;

    private BufferedImage sprites;

    public Duke(Point coord) {
        setCoordinates(coord);
        setHurtBox(new Circle(40f));
        setCurrentVector(new Vector2D());
        rnd = new Random();
        hp = 2500;
        loadSprites();
        MovableRepository.getInstance().getEntities().add(this);
        CollidableRepository.getInstance().getEntities().add(this);
    }


    public void update(ArrayList<NPC> npcs) {
        super.update();
        attackTimer--;
        if(attackTimer <= 100) {
            currentFrame = 0;
        }
        if(attackTimer <= 20) {
            currentFrame = 1;
        }
        if(attackTimer <= 10) {
            currentFrame = 2;
        }
        if(attackTimer == 0) {
            currentFrame = -1;
            attackTimer = 200 + rnd.nextInt(200);
            new Sound("spawn_fly").play();
            int flyAmnt = rnd.nextInt(6);
            for (int i = 0; i < flyAmnt; i++) {
                if(rnd.nextInt(100) == 0) {
                    Amogus amogus = new Amogus(new Point(getCoordinates().getX() - ((rnd.nextBoolean()) ? -rnd.nextInt(15) : rnd.nextInt(15)), getCoordinates().getY() + rnd.nextInt(10)));
                    amogus.hp = 10;
                    npcs.add(amogus);
                    continue;
                }
                Fly fly = new Fly(new Point(getCoordinates().getX() - ((rnd.nextBoolean()) ? -rnd.nextInt(15) : rnd.nextInt(15)), getCoordinates().getY() + rnd.nextInt(10)));
                if(rnd.nextInt(10) == 0) {
                    fly.hp = 50;
                }
                npcs.add(fly);
            }
        }
    }

    @Override
    public void onColliding(CollidableEntity other) throws CloneNotSupportedException {
        if (other instanceof Blockade) {
            Vector2D currentVector = getCurrentVector();
            Vector2D normalized = Vector2D.normalizeVector(currentVector);
            Vector2D collision = Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f);
            setCurrentVector(getCurrentVector().subVector(Vector2D.lerp(getCurrentVector(), CollisionManager.resolve(this, other), 0.0001f)));
        }
        if(other instanceof Player player) {
            if(player.isDashing()) {
                player.setHP(player.getHp() - 1);
            }
        }
    }

    @Override
    public void draw(Buffer buffer) {
        super.draw(buffer);
        buffer.drawCircle(getCoordinates().getX() - (this.getHurtBox().getWidth() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() / 2 - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY(), getHurtBox().getWidth() / 2, Color.GREEN);

        if(attackTimer < 100 && currentFrame != -1) {
            buffer.drawImage(attackAnimation[currentFrame], new Point(getCoordinates().getX() - (this.getHurtBox().getWidth() - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY()));
            return;
        }
        buffer.drawImage(flyAnimation, new Point(getCoordinates().getX() - (this.getHurtBox().getWidth() - Camera.getInstance().getFollowedEntity().getHurtBox().getWidth() / 2) - Camera.getInstance().getCoordinates().getX(), getCoordinates().getY() - (this.getHurtBox().getHeight() - Camera.getInstance().getFollowedEntity().getHurtBox().getHeight() / 2) - Camera.getInstance().getCoordinates().getY()));
    }

    @Override
    public void loadSprites() {
        super.loadSprites();
        try {
            sprites = ImageIO.read(this.getClass().getResourceAsStream("/LayoutEntities/Dynamic/Enemies/duke.png"));

            flyAnimation = sprites.getSubimage(0, 0, 86, 65).getScaledInstance(86 * 2, 65 * 2, Image.SCALE_FAST);
            attackAnimation = new Image[3];

            attackAnimation[0]  = sprites.getSubimage(88, 0, 86, 65).getScaledInstance(86 * 2, 65 * 2, Image.SCALE_FAST);
            attackAnimation[1] = sprites.getSubimage(0, 67, 86, 65).getScaledInstance(86 * 2, 65 * 2, Image.SCALE_FAST);
            attackAnimation[2] = sprites.getSubimage(88, 67, 86, 65).getScaledInstance(86 * 2, 65 * 2, Image.SCALE_FAST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
