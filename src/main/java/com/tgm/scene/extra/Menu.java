/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene.extra;

import com.tgm.App;
import com.tgm.enums.Platform;
import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.interfaces.SceneInterface;
import com.tgm.utils.SceneUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import static org.jsfml.window.Keyboard.Key.ESCAPE;
import static org.jsfml.window.Keyboard.Key.UP;
import org.jsfml.window.event.Event;
import static org.jsfml.window.event.Event.Type.KEY_PRESSED;

/**
 *
 * @author christopher
 */
public class Menu {

    private final Texture backgroundTexture = new Texture();
    private final Sprite backgroundSprite = new Sprite();
    private RenderStates backgroundStates = RenderStates.DEFAULT;
    private Text title;
    private float textSpinSpeed = 0.002f;
    private float textScaleValue = 1.0f;
    private boolean pause = false;
    private Long pauseTime = 0l;
    private int dir = 0;
    //private Text title2;
    AppInterface appInterface;
    private final List<Text> menu = new ArrayList<Text>();
    private RectangleShape menuSelectionBackground = null;
    private CircleShape menuSelectionBackgroundUp = null;
    private CircleShape menuSelectionBackgroundDown = null;
    private final int start = 80;
    private final int insert = 25;
    private final int margin = 7;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int menuSelectionIndex = 1;
    private float menuSelectionPosition = 1;
    private float menuSelectionMovement = 1;
    private int maxNumberMenuItemsDisplayed = 5;
    private int menuDisplayedIndex = 0;

    public void init(AppInterface appInterface) throws Exception {
        this.appInterface = appInterface;
        screenWidth = appInterface.getRenderTarget().getSize().x;
        screenHeight = appInterface.getRenderTarget().getSize().y;
        initTitle();
        initMenu();
    }

    private void initTitle() {
        title = createText("The Games Menu", 1);
        title.setCharacterSize(20);
        FloatRect titleTextBounds = title.getLocalBounds();
        title.setOrigin(titleTextBounds.width / 2, titleTextBounds.height / 2);
        title.setPosition((float) screenWidth / 8, 18);
    }

    private void initMenu() throws Exception {
        int h = start;
        menu.add(createText("Scan For Games", h));
        for (Platform platform : Platform.values()) {
            h += insert;
            menu.add(createText(platform.name(), h)); //Needs Formatting
        }
        h += insert;
        menu.add(createText("Quit", h));

        menuSelectionIndex = 0;
        menuSelectionPosition = start - 2;
        menuSelectionMovement = menuSelectionPosition;
        menuDisplayedIndex = 0;

        menuSelectionBackground = new RectangleShape(new Vector2f(screenWidth / 4.1f, 20));
        menuSelectionBackground.setPosition(5, menuSelectionPosition);
        menuSelectionBackground.setFillColor(Color.BLUE);
        menuSelectionBackground.setOutlineColor(Color.WHITE);
        menuSelectionBackground.setOutlineThickness(1.0f);

        menuSelectionBackgroundUp = new CircleShape(5, 3);
        menuSelectionBackgroundUp.setPosition(5, menuSelectionPosition - 5);
        menuSelectionBackgroundUp.setFillColor(Color.BLUE);
        menuSelectionBackgroundUp.setOutlineColor(Color.WHITE);
        menuSelectionBackgroundUp.setOutlineThickness(1.0f);

        menuSelectionBackgroundDown = new CircleShape(6, 3);
        menuSelectionBackgroundDown.setPosition(5, menuSelectionPosition - 5);
        menuSelectionBackgroundDown.setFillColor(Color.BLUE);
        menuSelectionBackgroundDown.setOutlineColor(Color.WHITE);
        menuSelectionBackgroundDown.setOutlineThickness(1.0f);
        menuSelectionBackgroundDown.setRotation(180f);

        backgroundTexture.loadFromFile(SceneUtils.readMedia("images/background.png"));
        backgroundTexture.setSmooth(true);

        //Setup logo sprite
        backgroundSprite.setTexture(backgroundTexture);
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.setScale((float) screenWidth / backgroundTexture.getSize().x, (float) screenHeight / backgroundTexture.getSize().y);


    }

    private Text createText(String text, int h) {
        Text t = new Text(text, App.fonts.getFreeSansFont());
        //t.setFont( App.fonts.getFreeSansFont());
        t.setStyle(Text.BOLD);
        t.setColor(new Color(255, 255, 255, 192));
        t.setCharacterSize(12);
        t.setPosition(margin, h);
        return t;
    }

    public void update() {
        updateTitle();
        updateMenuSelection();
    }

    private void updateTitle() {
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
        title.setScale(textScaleValue, title.getScale().y);
    }

    private void menuUp() {
        if ((menuSelectionIndex - 1) >= 0) {
            menuSelectionIndex -= 1;
        }

        if ((menuDisplayedIndex - 1) >= 0) {
            menuDisplayedIndex--;
        }

        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);


    }

    private void menuDown() {
        if ((menuSelectionIndex + 1) < menu.size()) {
            menuSelectionIndex += 1;
        }

        if (menuSelectionIndex >= maxNumberMenuItemsDisplayed) {
            if ((menuDisplayedIndex + maxNumberMenuItemsDisplayed) < menu.size()) {
                menuDisplayedIndex++;
            }
        }
        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);
    }

    public void menuHandleEvent(Event event, SceneInterface sceneInterface) {
        switch (event.type) {
            case KEY_PRESSED: {
                Logger.getLogger(this.getClass()).info("KEY: " + event.asKeyEvent().key);
                switch (event.asKeyEvent().key) {
                    case ESCAPE: {
                        break;
                    }
                    case UP: {
                        menuUp();
                        break;
                    }
                    case DOWN: {
                        menuDown();
                        break;
                    }
                    case RETURN: {
                        menuSelected(sceneInterface);
                        break;
                    }
                }
                break;
            }
        }
    }

    private void updateMenuSelection() {
        menuSelectionPosition = (start - 2) + ((menuSelectionIndex < maxNumberMenuItemsDisplayed) ? menuSelectionIndex * insert : (maxNumberMenuItemsDisplayed - 1) * insert);
        if (menuSelectionPosition < menuSelectionMovement) {
            menuSelectionMovement -= 0.5;
        }
        if (menuSelectionPosition > menuSelectionMovement) {
            menuSelectionMovement += 0.5;
        }

        menuSelectionBackground.setPosition(5, menuSelectionMovement);
        menuSelectionBackgroundUp.setPosition((menuSelectionBackground.getSize().x / 2), menuSelectionMovement - 9);
        menuSelectionBackgroundDown.setPosition(11 + (menuSelectionBackground.getSize().x / 2), menuSelectionMovement + 30);


        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            float p = start + (i * insert);
            menu.get(i + menuDisplayedIndex).setPosition(margin, p);
        }
    }

    private void renderTitle() {
        appInterface.getRenderTarget().draw(getTitle());
    }

    private void renderMenu() {
        if (menuSelectionIndex > 0) {
            appInterface.getRenderTarget().draw(menuSelectionBackgroundUp);
        }
        if (menuSelectionIndex < menu.size() - 1) {
            appInterface.getRenderTarget().draw(menuSelectionBackgroundDown);
        }
        appInterface.getRenderTarget().draw(menuSelectionBackground);

        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            if (i + menuDisplayedIndex < menu.size()) {
                appInterface.getRenderTarget().draw(menu.get(i + menuDisplayedIndex));
            }
        }

    }

    public List<Text> getMenu() {
        return menu;
    }

    public Text getTitle() {
        return title;
    }

    private void menuSelected(SceneInterface sceneInterface) {

        int quitIndex = menu.size() - 1;
        if (menuSelectionIndex == 0) {
            sceneInterface.updateNextScene(SceneEnum.SCAN_FOR_GAMES);
        } else if (menuSelectionIndex == quitIndex) {
            appInterface.quit();
        }
    }

    public void render() {
        appInterface.getRenderTarget().draw(backgroundSprite, backgroundStates);
        renderTitle();
        renderMenu();
    }
}
