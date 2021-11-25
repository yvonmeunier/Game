package com.company.engine.math;

import com.company.engine.entities.CollidableEntity;
import com.company.engine.entities.MovableEntity;
import com.company.engine.math.shapes.Circle;
import com.company.engine.math.shapes.Rectangle;

public class CollisionManager {

    public static boolean collisionCheck(CollidableEntity a, CollidableEntity b) {
        if (a.getHurtBox() instanceof Rectangle && b.getHurtBox() instanceof Rectangle) {
            return rectVSrect(a, b);
        }
        if ((a.getHurtBox() instanceof Circle && b.getHurtBox() instanceof Rectangle) || (a.getHurtBox() instanceof Rectangle && b.getHurtBox() instanceof Circle)) {
            return circVSrect(a, b);
        }
        if (a.getHurtBox() instanceof Circle && b.getHurtBox() instanceof Circle) {
            return circVScirc(a, b);
        }
        return false;
    }

    private static boolean circVSrect(CollidableEntity a, CollidableEntity b) {
        return false;
    }

    private static boolean rectVSrect(CollidableEntity a, CollidableEntity b) {
        Rectangle aRect = (Rectangle) a.getHurtBox();
        Rectangle bRect = (Rectangle) b.getHurtBox();
        Point aMin = a.getCoordinates();
        Point aMax = Point.addPoint(a.getCoordinates(), new Point(aRect.getWidth(), aRect.getHeight()));
        Point bMin = b.getCoordinates();
        Point bMax = Point.addPoint(b.getCoordinates(), new Point(bRect.getWidth(), bRect.getHeight()));

        if (aMax.getX() < bMin.getX() || aMin.getX() > bMax.getX()) return false;
        if (aMax.getY() < bMin.getY() || aMin.getY() > bMax.getY()) return false;

        return true;
    }

    private static boolean circVScirc(CollidableEntity a, CollidableEntity b) {
        Circle aCirc = (Circle) a.getHurtBox();
        Circle bCirc = (Circle) b.getHurtBox();

        float dx = a.getCoordinates().getX() - b.getCoordinates().getX();
        float dy = a.getCoordinates().getY() - b.getCoordinates().getY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < aCirc.getRadius() + bCirc.getRadius();
    }

    public static boolean isGoingToCollide(MovableEntity a, CollidableEntity b) throws CloneNotSupportedException {
        MovableEntity emulatedA = (MovableEntity) a.clone();
        emulatedA.move();
        return collisionCheck(emulatedA, b);
    }

    public static Vector2D resolve(MovableEntity a, CollidableEntity b) throws CloneNotSupportedException {
        MovableEntity emulatedA = (MovableEntity) a.clone();
        emulatedA.move();
        if (a.getHurtBox() instanceof Rectangle && b.getHurtBox() instanceof Rectangle) {
            return resolveRectvsRect(emulatedA,b);
        }
        if ((a.getHurtBox() instanceof Circle && b.getHurtBox() instanceof Rectangle) || (a.getHurtBox() instanceof Rectangle && b.getHurtBox() instanceof Circle)) {
           return Vector2D.ZERO;
        }
        if (a.getHurtBox() instanceof Circle && b.getHurtBox() instanceof Circle) {
            return resolveCircvsCirc(emulatedA,b);
        }
        return Vector2D.ZERO;
    }

    private static Vector2D resolveRectvsRect(MovableEntity emulatedA, CollidableEntity b) {

        return Vector2D.ZERO;

    }

    private static Vector2D resolveCircvsCirc(MovableEntity a, CollidableEntity b) {
        return Vector2D.ZERO;
    }

}
