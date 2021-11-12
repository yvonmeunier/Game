package com.company.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class RenderingEngine {


    private JPanel panel;
    private BufferedImage bufferedImage;
    private int viewPortX;
    private int viewPortY;
    private String title;

    private static RenderingEngine instance;

    private Screen screen;

    public static RenderingEngine getInstance() {

        if (instance == null) {
            instance = new RenderingEngine();
        }

        return instance;
    }

    public int getViewPortX() {
        return viewPortX;
    }

    public int getViewPortY() {
        return viewPortY;
    }

    public void setViewPortY(int viewPortY) {
        this.viewPortY = viewPortY;
    }

    public String setTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setViewPortX(int viewPortX) {
        this.viewPortX = viewPortX;
    }

    private RenderingEngine() {

        initializeScreen();
        initializePanel();

    }

    public Buffer getRenderingBuffer() {
        bufferedImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHints(getOptimalRenderingHints());
        return new Buffer(graphics);
    }

    public void renderBufferOnScreen() {
        Graphics2D graphics = (Graphics2D) panel.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, panel);
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }

    public void start() {
        screen.start();
    }

    public void stop() {
        screen.end();
    }

    public void addKeyListener(KeyListener listener) {
        panel.addKeyListener(listener);
    }

    private void initializeScreen() {
        screen = new Screen();
        screen.setTitle("TEST");
        screen.setSize(1920,1080);
    }

    public Screen getScreen() {
        return screen;
    }

    private void initializePanel() {

        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        screen.setPanel(panel); // Add panel in JFrame

    }

    private RenderingHints getOptimalRenderingHints() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return rh;
    }

}
