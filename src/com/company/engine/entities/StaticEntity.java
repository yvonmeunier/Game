package com.company.engine.entities;

import com.company.engine.Buffer;
import com.company.engine.math.Point;

public abstract class StaticEntity {

    protected Point coordinates;

    public abstract void draw(Buffer buffer);

}
