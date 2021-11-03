package com.company.Engine;

import java.awt.event.KeyListener;

public abstract class Game {


    private boolean playing = true;
    private RenderingEngine renderingEngine;

    public abstract void init();

    public abstract void conclude();

    public abstract void update();

    public abstract void draw(Buffer buffer);

    public Game() {

        renderingEngine = RenderingEngine.getInstance();

    }

    // final prevents overrides but children keep access to method
    public final void start() {
        init();
        maintain();
        conclude();
    }

    public final void stop() {
        playing = false;
    }

    public void addKeyListener(KeyListener listener) {
        renderingEngine.addKeyListener(listener);
    }

    //run
    private void maintain() {

        renderingEngine.start();

       GameTime.getInstance();

        while (playing) {
            update();
            draw(renderingEngine.getRenderingBuffer());
            drawBufferOnScreen();
            GameTime.getInstance().synchronize();
        }

        renderingEngine.stop();

    }

    private void drawBufferOnScreen() {
        renderingEngine.renderBufferOnScreen();
    }




}
