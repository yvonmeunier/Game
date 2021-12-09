package com.company.engine;

import com.company.engine.math.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Buffer {

    private final Graphics2D graphics;

    public Buffer(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public void drawText(String text, float x, float y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawCircle(float x, float y, float radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval((int) x, (int) y, (int) (radius * 2), (int) (radius * 2));
    }

    public void drawLine(float x, float y, float x2, float y2, Paint pain) {
        graphics.setPaint(pain);
        graphics.drawLine((int)x, (int)y, (int)x2, (int)y2);
    }

    public void drawCircle(Point coord, float radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval((int) coord.getX(), (int) coord.getY(), (int) (radius * 2), (int) (radius * 2));
    }

    public void drawRectangle(float x, float y, float width, float height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void drawRectangle(Point coord, float width, float height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect((int) coord.getX(), (int) coord.getY(), (int) width, (int) height);
    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }

    public void drawImage(Image image, Point coord) {
        drawImage(image, (int) coord.getX(), (int) coord.getY());
    }

    public BufferedImage flipImage(BufferedImage image,Point coord , double angle) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(angle,image.getWidth()/2,image.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image,null);
    }
}
