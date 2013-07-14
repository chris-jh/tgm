/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.App;
import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.utils.SceneUtils;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.window.event.Event;

/**
 *
 * @author christopher
 */
public class MainScene extends AbstractScene {

    {
        sceneName = "Main Scene";
    }

    @Override
    public void initialize(AppInterface appInterface) throws Exception {
        this.appInterface = appInterface;
        screenWidth = appInterface.getRenderTarget().getSize().x;
        screenHeight = appInterface.getRenderTarget().getSize().y;
    }

    @Override
    public void reset() {

    }

    @Override
    public void handleEvent(Event event) {
        App.menu.menuHandleEvent(event, this);
        
        switch (event.type) {
            case CLOSED: {
                quit();
                break;
            }
            case JOYSTICK_MOVED: {
                System.out.println("JOYSTICK [" + event.asJoystickMoveEvent().joyAxis.name() + "]: " + event.asJoystickMoveEvent().position);
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
                    case N: {
                        playNextScene(SceneEnum.NEXT);
                        break;
                    }
                }
                break;
            }

        }
    }
    
    @Override
    public void update(float dt) {
        App.menu.update();
    }

    @Override
    public void render() {
        App.menu.render();
    }
}
