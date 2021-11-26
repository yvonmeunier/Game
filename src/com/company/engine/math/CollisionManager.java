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

    public static boolean hasPhasedTrough(MovableEntity a, CollidableEntity b) throws CloneNotSupportedException {

        MovableEntity emulatedA = (MovableEntity) a.clone();
        CollidableEntity emulatedB = (CollidableEntity) b.clone();
        if (emulatedA.getHurtBox() instanceof Circle) {
            emulatedA.setHurtBox(new Rectangle( ((Circle) emulatedA.getHurtBox()).getRadius() * 2,((Circle) emulatedA.getHurtBox()).getRadius() * 2));
        }
        if (emulatedB.getHurtBox() instanceof Circle) {
            emulatedB.setHurtBox(new Rectangle( ((Circle) emulatedB.getHurtBox()).getRadius() * 2,((Circle) emulatedB.getHurtBox()).getRadius() * 2));
        }



        return false;

    }

    public static Vector2D resolve(MovableEntity a, CollidableEntity b) throws CloneNotSupportedException {
        MovableEntity emulatedA = (MovableEntity) a.clone();
        emulatedA.move();
        if (a.getHurtBox() instanceof Rectangle && b.getHurtBox() instanceof Rectangle) {
            return resolveRectVSRect(emulatedA, b);
        }
        if ((a.getHurtBox() instanceof Circle && b.getHurtBox() instanceof Rectangle) || (a.getHurtBox() instanceof Rectangle && b.getHurtBox() instanceof Circle)) {
            return Vector2D.ZERO;
        }
        if (a.getHurtBox() instanceof Circle && b.getHurtBox() instanceof Circle) {
            return resolveCircVSCirc(emulatedA, b);
        }
        return Vector2D.ZERO;
    }

    public static Vector2D resolvePhasing(MovableEntity a, CollidableEntity b) {
        return Vector2D.ZERO;
    }

    public static boolean isGoingToCollide(MovableEntity a, CollidableEntity b) throws CloneNotSupportedException {
        MovableEntity emulatedA = (MovableEntity) a.clone();
        emulatedA.move();
        return collisionCheck(emulatedA, b);
    }

    private static boolean circVSrect(CollidableEntity a, CollidableEntity b) {

        Point circDistance;

        if (a.getHurtBox() instanceof Rectangle) {
            Rectangle aRect = (Rectangle) a.getHurtBox();
            Circle bCirc = (Circle) b.getHurtBox();
            circDistance = new Point(Math.abs(b.getCoordinates().getX() - a.getCoordinates().getX()), Math.abs(b.getCoordinates().getY() - a.getCoordinates().getY()));

            if (circDistance.getX() > (aRect.getWidth() / 2 + bCirc.getRadius())) {
                return false;
            }
            if (circDistance.getY() > (aRect.getHeight() / 2 + bCirc.getRadius())) {
                return false;
            }

            if (circDistance.getX() <= (aRect.getWidth() / 2)) {
                return true;
            }

            if (circDistance.getY() <= (aRect.getHeight() / 2)) {
                return true;
            }

            float cornerDistance = ((circDistance.getX() - aRect.getWidth() / 2) * (circDistance.getX() - aRect.getWidth() / 2)) + ((circDistance.getY() - aRect.getHeight() / 2) * (circDistance.getY() - aRect.getHeight() / 2));
            return cornerDistance <= (bCirc.getRadius() * bCirc.getRadius());
        } else {
            Circle aCirc = (Circle) a.getHurtBox();
            Rectangle bRect = (Rectangle) b.getHurtBox();
            circDistance = new Point(Math.abs(a.getCoordinates().getX() - b.getCoordinates().getX()), Math.abs(a.getCoordinates().getY() - b.getCoordinates().getY()));

            if (circDistance.getX() > (bRect.getWidth() / 2 + aCirc.getRadius())) {
                return false;
            }
            if (circDistance.getY() > (bRect.getHeight() / 2 + aCirc.getRadius())) {
                return false;
            }

            if (circDistance.getX() <= (bRect.getWidth() / 2)) {
                return true;
            }

            if (circDistance.getY() <= (bRect.getHeight() / 2)) {
                return true;
            }

            float cornerDistance = ((circDistance.getX() - bRect.getWidth() / 2) * (circDistance.getX() - bRect.getWidth() / 2)) + ((circDistance.getY() - bRect.getHeight() / 2) * (circDistance.getY() - bRect.getHeight() / 2));
            return cornerDistance <= (aCirc.getRadius() * aCirc.getRadius());
        }
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

    private static Vector2D resolveRectVSRect(MovableEntity emulatedA, CollidableEntity b) {
        Rectangle aRect = (Rectangle) emulatedA.getHurtBox();
        Rectangle bRect = (Rectangle) b.getHurtBox();
        Point aMax = Point.addPoint(emulatedA.getCoordinates(), new Point(aRect.getWidth(), aRect.getHeight()));
        Point aMin = emulatedA.getCoordinates();
        Point bMax = Point.addPoint(b.getCoordinates(), new Point(bRect.getWidth(), bRect.getHeight()));
        Point bMin = b.getCoordinates();

        float dx = 0;
        float dy = 0;

        if (aMax.getX() > bMin.getX()) {
            dx -= aMax.getX() - bMin.getX();
        }
        if (aMin.getX() < bMax.getX()) {
            dx += bMax.getX() - aMin.getX();
        }
        if (aMax.getY() > bMin.getY()) {
            dy -= aMax.getY() - bMin.getY();
        }
        if (aMin.getY() < bMax.getY()) {
            dy += bMax.getY() - aMin.getY();
        }
        return new Vector2D(dx, dy);
    }

    private static Vector2D resolveCircVSCirc(MovableEntity a, CollidableEntity b) {
        Circle aCirc = (Circle) a.getHurtBox();
        Circle bCirc = (Circle) b.getHurtBox();

        float dx = a.getCoordinates().getX() - b.getCoordinates().getX();
        float dy = a.getCoordinates().getY() - b.getCoordinates().getY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return new Vector2D(aCirc.getRadius() + bCirc.getRadius() - dx, aCirc.getRadius() + bCirc.getRadius() - dy);
    }

}
