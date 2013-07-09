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
import org.jsfml.window.event.Event;

/**
 *
 * @author christopher
 */
public class MainScene extends AbstractScene {

    private final VertexArray background = new VertexArray(PrimitiveType.QUADS);
    private final Font freeSansFont = new Font();
    private final Text titleText = new Text("The Games Menu\nMain Scene", freeSansFont);
    private final Text titleTextShadow = new Text(titleText.getString(), freeSansFont);
    private final Text infoText = new Text(
            "Press N:\t\tNext Scene\n"
            + "Press Esc:\tQuit", freeSansFont);
    private float textSpinSpeed = 0.005f;
    private float textScaleValue = 0.0f;

    {
        sceneName = "Main Scene";
    }

    public MainScene(AppInterface appInterface) throws Exception {
        super(appInterface);
        initialize();
    }

    private void initialize() throws Exception {
        screenWidth = appInterface.getRenderTarget().getSize().x;
        screenHeight = appInterface.getRenderTarget().getSize().y;

        background.add(new Vertex(new Vector2f(0, 0), Color.RED));
        background.add(new Vertex(new Vector2f(screenWidth, 0), Color.BLUE));
        background.add(new Vertex(new Vector2f(screenWidth, screenHeight), Color.GREEN));
        background.add(new Vertex(new Vector2f(0, screenHeight), Color.YELLOW));

        freeSansFont.loadFromStream(this.getClass().getResourceAsStream("/resources/fonts/FreeSans.ttf"));

        titleText.setFont(freeSansFont);
        titleText.setStyle(Text.BOLD);
        titleText.setColor(new Color(255, 255, 255, 192));
        titleText.setCharacterSize(16);
        textScaleValue = 1.0f;

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
        textScaleValue = 1.0f;
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
                        nextScene = SceneEnum.NEXT;
                        playing = false;
                        break;
                    }
                }
                break;
            }

        }
    }
    int dir = 0;

    public void update(float dt) {

        if (dir == 0) {
            textScaleValue += textSpinSpeed;
            if (textScaleValue > 1.0) {
                dir = 1;
            }
        } else if (dir == 1) {
            textScaleValue -= textSpinSpeed;
            if (textScaleValue < -1.0) {
                dir = 0;
            }
        }

        titleText.setScale(textScaleValue, titleText.getScale().y);
        titleTextShadow.setScale(textScaleValue, titleText.getScale().y);


    }

    public void render() {
        appInterface.getRenderTarget().draw(background);

        appInterface.getRenderTarget().draw(titleTextShadow);
        appInterface.getRenderTarget().draw(titleText);

        appInterface.getRenderTarget().draw(infoText);



    }
}
