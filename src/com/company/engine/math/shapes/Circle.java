package com.company.engine.math.shapes;

import com.company.engine.math.Point;

public class Circle extends Shape {

    private float radius;

    public Circle(float radius, Point coord) {
        this.radius = radius;
        this.x = coord.getX();
        this.y = coord.getY();
    }

    public Circle(float radius, float x, float y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    private float getDiameter() {
        return radius * 2;
    }

}
