/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.enums.Platform;
import com.tgm.gui.components.Image;
import com.tgm.gui.components.Label;
import com.tgm.gui.components.Panel;
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

    private float textSpinSpeed = 0.02f;
    private float textScaleValue = 1.0f;
    private boolean pause = false;
    private Long pauseTime = 0l;
    private int dir = 0;
    
    
    private final float start = 80;
    private float insert = 25;
    private float margin = 7;
    private int menuSelectionIndex = 1;
    private float menuSelectionPosition = 1;
    private float menuSelectionMovement = 1;
    private int maxNumberMenuItemsDisplayed = 15;
    private int menuDisplayedIndex = 0;
    private int menuSize = 0;
    
    //-----------------
    private Color white = Color.white;
    private float startPos = start - 2;

    private void initMenu() {
        Logger.getLogger(this.getClass()).info("initMenu 1");


        float h = start;
        int i = 0;
        float t = 3.84f;
        float hh = 24f;
        float w = this.getWidth() / 1.05f;
        float w2 = this.getWidth() / 1.10f;
        float w3 = this.getWidth() / 1.20f;
        float mm = (w - w2) / 2f;

        margin = (w - w3) / 2f;
        insert = getHeight() / 24f;

        float msH = getHeight() / hh;
        int fS = (int) (getHeight() / 37.5);
        int tFS = (int) (getHeight() / 27.2);
        float fH = getHeight() / 31.5f;
        float fM = (msH - fH) / 2f;

        startPos = start - fM;

        menuSelectionIndex = 0;
        menuSelectionPosition = startPos;
        menuSelectionMovement = menuSelectionPosition;
        menuDisplayedIndex = 0;

        add(new Label("menuTitleLabel", "The Games Menu", 0, 10, w2, 50, Label.CENTER, "Arial", Font.BOLD, tFS, white, true));

        add(new Image("menuSelection", "images/menu_selection.png", mm, menuSelectionPosition, w2, msH));

        add(new Label("menuLabel" + i, "Scan For Games", margin, h, w3, msH, Label.CENTER, "Arial", Font.BOLD, fS, white, true));

        for (Platform platform : Platform.values()) {
            h += insert;
            i++;
            add(new Label("menuLabel" + i, WordUtils.capitalize(StringUtils.replace(platform.name(), "_", " ").toLowerCase()), margin, h, w3, msH, Label.CENTER, "Arial", Font.BOLD, fS, white, true));
        }
        h += insert;
        i++;
        add(new Label("menuLabel" + i, "Quit", margin, h, w3, msH, Label.CENTER, "Arial", Font.BOLD, fS, white, true));
        Logger.getLogger(this.getClass()).info("initMenu 2");
        menuSize = i + 1;

        Logger.getLogger(this.getClass()).info("initMenu 3");
        
        if (maxNumberMenuItemsDisplayed > menuSize){
            maxNumberMenuItemsDisplayed = menuSize;
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initMenu();
        super.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        super.update(gc, i);

//        updateTitle();
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

//    private void updateTitle() {
//        if (pause) {
//            if (System.currentTimeMillis() > pauseTime) {
//                pause = false;
//            }
//        } else {
//            if (dir == 0) {
//                textScaleValue += textSpinSpeed;
//                if (textScaleValue > 1.0) {
//                    dir = 1;
//                    pause = true;
//                    pauseTime = System.currentTimeMillis() + 2000;
//                } else {
//                    pauseTime = System.currentTimeMillis() + 50;
//                    pause = true;
//
//                }
//            } else if (dir == 1) {
//                textScaleValue -= textSpinSpeed;
//                pauseTime = System.currentTimeMillis() + 50;
//                pause = true;
//
//                if (textScaleValue < -1.0) {
//                    dir = 0;
//                }
//            }
//        }
//        getComponent("menuTitleLabel").setSx(textScaleValue);
//    }

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
        boolean vv = true;
        menuSelectionPosition = (startPos) + ((menuSelectionIndex < maxNumberMenuItemsDisplayed) ? menuSelectionIndex * insert : (maxNumberMenuItemsDisplayed - 1) * insert);

        if (menuSelectionPosition < menuSelectionMovement) {
            menuSelectionMovement -= 2;
            vv = false;
        }

        if (menuSelectionPosition > menuSelectionMovement) {
            menuSelectionMovement += 2;
            vv = false;
        }

        getComponent("menuSelection").setY(menuSelectionMovement);

        for (int i = 0; i < menuSize; i++) {
            getComponent("menuLabel" + i).setVisable(false);
        }

        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            float p = start + (i * insert);
            getComponent("menuLabel" + (i + menuDisplayedIndex)).setY(p);
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
