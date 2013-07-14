/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.interfaces.SceneInterface;
import com.tgm.resources.TgmResource;
import java.io.File;
import java.nio.file.Path;
import org.apache.log4j.Logger;

/**
 *
 * @author christopher
 */
public abstract class AbstractScene implements SceneInterface {

    protected int screenWidth, screenHeight;
    protected boolean playing = false;
    protected String sceneName = null;
    protected AppInterface appInterface;

    @Override
    public String getSceneName() {
        return sceneName;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void play() {
        if (!isPlaying()) {
            reset();
            playing = true;
        }
    }

    protected void playNextScene(SceneEnum scene) {
        playing = false;
        appInterface.processNextScene(scene);
    }

    protected void quit() {
        playing = false;
        appInterface.quit();
    }

    @Override
    public void stopPlaying() {
        playing = false;
    }

    @Override
    public void updateNextScene(SceneEnum scene) {
        playNextScene(scene);
    }
}
