package com.company.engine.math;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 toVector() {
        return new Vector2(x, y);
    }

    public static float getDistanceBetweenPoints(Point start, Point end) {
        return (float) Math.sqrt(((end.y - start.y) * (end.y - start.y)) + ((end.x - start.x) * (end.x - start.x)));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
