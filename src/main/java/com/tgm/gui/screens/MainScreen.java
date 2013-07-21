/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.screens;

import com.tgm.Boot;
import com.tgm.gui.components.Panel;
import com.tgm.gui.enums.Command;
import com.tgm.gui.panels.GamesLibraryPanel;
import com.tgm.gui.panels.MenuSidePanel;
import com.tgm.interfaces.ScannerInterface;
import org.apache.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class MainScreen extends AbstractScreen {

    @Autowired
    private MenuSidePanel menu;
    @Autowired
    private ScannerInterface scanner;
    private Panel mainPanel;
    private GamesLibraryPanel gamesPanel;

    {
        screenName = "Main Scene";
    }

    @Override
    public void configure() {
        menu.setAppInterface(appInterface);
        menu.setParentScreen(this);
        menu.setSize(appInterface.getWidth() / 3.69f, appInterface.getHeight());
        menu.setBackground("images/menu_background.png");
        menu.setLocation(0, 0);

        mainPanel = new Panel("mainPanel", 0, 0, appInterface.getWidth(), appInterface.getHeight(), "images/background_white.png");
        mainPanel.setParentScreen(this);
        mainPanel.setAppInterface(appInterface);

        float panelX = mainPanel.getWidth() / 3.95f;
        float panelWidth = mainPanel.getWidth() - panelX;
        float panelHeight = mainPanel.getHeight();

        gamesPanel = (GamesLibraryPanel) mainPanel.add(new GamesLibraryPanel("gamesPanel", panelX, 0, panelWidth, panelHeight));

        mainPanel.add(menu);

        menuFocus();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mainPanel.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        mainPanel.update(gc, i);
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        mainPanel.render(gc, grphcs);
    }

    @Override
    public void reset() {
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(ScannerInterface scanner) {
        this.scanner = scanner;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(MenuSidePanel menu) {
        this.menu = menu;
    }

    @Override
    public void command(Command command) {
        Logger.getLogger(this.getClass()).info("SCREEN COMMAND: " + command);

        switch (command) {
            case SCAN_FOR_GAMES: {
                scanner.scan();
                break;
            }
            case GAMEPANEL_FOCUS: {
                gamePanelFocus();
                break;
            }
            case GAMEPANEL_UPDATE: {
                break;
            }
            case MENU_FOCUS:{
                menuFocus();
                break;
            }
            case QUIT: {
                Boot.exit(0);
            }
        }
        Logger.getLogger(this.getClass()).info("SCREEN COMMAND: " + command + " DONE");

    }

    private void gamePanelFocus() {
        Logger.getLogger(this.getClass()).info("GAME PANEL FOCUSED");
        menu.setFocused(false);
        gamesPanel.setFocused(true);
    }

    private void menuFocus() {
        Logger.getLogger(this.getClass()).info("MENU PANEL FOCUSED");
        menu.setFocused(true);
        gamesPanel.setFocused(false);

    }
}
