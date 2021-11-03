package com.company.Engine;

import java.util.concurrent.TimeUnit;

public class GameTime {

    private final int FPS_TARGET = 60;
    private static GameTime instance;
    private static int currentFps;
    private static int fpsCount;
    private static long fpsTimeDelta;
    private static long gameStartTime;
    private long syncTime;

    public static long updateSyncTime() {
        return System.currentTimeMillis();
    }

    public static int getCurrentFps() {
        return (currentFps > 0) ? currentFps : fpsCount;
    }

    public static long getElapsedTime() {
        return System.currentTimeMillis() - gameStartTime;
    }

    public static String getElapsedFormattedTime() {
        long time = System.currentTimeMillis() - gameStartTime;
        long hours = TimeUnit.MICROSECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        time -= TimeUnit.SECONDS.toMillis(seconds);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    protected static GameTime getInstance() { // limit range to the "engine" package
        if (instance == null) {
            instance = new GameTime();
        }
        return instance;
    }

    protected void synchronize() {
        update();
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sync();

    }

    private long getSleepTime() {

        long targetTime = 1000L / FPS_TARGET;

        long sleep = targetTime - (System.currentTimeMillis() - syncTime);
        if (sleep < 4) {

            sleep = 4;
        }

        return sleep;

    }

    private void update() {

        fpsCount++;
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        if (fpsTimeDelta != currentSecond){
            currentFps = fpsCount;
            fpsCount = 0;
        }
        fpsTimeDelta = currentSecond;
    }

    private GameTime() {
        sync();
        gameStartTime = System.currentTimeMillis();
        fpsTimeDelta = 0;
        currentFps = 0;
    }

    private void sync() {

        syncTime = System.currentTimeMillis();

    }


}
