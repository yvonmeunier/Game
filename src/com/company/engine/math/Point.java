package com.company.engine.math;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D toVector() {
        return new Vector2D(x, y);
    }

    public static float getDistanceBetweenPoints(Point start, Point end) {
        return (float) Math.sqrt(((end.y - start.y) * (end.y - start.y)) + ((end.x - start.x) * (end.x - start.x)));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }



}
