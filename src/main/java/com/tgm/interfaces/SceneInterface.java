/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.interfaces;

import com.tgm.enums.SceneEnum;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.window.event.Event;

/**
 *
 * @author christopher
 */
public interface SceneInterface {
        
    /**
     * Get the Scene Name
     * 
     * @return SceneName 
     */
    public String getSceneName();
    
    public void reset();
    
    /**
     * Play the Scene
     *  
     */
    public void play();
    
    /**
     * Get the next scene to play
     * 
     * @return SceneInterface 
     */
    public SceneEnum getNextScene();
    
    /**
     * Check if the scene is playing
     * 
     * @return True/False
     */
    public boolean isPlaying();
    
    /**
     * Handles a user event.
     *
     * @param event The event to handle.
     */
    public void handleEvent(Event event);

    /**
     * Updates the scene.
     *
     * @param dt The time delta, in seconds, since the last update call.
     */
    public void update(float dt);

    /**
     * Renders the scene to the specified target.
     *
     * @param target The render target.
     */
    public void render();
    
}
