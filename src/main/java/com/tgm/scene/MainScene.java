/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.interfaces.SceneInterface;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

/**
 *
 * @author christopher
 */
public class MainScene implements SceneInterface {

    private final VertexArray background = new VertexArray(PrimitiveType.QUADS);
    private int screenWidth, screenHeight;
    private String sceneName = "Main Scene";
    private boolean playing = false;

    public String getSceneName() {
        return sceneName;
    }

    public void initialize(RenderTarget target) throws Exception {
        playing = true;
        screenWidth = target.getSize().x;
        screenHeight = target.getSize().y;

        background.add(new Vertex(new Vector2f(0, 0), Color.RED));
        background.add(new Vertex(new Vector2f(target.getSize().x, 0), Color.BLUE));
        background.add(new Vertex(new Vector2f(target.getSize().x, target.getSize().y), Color.GREEN));
        background.add(new Vertex(new Vector2f(0, target.getSize().y), Color.YELLOW));

    }

    public void handleEvent(Event event) {
        switch (event.type) {
            case CLOSED: {
                quit();
                break;
            }
            case JOYSTICK_MOVED: {
                System.out.println("JOYSTICK ["+event.asJoystickMoveEvent().joyAxis.name()+"]: " + event.asJoystickMoveEvent().position);
                break;
            }
            case JOYSTICK_BUTTON_PRESSED: {
                System.out.println("JOYSTICK BUTTON: " + event.asJoystickButtonEvent().button);
                break;
            }
            case KEY_PRESSED: {
                switch (event.asKeyEvent().key) {
                    case ESCAPE: {
                        quit();
                        break;
                    }
                }
                break;
            }

        }
    }

    public void update(float dt) {
    }

    public void render(RenderTarget target) {
        target.draw(background);
    }

    private void quit() {
        playing = false;
    }

    public boolean isDone() {
        return !playing;
    }

    public SceneInterface getNextScene() {
        return null;
    }

    public boolean isPlaying() {
        return playing;
    }
}
