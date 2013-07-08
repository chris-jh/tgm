/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.interfaces;

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
    
    /**
     * Get the next scene to play
     * 
     * @return SceneInterface 
     */
    public SceneInterface getNextScene();
    
    /**
     * Check if the scene is playing
     * 
     * @return True/False
     */
    public boolean isPlaying();
    
    /**
     * Initializes the scene by loading resources and creating initial entities.
     *
     * @param target The render target.
     * @throws Exception In case an error occurs while initializing the scene.
     */
    public void initialize(RenderTarget target) throws Exception;

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
    public void render(RenderTarget target);

    /**
     * Determines whether the scene is done and should be ended.
     *
     * @return <tt>true</tt> if the scene should be ended, <tt>false</tt> to continue.
     */
    public boolean isDone();

    
}
