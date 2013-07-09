/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import org.apache.log4j.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
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
    private final Texture backgroundTexture = new Texture();
    private final Sprite backgroundSprite = new Sprite();
    private RenderStates backgroundStates = RenderStates.DEFAULT;
    private final Font freeSansFont = new Font();
    private final Text titleText = new Text("The Games Menu\n     Main Scene", freeSansFont);
    private final Text titleTextShadow = new Text(titleText.getString(), freeSansFont);
    private final Text infoText = new Text(
            "Press N:\t\tNext Scene\n"
            + "Press Esc:\tQuit", freeSansFont);
    private float textSpinSpeed = 0.002f;
    private float textScaleValue = 0.0f;

    {
        sceneName = "Main Scene";
    }

    public void initialize(AppInterface appInterface) throws Exception {
        this.appInterface = appInterface;
        screenWidth = appInterface.getRenderTarget().getSize().x;
        screenHeight = appInterface.getRenderTarget().getSize().y;

        background.add(new Vertex(new Vector2f(0, 0), Color.RED));
        background.add(new Vertex(new Vector2f(screenWidth, 0), Color.BLUE));
        background.add(new Vertex(new Vector2f(screenWidth, screenHeight), Color.GREEN));
        background.add(new Vertex(new Vector2f(0, screenHeight), Color.YELLOW));

        backgroundTexture.loadFromStream(getClass().getResourceAsStream("/resources/images/background.png"));
        backgroundTexture.setSmooth(true);

        //Setup logo sprite
        backgroundSprite.setTexture(backgroundTexture);
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setScale((float) screenWidth / backgroundTexture.getSize().x, (float) screenHeight / backgroundTexture.getSize().y);

        freeSansFont.loadFromStream(this.getClass().getResourceAsStream("/resources/fonts/FreeSans.ttf"));

        titleText.setFont(freeSansFont);
        titleText.setStyle(Text.BOLD);
        titleText.setColor(new Color(255, 255, 255, 192));
        titleText.setCharacterSize(16);
        textScaleValue = 1.0f;

        FloatRect titleTextBounds = titleText.getLocalBounds();

        titleText.setOrigin(titleTextBounds.width / 2, titleTextBounds.height / 2);

        titleText.setPosition((float) screenWidth / 8, 18);

        titleTextShadow.setFont(freeSansFont);
        titleTextShadow.setStyle(Text.BOLD);
        titleTextShadow.setColor(new Color(0, 0, 0, 128));
        titleTextShadow.setCharacterSize(16);

        FloatRect titleTextShadowBounds = titleText.getLocalBounds();
        titleTextShadow.setOrigin(titleTextShadowBounds.width / 2, titleTextShadowBounds.height / 2);
        titleTextShadow.setPosition(((float) screenWidth / 8) + 2, 20);

        infoText.setFont(freeSansFont);
        infoText.setStyle(Text.BOLD);
        infoText.setColor(new Color(255, 255, 255, 192));
        infoText.setCharacterSize(12);
        infoText.setPosition(7, 50);

    }

    public void reset() {
        textScaleValue = 1.0f;
        dir = 0;
        pause = false;
        pauseTime = 0;
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
                        playNextScene(SceneEnum.NEXT);
                        break;
                    }
                }
                break;
            }

        }
    }
    int dir = 0;
    boolean pause = false;
    long pauseTime = 0;

    public void update(float dt) {

        if (pause) {
            if (System.currentTimeMillis() > pauseTime) {
                pause = false;
            }
        } else {
            if (dir == 0) {
                textScaleValue += textSpinSpeed;
                if (textScaleValue > 1.0) {
                    dir = 1;
                    pause = true;
                    pauseTime = System.currentTimeMillis() + 1000;
                }
            } else if (dir == 1) {
                textScaleValue -= textSpinSpeed;
                if (textScaleValue < -1.0) {
                    dir = 0;
                }
            }
        }



        titleText.setScale(textScaleValue, titleText.getScale().y);
        titleTextShadow.setScale(textScaleValue, titleText.getScale().y);


    }

    public void render() {
        appInterface.getRenderTarget().draw(background);

        appInterface.getRenderTarget().draw(backgroundSprite, backgroundStates);

        appInterface.getRenderTarget().draw(titleTextShadow);
        appInterface.getRenderTarget().draw(titleText);

        appInterface.getRenderTarget().draw(infoText);



    }
}
