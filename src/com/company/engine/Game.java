package com.company.engine;

import java.awt.event.KeyListener;

public abstract class Game {


    private boolean playing = true;
    private RenderingEngine renderingEngine;

    public abstract void init();

    public abstract void conclude();

    public abstract void update() throws CloneNotSupportedException;

    public abstract void draw(Buffer buffer);

    public Game() {

        renderingEngine = RenderingEngine.getInstance();

    }

    // final prevents overrides but children keep access to method
    public final void start() throws CloneNotSupportedException {
        init();
        maintain();
        conclude();
    }

    public final void stop() {
        playing = false;
    }

    //run
    private void maintain() throws CloneNotSupportedException {

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
