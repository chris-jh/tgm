/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.interfaces.SceneInterface;
import org.jsfml.window.event.Event;

/**
 *
 * @author christopher
 */
public abstract class AbstractScene implements SceneInterface {

    protected int screenWidth, screenHeight;
    protected boolean playing = false;
    protected SceneEnum nextScene = null;
    protected String sceneName = null;
    protected AppInterface appInterface;

    public AbstractScene(AppInterface appInterface) {
        this.appInterface = appInterface;
    }

    public String getSceneName() {
        return sceneName;
    }

    public boolean isPlaying() {
        return playing;
    }

    public synchronized void play() {
        playing = true;
        //Main Loop for Scene
        while (isPlaying()) {
            //Delegate events to the scene
            for (Event event = appInterface.getRenderWindow().pollEvent(); event != null; event = appInterface.getRenderWindow().pollEvent()) {
                handleEvent(event);
            }

            //Update the scene
            update(appInterface.getClock().restart().asSeconds());

            //Clear the window
            appInterface.getRenderWindow().clear();

            //Render the scene
            render();

            //Display the scene
            appInterface.getRenderWindow().display();
        }

    }

    public SceneEnum getNextScene() {
        return nextScene;
    }
    
    protected void quit() {
        nextScene = null;
        playing = false;
    }
}
