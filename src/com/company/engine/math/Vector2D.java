package com.company.engine.math;

public class Vector2D {

    public static final Vector2D ZERO = new Vector2D();
    public static final Vector2D ONE = new Vector2D(1);
    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(float xy) {
        this.x = xy;
        this.y = xy;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public Vector2D addVector(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    public Vector2D addVector(float xy) {
        return new Vector2D(this.x + xy, this.y + xy);
    }

    public Vector2D subVector(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    public Vector2D subVector(float xy) {
        return new Vector2D(this.x - xy, this.y - xy);
    }

    public Vector2D multiplyVector(Vector2D vector) {
        return new Vector2D(this.x * vector.x, this.y * vector.y);
    }

    public Vector2D multiplyVector(float xy) {
        return new Vector2D(this.x * xy, this.y * xy);
    }

    public Vector2D divideVector(Vector2D vector) {
        return new Vector2D(this.x / vector.x, this.y / vector.y);
    }

    public Vector2D divideVector(float xy) {
        return new Vector2D(this.x / xy, this.y / xy);
    }

    public static Vector2D normalizeVector(Vector2D target) {
        return new Vector2D(target.x - (float) Math.floor(target.x),target.y - (float) Math.floor(target.y));
    }

    public static Vector2D lerp(Vector2D start, Vector2D end, float percentage) {
        return new Vector2D((start.x + percentage * (end.x - start.x)), (start.y + percentage * (end.y - start.y)));
    }

    public static float getDistanceBetweenVectors(Vector2D start, Vector2D end) {
        return (float) Math.sqrt(((end.y - start.y) * (end.y - start.y)) + ((end.x - start.x) * (end.x - start.x)));
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
