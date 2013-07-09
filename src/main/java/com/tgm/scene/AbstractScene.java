/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.interfaces.SceneInterface;
import org.jsfml.window.event.Event;
import org.springframework.beans.factory.InitializingBean;

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

    protected void playNextScene(SceneEnum scene){
        playing = false;
        appInterface.processNextScene(scene);
    }
    
    protected void quit() {
        playing = false;
        appInterface.quit();
    }

}
