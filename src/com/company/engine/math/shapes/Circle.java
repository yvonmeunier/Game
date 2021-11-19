package com.company.engine.math.shapes;

import com.company.engine.math.Point;

public class Circle extends Shape {

    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }


    private float getDiameter() {
        return radius * 2;
    }

    @Override
    float getWidth() {
        return 0;
    }

    @Override
    float getHeight() {
        return 0;
    }

    @Override
    Shape getShape() {
        return null;
    }
}
