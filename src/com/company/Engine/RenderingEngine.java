package com.company.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class RenderingEngine {

    private JFrame frame;
    private JPanel panel;
    private BufferedImage bufferedImage;
    private int viewPortX;
    private int viewPortY;
    private String title;

    private static RenderingEngine instance;

    public static RenderingEngine getInstance() {

        if (instance == null){
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

        initializeFrame();
        initializePanel();

    }

    public Buffer getRenderingBuffer() {
        bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
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
        frame.setVisible(true);
    }

    public void stop() {
        frame.setVisible(false);
        frame.dispose();
    }

    public void addKeyListener(KeyListener listener) {
        panel.addKeyListener(listener);
    }

    private void initializeFrame() {

        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);// Center Frame on screen
        frame.setResizable(false);
        frame.setTitle("TOTALLY NOT A GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setState(JFrame.NORMAL);
        frame.setUndecorated(true); //Delete window border

    }

    private void initializePanel() {

        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        frame.add(panel); // Add panel in JFrame

    }

    private RenderingHints getOptimalRenderingHints() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return rh;
    }

}
