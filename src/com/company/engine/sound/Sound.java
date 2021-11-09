package com.company.engine.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {

    private static final String BASE_PATH = "./assets/Audio/";
    private Clip clip;

    public Sound(String filename) {
        try {
            File url = new File(BASE_PATH + filename + ".wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public final void playWithoutInterrup() {
        play(false, false);
    }

    public final void play() {
        play(false, true);
    }

    public final void playWithLoop() {
        play(true, true);
    }

    private void play(boolean loop, boolean allowInterrupt) {
        if(isRunning() && allowInterrupt) {
            stop();
        } else if(isRunning()){
            return;
        }

        clip.setFramePosition(0);
        clip.start();
        if(loop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public final boolean isRunning() {
        return clip.isRunning();
    }

    public final void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }

}
