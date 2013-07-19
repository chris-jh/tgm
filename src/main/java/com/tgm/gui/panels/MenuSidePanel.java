/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.enums.Platform;
import com.tgm.gui.components.Label;
import com.tgm.gui.components.Panel;
import com.tgm.gui.components.Shape;
import com.tgm.gui.enums.Command;
import java.awt.Font;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.springframework.stereotype.Component;
/**
 *
 * @author christopher
 */
@Component
public class MenuSidePanel extends Panel {
    
    private float textSpinSpeed = 0.002f;
    private float textScaleValue = 1.0f;
    private boolean pause = false;
    private Long pauseTime = 0l;
    private int dir = 0;
    private final int start = 80;
    private final int insert = 25;
    private final int margin = 7;
    private int menuSelectionIndex = 1;
    private float menuSelectionPosition = 1;
    private float menuSelectionMovement = 1;
    private int maxNumberMenuItemsDisplayed = 5;
    private int menuDisplayedIndex = 0;
    private int menuSize = 0;
    //-----------------

    private void initTitle() {
        add(new Label("menuTitleLabel", "The Games Menu", 10, 10, "Arial", Font.BOLD, 22));
    }

    private void initMenu() {
        Logger.getLogger(this.getClass()).info("initMenu 1");
        menuSelectionIndex = 0;
        menuSelectionPosition = start - 2;
        menuSelectionMovement = menuSelectionPosition;
        menuDisplayedIndex = 0;

        int h = start;
        int i = 0;
        add(new Shape("menuSelection", Shape.Type.ROUND_RECTANGLE, 5, menuSelectionPosition, this.getWidth(), 20, 5));
        ((Shape) getComponent("menuSelection")).setFill(Color.red);

        add(new Label("menuLabel" + i, "Scan For Games", margin, h, "Arial", Font.BOLD, 16));

        for (Platform platform : Platform.values()) {
            h += insert;
            i++;
            add(new Label("menuLabel" + i, WordUtils.capitalize(StringUtils.replace(platform.name(), "_", " ").toLowerCase()), margin, h, "Arial", Font.BOLD, 16));
        }
        h += insert;
        i++;
        add(new Label("menuLabel" + i, "Quit", margin, h, "Arial", Font.BOLD, 16));
        Logger.getLogger(this.getClass()).info("initMenu 2");
        menuSize = i + 1;

        Logger.getLogger(this.getClass()).info("initMenu 3");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initTitle();
        initMenu();
        super.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        super.update(gc, i);
        updateTitle();
        updateMenuSelection();

        if (gc.getInput().isKeyPressed(Input.KEY_RETURN)) {
            menuSelected();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            menuUp();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            menuDown();
        }

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
        //title.setScale(textScaleValue, title.getScale().y);
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
        if ((menuSelectionIndex + 1) < menuSize) {
            menuSelectionIndex += 1;
        }

        if (menuSelectionIndex >= maxNumberMenuItemsDisplayed) {
            if ((menuDisplayedIndex + maxNumberMenuItemsDisplayed) < menuSize) {
                menuDisplayedIndex++;
            }
        }
        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);
    }

    private void updateMenuSelection() {
        menuSelectionPosition = (start - 2) + ((menuSelectionIndex < maxNumberMenuItemsDisplayed) ? menuSelectionIndex * insert : (maxNumberMenuItemsDisplayed - 1) * insert);
        if (menuSelectionPosition < menuSelectionMovement) {
            menuSelectionMovement -= 1;
        }
        if (menuSelectionPosition > menuSelectionMovement) {
            menuSelectionMovement += 1;
        }

        ((Shape) getComponent("menuSelection")).setLocation(5, menuSelectionMovement);

        for (int i = 0; i < menuSize; i++) {
            getComponent("menuLabel" + i).setVisable(false);
        }

        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            float p = start + (i * insert);
            getComponent("menuLabel" + (i + menuDisplayedIndex)).setLocation(margin, p);
            getComponent("menuLabel" + (i + menuDisplayedIndex)).setVisable(true);
        }
    }

    private void menuSelected() {
        int quitIndex = menuSize - 1;
        if (menuSelectionIndex == 0) {
            parentScreen.command(Command.SCAN_FOR_GAMES);
        } else if (menuSelectionIndex == quitIndex) {
            parentScreen.command(Command.QUIT);
        }
    }
}
