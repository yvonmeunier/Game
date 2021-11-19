package com.company.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {

    private static GraphicsDevice device;
    private JFrame frame;
    private DisplayMode fullscreenDisplayMode;
    private DisplayMode windowedDisplayMode;
    private boolean isFullscreenMode;
    private Cursor invisibleCursor;

    public Screen() {
        initializeFrame();
        initializeHiddenCursor();
        initializeDevice();
    }

    public void fullscreen() {
        if (device.isDisplayChangeSupported()) {
            if (device.isFullScreenSupported()) {
                device.setFullScreenWindow(frame);
            }
            device.setDisplayMode(fullscreenDisplayMode);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.requestFocus();
            isFullscreenMode = true;
        }
    }

    public void windowed() {
        if (device.isDisplayChangeSupported()){
            if (device.isFullScreenSupported()){
                device.setFullScreenWindow(null);
            }
            device.setDisplayMode(windowedDisplayMode);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.requestFocus();
            isFullscreenMode = false;
        }
    }

    public void toggleFullScreen() {
        if (isFullscreenMode) {
            windowed();
        } else {
            fullscreen();
        }
    }

    public void hideCursor() {
        frame.setCursor(invisibleCursor);
    }

    public void showCursor() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void showCrossHair() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    private void initializeFrame() {

        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("TOTALLY NOT A GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setState(JFrame.NORMAL);
        frame.setUndecorated(true); //Delete window border

    }

    protected void setSize(int width, int height) {
        boolean frameIsVisible = frame.isVisible();
        if (frameIsVisible) {
            frame.setVisible(false);
        }
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        if (frameIsVisible) {
            frame.setVisible(true);
        }
        fullscreenDisplayMode = findClosestDisplayMode(width, height);
        System.out.println("Fullscreen : " + fullscreenDisplayMode.getWidth() + "x" + fullscreenDisplayMode.getHeight());
    }

    protected void setPanel(JPanel panel) {
        frame.add(panel);
    }

    protected void setTitle(String title) {
        frame.setTitle(title);
    }

    protected void start() {
        frame.setVisible(true);
    }

    protected void end() {
        frame.setVisible(false);
        frame.dispose();
    }

    private void initializeHiddenCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0, 0);
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, hotSpot, "Invisible cursor");
    }

    private void initializeDevice() {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowedDisplayMode = device.getDisplayMode();
        System.out.println("Windowed : " + windowedDisplayMode.getWidth() + "x" + windowedDisplayMode.getHeight());
    }

    private int closestIndexOfValue(int value, int[] list) {
        int closestIndex = -1;
        for (int i = 0, min = Integer.MAX_VALUE; i < list.length; i++) {
            final int difference = Math.abs(list[i] - value);
            if (difference < min) {
                min = difference;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    private DisplayMode findClosestDisplayMode(int width, int height) {
        DisplayMode[] displayModes = device.getDisplayModes();
        int desiredResolution = width * height;
        int[] availableResolutions = new int[displayModes.length];
        for (int i = 0; i < displayModes.length; i++) {
            availableResolutions[i] = displayModes[i].getWidth() * displayModes[i].getHeight();
        }
        return displayModes[closestIndexOfValue(desiredResolution, availableResolutions)];
    }

}
