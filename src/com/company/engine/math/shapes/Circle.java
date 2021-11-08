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

}
