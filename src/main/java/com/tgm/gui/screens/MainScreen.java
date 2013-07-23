/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.screens;

import com.tgm.Boot;
import com.tgm.gui.components.Panel;
import com.tgm.gui.enums.Command;
import com.tgm.gui.enums.Screen;
import com.tgm.gui.panels.GamesLibraryPanel;
import com.tgm.gui.panels.MainMenuPanel;
import com.tgm.interfaces.ScannerInterface;
import com.tgm.resources.TgmResource;
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

    private MainMenuPanel menu;
    @Autowired
    private ScannerInterface scanner;
    private Panel mainPanel;
    private GamesLibraryPanel gamesPanel;
    @Autowired
    private TgmResource tgmResource;

    {
        screenName = "Main Screen";
    }

    @Override
    public void configure() {
        menu = tgmResource.createComponentInstance(MainMenuPanel.class);
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


        gamesPanel = (GamesLibraryPanel) mainPanel.add(tgmResource.createComponentInstance(GamesLibraryPanel.class, "gamesPanel", panelX, 0, panelWidth, panelHeight));

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
        menuFocus();
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(ScannerInterface scanner) {
        this.scanner = scanner;
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
                gamePanelUpdate();
                break;
            }
            case MENU_FOCUS: {
                menuFocus();
                break;
            }
            case PLATFORM_MENU: {
                platformMenu();
                break;
            }
            case GO_BACK: {
                Boot.exit(0);
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
        if (gamesPanel.hasGames()) {
            menu.setFocused(false);
            gamesPanel.setFocused(true);
        } else {
            menuFocus();
        }
    }

    private void menuFocus() {
        Logger.getLogger(this.getClass()).info("MENU PANEL FOCUSED");
        menu.setFocused(true);
        gamesPanel.setFocused(false);

    }

    private void platformMenu() {
        PlatformScreen p = (PlatformScreen) appInterface.getScreenInterface(Screen.PLATFORM);
        p.setPlatform(menu.getChosenPlatform());
        appInterface.processNextScreen(Screen.PLATFORM);
    }

    private void gamePanelUpdate() {
        gamesPanel.updateLibrary(menu.getChosenPlatform());
    }

    /**
     * @param tgmResource the tgmResource to set
     */
    public void setTgmResource(TgmResource tgmResource) {
        this.tgmResource = tgmResource;
    }
}
