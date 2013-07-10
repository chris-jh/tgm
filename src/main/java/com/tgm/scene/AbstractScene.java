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

    public String getSceneName() {
        return sceneName;
    }

    public boolean isPlaying() {
        return playing;
    }

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

    protected Path readMedia(String media) {
        File f = new File(TgmResource.MEDIA + "/" + media);
        Logger.getLogger(this.getClass()).info("LOAD RESOURCE: [" + TgmResource.MEDIA + "/" + media + "] = " + f.getAbsolutePath());
        return f.toPath();
    }
}
