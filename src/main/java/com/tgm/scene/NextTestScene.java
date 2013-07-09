/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;
import static org.jsfml.window.Keyboard.Key.N;
import org.jsfml.window.event.Event;

/**
 *
 * @author christopher
 */
public class NextTestScene extends AbstractScene {

    private final VertexArray background = new VertexArray(PrimitiveType.QUADS);
    private final Font freeSansFont = new Font();
    private final Text titleText = new Text("The Games Menu\nNext Scene", freeSansFont);
    private final Text titleTextShadow = new Text(titleText.getString(), freeSansFont);
    private final Text infoText = new Text(
            "Press N:\t\tNext Scene\n"
            + "Press Esc:\tQuit", freeSansFont);

    {
        sceneName = "Next Scene";
    }

    public void initialize(AppInterface appInterface) throws Exception {
        this.appInterface = appInterface;
        screenWidth = appInterface.getRenderTarget().getSize().x;
        screenHeight = appInterface.getRenderTarget().getSize().y;

        background.add(new Vertex(new Vector2f(0, 0), Color.YELLOW));
        background.add(new Vertex(new Vector2f(screenWidth, 0), Color.GREEN));
        background.add(new Vertex(new Vector2f(screenWidth, screenHeight), Color.BLUE));
        background.add(new Vertex(new Vector2f(0, screenHeight), Color.RED));

        freeSansFont.loadFromStream(this.getClass().getResourceAsStream("/resources/fonts/FreeSans.ttf"));

        titleText.setFont(freeSansFont);
        titleText.setStyle(Text.BOLD);
        titleText.setColor(new Color(255, 255, 255, 192));
        titleText.setCharacterSize(16);

        FloatRect titleTextBounds = titleText.getLocalBounds();

        titleText.setOrigin(titleTextBounds.width / 2, titleTextBounds.height / 2);

        titleText.setPosition(screenWidth / 2 - 10, 18);

        titleTextShadow.setFont(freeSansFont);
        titleTextShadow.setStyle(Text.BOLD);
        titleTextShadow.setColor(new Color(0, 0, 0, 128));
        titleTextShadow.setCharacterSize(16);

        FloatRect titleTextShadowBounds = titleText.getLocalBounds();
        titleTextShadow.setOrigin(titleTextShadowBounds.width / 2, titleTextShadowBounds.height / 2);
        titleTextShadow.setPosition(screenWidth / 2 - 8, 20);

        infoText.setFont(freeSansFont);
        infoText.setStyle(Text.BOLD);
        infoText.setColor(new Color(255, 255, 255, 192));
        infoText.setCharacterSize(12);
        infoText.setPosition(7, 50);
    }

    public void reset() {
    }

    public void handleEvent(Event event) {
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
                        playNextScene(SceneEnum.MAIN);
                        break;
                    }
                }
                break;
            }

        }
    }

    public void update(float dt) {
    }

    public void render() {
        appInterface.getRenderTarget().draw(background);

        appInterface.getRenderTarget().draw(titleTextShadow);
        appInterface.getRenderTarget().draw(titleText);

        appInterface.getRenderTarget().draw(infoText);

    }
}
