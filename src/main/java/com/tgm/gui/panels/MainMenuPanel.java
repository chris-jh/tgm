/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.config.PlatformConfig;
import com.tgm.enums.Platform;
import com.tgm.gui.components.Image;
import com.tgm.gui.components.Label;
import com.tgm.gui.components.MenuLabel;
import com.tgm.gui.components.Panel;
import com.tgm.gui.enums.Command;
import com.tgm.resources.TgmResource;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class MainMenuPanel extends Panel {

    private float textSpinSpeed = 0.02f;
    private float textScaleValue = 1.0f;
    private boolean pause = false;
    private Long pauseTime = 0l;
    private int dir = 0;
    private final float marginTop = 80;
    private float menuInsert = 25;
    private float textMarginLeft = 7;
    private int menuSelectionIndex = 1;
    private float menuSelectionPosition = 1;
    private float menuSelectionMovement = 1;
    private int maxNumberMenuItemsDisplayed = 15;
    private int menuDisplayedIndex = 0;
    //-----------------
    private Color white = Color.white;
    private float marginSelectionTop = marginTop - 2;
    @Autowired
    private TgmResource tgmResource;
    private List<MenuLabel> menuList = new ArrayList<MenuLabel>();

    private void initMenu() {
        Logger.getLogger(this.getClass()).info("initMenu 1");

        float marginMenu = marginTop;
        float menuHeightPercentage = 24f;
        float normalWidth = this.getWidth() / 1.05f;
        float selectionWidth = this.getWidth() / 1.10f;
        float textWidth = this.getWidth() / 1.20f;
        float selcetionMargin = (normalWidth - selectionWidth) / 2f;

        float menuSelectionHeight = getHeight() / menuHeightPercentage;
        float menuFontHeight = getHeight() / 31.5f;
        float menuFontMarginTop = (menuSelectionHeight - menuFontHeight) / 2f;

        int i = 0;
        int menuFontSize = (int) (getHeight() / 37.5);
        int titleFontSize = (int) (getHeight() / 27.2);

        textMarginLeft = (normalWidth - textWidth) / 2f;
        menuInsert = getHeight() / 24f;
        marginSelectionTop = marginTop - menuFontMarginTop;

        menuSelectionIndex = 0;
        menuSelectionPosition = marginSelectionTop;
        menuSelectionMovement = menuSelectionPosition;
        menuDisplayedIndex = 0;

        add(new Label("menuTitleLabel", "The Games Menu", 0, 10, selectionWidth, 50, Label.CENTER, "Arial", Font.BOLD, titleFontSize, white, true));

        add(new Image("menuSelection", "images/menu_selection.png", selcetionMargin, menuSelectionPosition, selectionWidth, menuSelectionHeight));

        menuList.add((MenuLabel) add(new MenuLabel(i, "SCAN_FOR_GAMES", "menuLabel" + i, "Scan For Games", textMarginLeft, marginMenu, textWidth, menuSelectionHeight, Label.CENTER, "Arial", Font.BOLD, menuFontSize, white, true)));

        for (Map.Entry<Platform, PlatformConfig> entry : tgmResource.getPlatformConfigs().entrySet()) {
            marginMenu += menuInsert;
            i++;
            menuList.add((MenuLabel) add(new MenuLabel(i, entry.getKey().name(), "menuLabel" + i, WordUtils.capitalize(StringUtils.replace(entry.getKey().name(), "_", " ").toLowerCase()), textMarginLeft, marginMenu, textWidth, menuSelectionHeight, Label.CENTER, "Arial", Font.BOLD, menuFontSize, white, true)));
        }

        marginMenu += menuInsert;
        i++;
        menuList.add((MenuLabel) add(new MenuLabel(i, "QUIT", "menuLabel" + i, "Quit", textMarginLeft, marginMenu, textWidth, menuSelectionHeight, Label.CENTER, "Arial", Font.BOLD, menuFontSize, white, true)));
        Logger.getLogger(this.getClass()).info("initMenu 2");

        Logger.getLogger(this.getClass()).info("initMenu 3");

        if (maxNumberMenuItemsDisplayed > menuList.size()) {
            maxNumberMenuItemsDisplayed = menuList.size();
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
        //Logger.getLogger(this.getClass()).info("UPDATE MENU PANEL "+System.currentTimeMillis());

//        updateTitle();
        updateMenuSelection();
        boolean r = false;
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
            r = true;
            Logger.getLogger(this.getClass()).info("KEY RIGHT: on focus " + focused);
        }

        if (focused) {

            if (gc.getInput().isKeyPressed(Input.KEY_RETURN)) {
                menuSelected();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
                menuUp();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
                menuDown();
            }
            if ((gc.getInput().isKeyPressed(Input.KEY_RIGHT)) || r) {
                menuRight();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
                menuGoBack();
            }

            gc.getInput().clearKeyPressedRecord();
        }
    }

    private void menuUp() {
        if ((menuSelectionIndex - 1) >= 0) {
            menuSelectionIndex -= 1;
        }

        if ((menuDisplayedIndex - 1) >= 0) {
            menuDisplayedIndex--;
        }

        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);
        updateGamePanel();
    }

    private void menuDown() {
        if ((menuSelectionIndex + 1) < menuList.size()) {
            menuSelectionIndex += 1;
        }

        if (menuSelectionIndex >= maxNumberMenuItemsDisplayed) {
            if ((menuDisplayedIndex + maxNumberMenuItemsDisplayed) < menuList.size()) {
                menuDisplayedIndex++;
            }
        }
        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);
        updateGamePanel();
    }

    private void updateMenuSelection() {
        boolean vv = true;
        menuSelectionPosition = (marginSelectionTop) + ((menuSelectionIndex < maxNumberMenuItemsDisplayed) ? menuSelectionIndex * menuInsert : (maxNumberMenuItemsDisplayed - 1) * menuInsert);

        if (menuSelectionPosition < menuSelectionMovement) {
            menuSelectionMovement -= 2;
            vv = false;
        }

        if (menuSelectionPosition > menuSelectionMovement) {
            menuSelectionMovement += 2;
            vv = false;
        }

        getComponent("menuSelection").setY(menuSelectionMovement);

        for (int i = 0; i < menuList.size(); i++) {
            menuList.get(i).setVisible(false);
        }

        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            float p = marginTop + (i * menuInsert);
            menuList.get(i + menuDisplayedIndex).setY(p);
            menuList.get(i + menuDisplayedIndex).setVisible(isFocused());
        }

        if (!isFocused()) {
            menuList.get(menuSelectionIndex).setVisible(true);
        }

    }

    private void menuSelected() {
        Logger.getLogger(this.getClass()).info("MENU SELECTED AN ITEM");
        int quitIndex = menuList.size() - 1;
        if (menuSelectionIndex == 0) {
            parentScreen.command(Command.SCAN_FOR_GAMES);
        } else if (menuSelectionIndex == quitIndex) {
            parentScreen.command(Command.QUIT);
        } else {
            Logger.getLogger(this.getClass()).info("MENU SELECTED PLATFORM MENU");
            parentScreen.command(Command.PLATFORM_MENU);
        }
    }

    public Platform getChosenPlatform() {
        return Platform.valueOf(menuList.get(menuSelectionIndex).getKey());
    }

    private void menuGoBack() {
        parentScreen.command(Command.GO_BACK);
    }

    private void menuRight() {
        Logger.getLogger(this.getClass()).info("MENU RIGHT " + menuSelectionIndex);
        int quitIndex = menuList.size() - 1;
        if (menuSelectionIndex == 0) {
        } else if (menuSelectionIndex == quitIndex) {
        } else {
            parentScreen.command(Command.GAMEPANEL_FOCUS);
        }

    }

    private void updateGamePanel() {
        int quitIndex = menuList.size() - 1;
        if (menuSelectionIndex == 0) {
        } else if (menuSelectionIndex == quitIndex) {
        } else {
            parentScreen.command(Command.GAMEPANEL_UPDATE);
        }
    }

    /**
     * @param tgmResource the tgmResource to set
     */
    public void setTgmResource(TgmResource tgmResource) {
        this.tgmResource = tgmResource;
    }
}
