package com.company.engine.math.shapes;

public class Rectangle extends Shape {

    private float width;
    private float height;

    public Rectangle(float width, float height) {
        this.width = width;
        this.height = height;
    }
    @Override
    public float getWidth() {
        return width;
    }
    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public Shape getShape() {
        return this;
    }
}
