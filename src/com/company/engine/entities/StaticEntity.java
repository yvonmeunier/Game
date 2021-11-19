package com.company.engine.entities;

import com.company.engine.Buffer;
import com.company.engine.math.Point;
import com.company.engine.math.shapes.Shape;

public abstract class StaticEntity {

    private Point coordinates;

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public abstract void draw(Buffer buffer);

}
