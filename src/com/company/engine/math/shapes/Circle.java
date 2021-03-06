package com.company.engine.math.shapes;

public class Circle extends Shape {

    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public float getDiameter() {
        return radius * 2;
    }

    @Override
    public float getWidth() {
        return 2 * radius;
    }

    @Override
    public float getHeight() {
        return 2 * radius;
    }

    @Override
    Shape getShape() {
        return this;
    }

}
