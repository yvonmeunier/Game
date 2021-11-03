package com.company.engine.math.shapes;

import com.company.engine.math.Point;

public class Rectangle extends Shape {

    private float width;
    private float height;

    public Rectangle(float width, float height, Point coord) {
        this.width = width;
        this.height = height;
        setX(coord.getX());
        setY(coord.getY());
    }

    public Rectangle(float width, float height, float x, float y) {
        this.width = width;
        this.height = height;
        setX(x);
        setY(y);
    }
}
