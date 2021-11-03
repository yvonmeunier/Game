package com.company.engine.math;

public class Vector2 {

    public static final Vector2 ZERO = new Vector2();
    public static final Vector2 ONE = new Vector2(1);
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(float xy) {
        this.x = xy;
        this.y = xy;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public Vector2 addVector(Vector2 vector) {
        return new Vector2(this.x + vector.x, this.y + vector.y);
    }

    public Vector2 addVector(float xy) {
        return new Vector2(this.x + xy, this.y + xy);
    }

    public Vector2 subVector(Vector2 vector) {
        return new Vector2(this.x - vector.x, this.y - vector.y);
    }

    public Vector2 subVector(float xy) {
        return new Vector2(this.x - xy, this.y - xy);
    }

    public Vector2 multiplyVector(Vector2 vector) {
        return new Vector2(this.x * vector.x, this.y * vector.y);
    }

    public Vector2 multiplyVector(float xy) {
        return new Vector2(this.x * xy, this.y * xy);
    }

    public Vector2 divideVector(Vector2 vector) {
        return new Vector2(this.x / vector.x, this.y / vector.y);
    }

    public Vector2 divideVector(float xy) {
        return new Vector2(this.x / xy, this.y / xy);
    }

    public static Vector2 normalizeVector(Vector2 target) {
        return new Vector2(target.x - (float) Math.floor(target.x),target.y - (float) Math.floor(target.y));
    }

    public static Vector2 lerp(Vector2 start, Vector2 end, float percentage) {
        return new Vector2((start.x + percentage * (end.x - start.x)), (start.y + percentage * (end.y - start.y)));
    }

    public static float getDistanceBetweenVectors(Vector2 start, Vector2 end) {
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
