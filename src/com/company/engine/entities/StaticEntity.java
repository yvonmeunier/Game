package com.company.engine.entities;

import com.company.engine.Buffer;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Shape;

public abstract class StaticEntity {

    private Point coordinates;
    private Shape shape;

    public void setShape(Shape shape) {

        this.shape = shape;

    }

    public abstract void draw(Buffer buffer);

}
